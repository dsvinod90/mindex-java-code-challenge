# Instuctions to run the application and tests
---
The coding challenge has been completed as per the requirements mentioned in the **README.md** file.

*Please Note: I had to upgrade gradle to version 7.4.2 to be compatible with my Java version.*

## API Contract:
***Command to start the application: `./gradlew bootRun`***
## TASK 1
### Reporting Structure
This is a new class added to the code repository. As mentioned in the README.md file, it is calculated on the fly.
#### Endpoint
`GET /reportingStructure/{employeeId}`:
It returns the ReportingStructure object for the specified employeeId. This is calculated by recursively going through all the direct reports of the mentioned employee and aggregating the sum.

*Assumption: Consider Employee A has 4 direct reports including Employee B and Employee B has 2 direct reports. If Employee C has both Employee A and Employee B as direct reports, then the total number of reportees for Employee C will be calculated to be 8.*

## TASK 2
### Compensation
This is a new class added to the code repository as mentioned in the README.md file. This is mapped to the MongoDB repository and is persisted.
#### Endpoints
1. `POST /compensation`: 
This endpoint is used to create a compensation. The following fields are sent in the JSON to create the compensation object:
    * `Employee employee`
    * `int salary`
    * `String effectiveDate`

2. `GET /compensation/{employeeId}`:
This endpoing is used to fetch the compensation for the specified employee.

## Tests:
***Command to run the tests `./gradlew test`***

Tests have been written for ***ReportingStructureServiceImpl*** and ***CompensationServiceImpl***.

**Note:** *The postman bundle has been attached for reference. Please refer to it to run the api tests.*
