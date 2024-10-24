package com.stawisha.maziwa.erpz.services;

import com.stawisha.maziwa.erpz.exceptions.MemberExistsException;
import com.stawisha.maziwa.erpz.exceptions.MemberNotFoundException;
import com.stawisha.maziwa.erpz.model.Member;
import com.stawisha.maziwa.erpz.model.Tenant;
import com.stawisha.maziwa.erpz.repositories.MemberRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author samuel
 */
@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private TenantService tenantService;

    public void save(Member member, String tenantId) {
        if (memberExists(member.getId(), tenantId)) {
            throw new MemberExistsException("Member exists");
        }
        Tenant tenant = tenantService.findById(tenantId);
        member.setTenant(tenant);
        memberRepository.save(member);
    }

    public boolean memberExists(String member, String tenant) {
        Optional<Member> memberTemp = memberRepository.findMemberByIdAndTenantId(member, tenant);
        return !memberTemp.isEmpty();
    }

    public Member findmemberByIdAndTenantId(String memberId, String tenantId) {
        Optional<Member> memberTemp = memberRepository.findMemberByIdAndTenantId(memberId, tenantId);
        return memberTemp.orElseThrow(() -> new MemberNotFoundException(""));
    }

   public List<Member> findMembersByTenantId(String tenant) {
        return memberRepository.findMembersByTenantId(tenant);
    }

}
