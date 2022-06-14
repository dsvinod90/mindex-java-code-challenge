package com.mindex.challenge.service.impl;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mindex.challenge.dao.CompensationRepository;
import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.service.CompensationService;

@Service
public class CompensationServiceImpl implements CompensationService {

    private static final Logger LOG = LoggerFactory.getLogger(CompensationServiceImpl.class);

    @Autowired
    private CompensationRepository compensationRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public Compensation create(Compensation compensation) {
        LOG.debug("Creating compensation [{}]", compensation);
        compensation.setId(UUID.randomUUID().toString());
        compensationRepository.insert(compensation);
        LOG.debug("Created compensation [{}]", compensation);
        
        return compensation;
    }

    @Override
    public Compensation read(String employeeId) {
        LOG.debug("Fetching compensation for employee [{}]", employeeId);
        Employee employee = employeeRepository.findByEmployeeId(employeeId);
        Compensation comp = compensationRepository.findByEmployee(employee);
        LOG.debug("Found compensation for employee [{}]", employeeId);

        return comp;
    }
}
