package com.mindex.challenge.data;

public class ReportingStructure {
    
    private Employee employee;
    private int noOfReports;

    public ReportingStructure(Employee employee, int noOfReports) {
        this.employee = employee;
        this.noOfReports = noOfReports;
    }

    public Employee getEmployee() {
        return this.employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public int getNoOfReports() {
        return this.noOfReports;
    }

    public void setNoOfReports(int noOfReports) {
        this.noOfReports = noOfReports;
    }
}
