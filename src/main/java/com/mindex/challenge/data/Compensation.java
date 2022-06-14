package com.mindex.challenge.data;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonFormat;

@Document
public class Compensation {
   
    @Id
    private String id;
    
    private Employee employee;
    private int salary;
    private String effectiveDate;

    public Compensation(Employee employee, int salary, String effectiveDate) {
        this.employee = employee;
        this.salary = salary;
        this.effectiveDate = effectiveDate;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Employee getEmployee() {
        return this.employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public int getSalary() {
        return this.salary;
    }
    
    public void setSalary(int salary) {
        this.salary = salary;
    }

    public String getEffectiveDate() {
        return this.effectiveDate;
    }

    public void setEffectiveDate(String effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof Compensation)) return false;
        Compensation compensation = (Compensation) obj;
        System.out.println("Passed: " + compensation.getEffectiveDate());
        System.out.println("This: " + this.getEffectiveDate());
        return (
            compensation.getEmployee().getEmployeeId().equalsIgnoreCase(this.employee.getEmployeeId()) &&
            compensation.getSalary() == this.salary &&
            compensation.getEffectiveDate().equals(this.effectiveDate)
        );
    }
}
