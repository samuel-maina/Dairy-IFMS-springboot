
package com.stawisha.maziwa.erpz.services;

import com.stawisha.maziwa.erpz.model.EmployeeRoles;
import com.stawisha.maziwa.erpz.repositories.EmployeeRolesRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author samuel
 */
@Service
public class EmployeeRoleService {
 @Autowired
 private EmployeeRolesRepository employeeRolesRepository;
 
 public void save(List<EmployeeRoles> employeeRoles){
     employeeRolesRepository.saveAll(employeeRoles);
 
 }
}
