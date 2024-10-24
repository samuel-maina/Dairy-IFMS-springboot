package com.stawisha.maziwa.erpz.repositories;

import com.stawisha.maziwa.erpz.model.Employee;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 *
 * @author samuel
 */
public interface EmployeeRepository extends PagingAndSortingRepository<Employee, String> {

    @Query("from Employee m where m.Id =?1 and m.tenant.Id=?2")
    public Optional<Employee> findEmployeeByIdAndTenantId(String employee, String tenant);

    @Query("from Employee e where e.phone=?1")
    public Optional<Employee> findEmployeeByPhone(String phone);

    @Modifying
    @Transactional
    @Query(value = "delete  from employee_records where records_id=?1", nativeQuery = true)
    public void deleteEmployeeRecordJoinBy(Long id);
    

}
