package com.flyerssoft.org_chart.controller;

import com.flyerssoft.org_chart.dto.EmployeeDepartmentCustomDto;
import com.flyerssoft.org_chart.dto.EmployeePersonalDetailDto;
import com.flyerssoft.org_chart.dto.LoginRequestDto;
import com.flyerssoft.org_chart.dto.OrganisationDepartmentResponse;
import com.flyerssoft.org_chart.response.AppResponse;
import com.flyerssoft.org_chart.response.CustomEmployeeResponseDto;
import com.flyerssoft.org_chart.response.LoginResponse;
import com.flyerssoft.org_chart.service.EmployeeService;
//import jakarta.validation.Valid;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
//@RequestMapping("/flyers-soft")
@Slf4j
@Validated
@CrossOrigin
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    /**
     * Signup Api, Employee needs to Signup with all the requested credentials.
     * @param employeePersonalDetailDto
     * @return employeeDetails
     */
    @PostMapping("/employee/add")
    public ResponseEntity<AppResponse<EmployeePersonalDetailDto>> addEmployeeDetail(@RequestBody @Valid EmployeePersonalDetailDto employeePersonalDetailDto) {
        log.info("Add Employee Controller Accessed");
        return ResponseEntity.status(HttpStatus.CREATED).body(employeeService.addEmployeeDetail(employeePersonalDetailDto));
    }

    /**
     * Fetch employee details by using employee id
     * @param id
     * @return employee details
     * @throws Exception
     */
    @GetMapping("/employee/{id}")
    public ResponseEntity<AppResponse<EmployeePersonalDetailDto>> getEmployeeById(@PathVariable(required = true) Long id) throws Exception {
        log.info("Fetch Employee Controller Accessed");
        return ResponseEntity.status(HttpStatus.OK).body(employeeService.getEmployeeDetailsById(id));
    }

    /**
     * Update api,To update Employee details, employee should already exist
     * Employee details can be updated by Admin
     * @param id id
     * @param employeePersonalDetailDto employeePersonalDetailDto
     * @return Updated employee credentials
     * @throws Exception employee not found
     */
    @PutMapping("/employee/update")
    public ResponseEntity<AppResponse<EmployeePersonalDetailDto>> updateEmployee(@Valid @RequestParam(required = true) Long id, @RequestBody EmployeePersonalDetailDto employeePersonalDetailDto) throws Exception {
        log.info("Update Employee Controller Accessed");
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(employeeService.updateEmployee(id, employeePersonalDetailDto));
    }

    /**
     * To delete employee details,employee should already exist
     * Employee details can be deleted by Admin
     * @param id employee id
     * @return employee details will be deleted
     * @throws Exception Employee not found
     */

    @DeleteMapping("/employee/remove/{id}")
    public ResponseEntity<AppResponse<String>> deleteEmployee(@PathVariable(required = true) Long id) throws Exception {
        log.info("Remove Employee Controller Accessed");
        return ResponseEntity.status(HttpStatus.OK).body(employeeService.deleteEmployee(id));
    }

    /**
     *  To login, employee need to Signup and employee can be login with his username and password.
     * @param loginRequestDto login request dto
     * @return employee credential
     * @throws Exception Bad credential exception
     */
    @PostMapping("/employee/login")
    public ResponseEntity<AppResponse<LoginResponse>> userLogin(@RequestBody @Valid LoginRequestDto loginRequestDto) throws Exception {
    log.info("Login Request Accessed");
    return ResponseEntity.status(HttpStatus.OK).body(employeeService.userLogin(loginRequestDto.getEmail(),loginRequestDto.getPassword()));
    }

    /**
     *
     * fetch all the specified details from the table
     *
     * @return list of employee details in custom dto response
     */
    @GetMapping("/employee/all")
    public ResponseEntity<AppResponse<List<CustomEmployeeResponseDto>>> allEmployeeDtoResponse(){
        log.info("Find all employee api accessed");
        return ResponseEntity.status(HttpStatus.OK).body(employeeService.allEmployeeDtoResponse());
    }

    @GetMapping("/hierarchy")
    public ResponseEntity<AppResponse<OrganisationDepartmentResponse>> getDepartmentsForHierarchy () {
        log.info("get all ceos and departments for super admin api accessed");
        return ResponseEntity.status(HttpStatus.OK).body(employeeService.getCeoAndAllDepartments());
    }

    @GetMapping(value = "/hierarchy", params = "departmentId")
    public ResponseEntity<AppResponse<List<CustomEmployeeResponseDto>>> getListOfManagers (@RequestParam("departmentId") Long departmentId ) {
        log.info("get all Managers of department for super admin api accessed");
        return ResponseEntity.status(HttpStatus.OK).body(employeeService.getManagersOfDepartment(departmentId));
    }

    @GetMapping(value = "/hierarchy", params = "id")
    public ResponseEntity<AppResponse<?>> getDepartmentsForHierarchy (@RequestParam("id") Long employeeId) {
        log.info("get all departments for super admin api accessed");
        return ResponseEntity.status(HttpStatus.OK).body(employeeService.getChildEmployeesOrReportingManagers(employeeId));
    }

    @GetMapping("/employee/department/all")
    public ResponseEntity<AppResponse<List<EmployeeDepartmentCustomDto>>> getAllDepartmentDetails() {
        log.info("Find all Department api accessed");
        return ResponseEntity.status(HttpStatus.OK).body(employeeService.getAllDepartmentDetails());
    }
}
