package com.stawisha.maziwa.erpz.repositories;

import com.stawisha.maziwa.erpz.model.License;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author samuel
 */
public interface LicenseRepository extends JpaRepository<License,String>{
    @Query("from License l where l.tenant.id=?1 order by l.expirely ASC")
    List<License> getLicensesByTenantId(String tenantId);
    
}
