package com.stawisha.maziwa.erpz.repositories;

import com.stawisha.maziwa.erpz.model.Member;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 *
 * @author samuel
 */
public interface MemberRepository extends PagingAndSortingRepository<Member, String> {

    @Query("from Member m where m.Id =?1 and m.tenant.Id=?2")
    public Optional<Member> findMemberByIdAndTenantId(String member, String tenant);

    @Query("from Member m where m.tenant.Id=?1 order by m.firstName ASC")
    public List<Member> findMembersByTenantId(String tenant);

}
