package com.mindex.challenge.data;

import java.util.Objects;

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

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof ReportingStructure)) return false;
        ReportingStructure reportingStructure = (ReportingStructure) obj;
        return (
            reportingStructure.getEmployee().getEmployeeId().equalsIgnoreCase(this.employee.getEmployeeId()) &&
            reportingStructure.getNoOfReports() == this.noOfReports
        );
    }
}
