package com.stawisha.maziwa.erpz.Controller;

import com.stawisha.maziwa.erpz.model.Employee;
import com.stawisha.maziwa.erpz.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author samuel
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/employee/")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/{tenant}")
    public ResponseEntity<?> save(@RequestBody Employee employee, @PathVariable String tenant) {
        employeeService.save(employee, tenant);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @GetMapping("/permissions")
    public ResponseEntity<?> getPermissionsAsMap() {
        return new ResponseEntity<>(employeeService.rolesAsMap(),HttpStatus.OK);
    }

}
