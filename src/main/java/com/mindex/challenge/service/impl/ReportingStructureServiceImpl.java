package com.mindex.challenge.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.EmployeeService;
import com.mindex.challenge.service.ReportingStructureService;

@Service
public class ReportingStructureServiceImpl implements ReportingStructureService {
    private static final Logger LOG = LoggerFactory.getLogger(ReportingStructureServiceImpl.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public ReportingStructure read(String employeeId) {
        LOG.debug("Fetching reporting structure for employee [{}]", employeeId);
        Employee employee = employeeRepository.findByEmployeeId(employeeId);

        if (employee == null) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "employee not found");
        }
        int totalNoOfReportees = getTotalReportees(employeeId);
        return new ReportingStructure(employee, totalNoOfReportees);
    }

    private int getTotalReportees(String employeeId) {
        int count = 0;
        Employee employee = employeeRepository.findByEmployeeId(employeeId);

        List<Employee> reportees = employee.getDirectReports();
        if (reportees != null) {
            for (Employee reportee : reportees) {
                count += 1 + getTotalReportees(reportee.getEmployeeId());
            }
        } 
        return count;
    }
}
