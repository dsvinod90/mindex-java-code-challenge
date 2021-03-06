package com.mindex.challenge.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.text.ParseException;

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
import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.service.CompensationService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CompensationServiceImplTest {

    private String readUrl;
    private String createUrl;
    private Employee testEmployee;
    private Employee testEmployeeOne;
    private Compensation testCompensationOne;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private TestRestTemplate restTemplate;
    
    @LocalServerPort
    private int port;

    @Autowired
    private CompensationService compensationService;

    @Before
    public void setup() throws ParseException {
        readUrl = "http://localhost:" + port + "/compensation/{employeeId}";
        createUrl = "http://localhost:" + port + "/compensation"; 
        testEmployee = employeeRepository.findByEmployeeId(
            "16a596ae-edd3-4847-99fe-c4518e82c86f"
        );
    }
    
    // Test for create endpoint
    @Test
    public void testCreateCompensation() {
        Compensation testCompensation = new Compensation(testEmployee, 10000, "2022-10-10");
        ResponseEntity res = restTemplate.postForEntity(
            createUrl,
            testCompensation,
            Compensation.class
        );
        assertEquals(HttpStatus.OK, res.getStatusCode());
        Compensation responseCompensation = (Compensation)res.getBody();
        assertNotNull(responseCompensation);
        assertEquals(testCompensation, responseCompensation);
    }

    // Test for read endpoint
    @Test
    public void testReadCompensation() {
        testEmployeeOne = employeeRepository.findByEmployeeId("b7839309-3348-463b-a7e3-5de1c168beb3");
        testCompensationOne = new Compensation(testEmployeeOne, 5000, "2022-10-05");
        compensationService.create(testCompensationOne);
        
        ResponseEntity res = restTemplate.getForEntity(
            readUrl,
            Compensation.class,
            testEmployeeOne.getEmployeeId()
        );
        assertEquals(HttpStatus.OK, res.getStatusCode());
        Compensation responseCompensation = (Compensation)res.getBody();
        assertNotNull(responseCompensation);
        assertEquals(
            testEmployeeOne.getEmployeeId(),
            responseCompensation.getEmployee().getEmployeeId()
        );
        assertEquals(
            testCompensationOne.getSalary(),
            responseCompensation.getSalary()
        );
    }

    // Test to check 404 response when employee is not found
    @Test
    public void testNegativeScenario() {
        ResponseEntity res = restTemplate.getForEntity(
            readUrl, 
            Compensation.class, 
            "foobar123"
        );
        assertEquals(HttpStatus.NOT_FOUND, res.getStatusCode());
    }
}
