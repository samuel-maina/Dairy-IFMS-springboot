package com.stawisha.maziwa.erpz.repositories;

import com.stawisha.maziwa.erpz.model.Tenant;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author samuel
 */
public interface TenantRepository extends CrudRepository<Tenant,String>{
    
    
}
