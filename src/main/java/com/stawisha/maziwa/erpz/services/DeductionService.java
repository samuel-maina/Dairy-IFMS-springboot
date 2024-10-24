package com.stawisha.maziwa.erpz.services;

import com.stawisha.maziwa.erpz.model.Deduction;
import com.stawisha.maziwa.erpz.model.Member;
import com.stawisha.maziwa.erpz.repositories.DeductionRepository;
import com.stawisha.maziwa.erpz.repositories.MemberRepository;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author samuel
 */
@Service
public class DeductionService {

    @Autowired
    private DeductionRepository deductionRepository;

    @Autowired
    private MemberService memberService;

    public void save(Deduction deduction, String tenant) {
        Member member = memberService.findmemberByIdAndTenantId(deduction.getMember().getId(), tenant);
        deduction.setMember(member);
        deduction.setDate(LocalDate.now());
        deductionRepository.save(deduction);
    }

    public List<Deduction> findDeductionByMonthAndMemberAndTenant(LocalDate date, String member, String tenant) {
        return deductionRepository.findDeductionByMonthAndMemberAndTenant(date.getYear(), date.getMonthValue(), member, tenant);
    }

    public Double findDeductionSumByMonthAndMemberAndTenant(LocalDate date, String member, String tenant) {
        return deductionRepository.findDeductionSumByMonthAndMemberAndTenant(date.getYear(), date.getMonthValue(), member, tenant);
    }

}
