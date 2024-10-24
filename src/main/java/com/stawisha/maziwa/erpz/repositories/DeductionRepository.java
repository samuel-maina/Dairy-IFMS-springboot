package com.stawisha.maziwa.erpz.repositories;

import com.stawisha.maziwa.erpz.model.Deduction;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author samuel
 */
public interface DeductionRepository extends JpaRepository<Deduction,String>{
  @Query("from Deduction d where YEAR(d.date)=?1 and MONTH(d.date)=?2 and d.member.Id=?3 and d.member.tenant=?4")
  public List<Deduction> findDeductionByMonthAndMemberAndTenant(int year,int month,String member,String tenant);
  
  @Query("select SUM(d.Cost)from Deduction d where YEAR(d.date)=?1 and MONTH(d.date)=?2 and d.member.Id=?3 and d.member.tenant=?4")
  public Double findDeductionSumByMonthAndMemberAndTenant(int year,int month,String member,String tenant);
}
