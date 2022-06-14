package com.mindex.challenge.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.EmployeeService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReportingStructureServiceImplTest {
    
    private String readUrl;
    private Employee testEmployeeOne;
    private Employee testEmployeeTwo;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeService employeeService;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Before
    public void setup() {
        readUrl = "http://localhost:" + port + "/reportingStructure/{employeeId}";

        testEmployeeOne = employeeRepository.findByEmployeeId(
            "16a596ae-edd3-4847-99fe-c4518e82c86f"
        );
        testEmployeeTwo = new Employee();
        List<Employee> directReports = new ArrayList<>();
        directReports.add(testEmployeeOne);
        directReports.add(
            employeeRepository.findByEmployeeId(
                "03aa1462-ffa9-4978-901b-7c001562cf6f"
            )
        );

        testEmployeeTwo.setFirstName("Steven");
        testEmployeeTwo.setLastName("Wilson");
        testEmployeeTwo.setPosition("Security Engineer");
        testEmployeeTwo.setDepartment("Technology");
        testEmployeeTwo.setDirectReports(directReports);
        employeeService.create(testEmployeeTwo);
    }

    @After
    public void teardown() {
        readUrl = null;
        testEmployeeOne = null;
    }

    // Test to read reporting structure from existing employee
    @Test
    public void testCreateReportingStructureForExistingEmployee() {
        ReportingStructure testReportingStructure = new ReportingStructure(testEmployeeOne, 4);
        ResponseEntity res = restTemplate.getForEntity(
            readUrl,
            ReportingStructure.class, 
            testEmployeeOne.getEmployeeId()
        );

        assertEquals(HttpStatus.OK, res.getStatusCode());
        ReportingStructure reportingStructure = (ReportingStructure)res.getBody();
        assertNotNull(reportingStructure);
        assertEquals(testReportingStructure, reportingStructure);
    }

    // Test to read reporting structure after creating new employee 
    @Test
    public void testCreateReportingStructureForNewEmployee() {
        ReportingStructure testReportingStructure = new ReportingStructure(testEmployeeTwo, 8);
        ResponseEntity res = restTemplate.getForEntity(
            readUrl, 
            ReportingStructure.class, 
            testEmployeeTwo.getEmployeeId()
        );
        assertEquals(HttpStatus.OK, res.getStatusCode());
        ReportingStructure reportingStructure = (ReportingStructure)res.getBody();
        assertNotNull(reportingStructure);
        assertEquals(testReportingStructure, reportingStructure);
    }

    // Test to check 404 response when employee is not found
    @Test
    public void testNegativeScenario() {
        ResponseEntity res = restTemplate.getForEntity(
            readUrl, 
            ReportingStructure.class, 
            "foobar123"
        );
        assertEquals(HttpStatus.NOT_FOUND, res.getStatusCode());
    }
}
