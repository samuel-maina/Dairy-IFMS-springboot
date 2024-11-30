package com.stawisha.maziwa.erpz;

import com.stawisha.maziwa.erpz.model.Employee;
import com.stawisha.maziwa.erpz.repositories.EmployeeRepository;
import com.stawisha.maziwa.erpz.services.EmployeeService;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;


@ExtendWith(MockitoExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class ErpzApplicationTests {

    @InjectMocks
    private EmployeeService employeeService;
    @Mock
    private EmployeeRepository employeeRepository;
    @Test
    void contextLoads() {
        String employeeId = "0707588686";
        Employee employee = new Employee();
        employee.setPhone(employeeId);
        employee.setFirstName("Samuel");
        employee.setEmail("maina9025@gmail.com");
        Mockito.when(employeeRepository.findEmployeeByPhone(employeeId)).thenReturn(Optional.of(employee));

        Employee result = employeeService.findEmployeeByPhone("0707588686");
        System.out.println(result);
        assertNotNull(result);
        assertEquals(employeeId,result.getPhone());
        assertEquals("Samuel",result.getFirstName());
       assertEquals("maina9025@gmail.com",result.getEmail());
        
        
        
    }

}
