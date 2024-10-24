package com.stawisha.maziwa.erpz.security;

import com.stawisha.maziwa.erpz.exceptions.EmployeeNotFoundException;
import com.stawisha.maziwa.erpz.model.Employee;
import com.stawisha.maziwa.erpz.repositories.EmployeeRepository;
import java.util.Scanner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    EmployeeRepository employeeRepository;

    @Override
    public UserDetails loadUserByUsername(String phone) {

        Employee employee = employeeRepository.findEmployeeByPhone(phone).orElseThrow(() -> new EmployeeNotFoundException("User name not found"));

        return UserDetailsImpl.build(employee);
    }
}
