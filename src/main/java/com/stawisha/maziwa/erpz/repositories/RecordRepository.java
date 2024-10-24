package com.stawisha.maziwa.erpz.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import com.stawisha.maziwa.erpz.model.Record;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.persistence.MapKeyColumn;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author samuel
 */
public interface RecordRepository extends PagingAndSortingRepository<Record, Long> {

    @Query("from Record r where r.member.tenant=?1 and r.member.Id=?2 and r.date=?3")
    public Optional<Record> findRecordByMemberIdAndTenantIdAndDate(String tenant, String member, LocalDate date);

    @Query("from Record r where r.member.Id =?1 and r.member.tenant=?2 and YEAR(r.date)=?3 and MONTH(r.date)=?4")
    public List<Record> findRecordByMemberIdAndTenantIdAndMonth(String memberId, String tenantId, int year, int month);

    @Query("select sum(r.amount) from Record r where r.member.Id =?1 and r.member.tenant=?2 and YEAR(r.date)=?3 and MONTH(r.date)=?4")
    public Optional<Double> findSumByMemberIdAndTenantIdAndMonth(String memberId, String tenantId, int year, int month);

    @Query("select SUM(r.amount) from Record r where r.member.tenant=?1")
    public Integer findSumByTenantId(String tenant);

    @MapKeyColumn(name = "year")
    @Query("select new Map(SUM(r.amount) as amount,YEAR(r.date) as year) from Record r where r.member.tenant=?1 group by(YEAR(r.date)) order by YEAR(r.date) ASC")
    public List<Map<Integer, String>> findAnnualSumationsByTenantId(String tenant);

    @MapKeyColumn(name = "month")
    @Query("select new Map(SUM(r.amount) as amount,MONTH(r.date) as month) from Record r where r.member.tenant=?1 and YEAR(r.date)=?2 group by(MONTH(r.date)) order by MONTH(r.date) ASC")
    public List<Map<Integer, String>> findMonthlySumationsByTenantIdANdYear(String tenant, Integer year);

    @MapKeyColumn(name = "year")
    @Query("select new Map(SUM(r.amount) as amount,DAY(r.date) as date) from Record r where r.member.tenant=?1 and YEAR(r.date)=?2 and MONTH(r.date)=?3 group by(DAY(r.date)) order by DAY(r.date) ASC")
    public List<Map<Integer, String>> findDailySumationsByTenantIdAndMonth(String tenant, int year, int month);

    @Modifying
    @Transactional
    @Query("delete from Record r where r.member.Id=?1 and r.member.tenant=?2 and r.date=?3")
    public void deleteRecordByMemberIdAndTenantIdAndDate(String memberId, String tenantId, LocalDate date);

    @Query("select SUM(r.amount) from Record r where r.member.tenant=?1 and YEAR(r.date)=?2")
    public Double findSumByTenantIdAndYear(String tenant, int year);

    @Query(value="SELECT SUM(record.amount)  from employee_records join record on employee_records.records_id = record.id where employee_records.employee_tenant=?1 and employee_records.employee_id=?2 and record.date=?3",nativeQuery=true)
    public Double findSumTodayByEmployeeAndTenantId(String tenant, String employeeId, LocalDate date);
    
    @Query(value="SELECT Count(record.amount)  from employee_records join record on employee_records.records_id = record.id where employee_records.employee_tenant=?1 and employee_records.employee_id=?2 and record.date=?3",nativeQuery=true)
    public Integer findCountTodayByEmployeeAndTenantId(String tenant, String employeeId, LocalDate date);
    
    @Query(value="select * from employee_records join record on employee_records.records_id = record.id where employee_records.employee_tenant=?1 and employee_records.employee_id=?2 and record.date=?3 order by inserted desc",nativeQuery=true)
    public List<Record>findRecordsByDateAndEmployeeAndTenant(String tenant, String employeeId, LocalDate date);
    }
