package com.stawisha.maziwa.erpz.services;

import com.stawisha.maziwa.erpz.exceptions.EmployeeNotFoundException;
import com.stawisha.maziwa.erpz.exceptions.EmployeeAlreadyExistsException;
import com.stawisha.maziwa.erpz.model.Employee;
import com.stawisha.maziwa.erpz.model.EmployeeRoles;
import com.stawisha.maziwa.erpz.model.Roles;
import com.stawisha.maziwa.erpz.model.Tenant;
import com.stawisha.maziwa.erpz.repositories.EmployeeRepository;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 *
 * @author samuel
 */
@Service
public class EmployeeService {
    
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private TenantService tenantService;
    
    @Autowired
    private PasswordEncoder encoder;
    
    @Autowired
    private EmployeeRoleService roleService;
    
    private final Logger log = Logger.getLogger(EmployeeService.class);
    
    @Transactional
    public void save(Employee employee, String tenantId) {
        if (employeeExists(employee.getId(), tenantId)||this.findEmployeeByPhone(employee.getPhone())!=null) {
            System.out.println("Employee with " +employee.getId()+" for tenant "+tenantId + " already exists ACTION=SAVE");
            throw new EmployeeAlreadyExistsException("Employee exists");
        }
        Tenant tenant = tenantService.findById(tenantId);
        employee.setTenant(tenant);
        String password = employee.getLoginAccess();
        employee.setLoginAccess(encoder.encode(password));
        employee.setEnabled(false);
        Employee emp = employeeRepository.save(employee);
       List<EmployeeRoles> employeeRoles = employee.getEmployeeRoles();
        for (EmployeeRoles r : employeeRoles) {
           r.setEmployee(emp);
        }
        roleService.save(employeeRoles);
        
    }
    
    public void saveEmployeeAndRecordInformation(Employee employee) {
        if (employeeExists(employee.getId(), employee.getTenant().getId())) {
            employeeRepository.save(employee);
        } else {
            throw new EmployeeNotFoundException("");
        }
    }
    
    public boolean employeeExists(String employee, String tenant) {
        Optional<Employee> employeeTemp = employeeRepository.findEmployeeByIdAndTenantId(employee, tenant);
        return !employeeTemp.isEmpty();
    }
    
    public Employee findById(String employeeId, String Tenant) {
        Optional<Employee> employee = employeeRepository.findEmployeeByIdAndTenantId(employeeId, Tenant);
        return employee.orElseThrow(() -> new EmployeeNotFoundException(""));
    }
    
    public Employee findEmployeeByPhone(String phone) {
        Optional<Employee> employee = employeeRepository.findEmployeeByPhone(phone);
        return employee.orElseThrow(() -> new EmployeeNotFoundException("Employee with the phone does not exist"));
    }

    public void deleteEmployeeRecordById(Long Id) {
        employeeRepository.deleteEmployeeRecordJoinBy(Id);
    }
    
    public List<Role> rolesAsMap(){
       Roles[] roles= Roles.values();
        List<Role> temp =new ArrayList();
        for(Roles role:roles){    
       temp.add( new Role(role.toString().replace("_", " ").toLowerCase(),role.toString().replace("_", "_")));
        }
        
        return temp;
    
    }
    
    private class Role{
    String role;
    String value;

        public Role(String value, String role) {
            this.role = role;
            this.value = value;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

       
    }
}
