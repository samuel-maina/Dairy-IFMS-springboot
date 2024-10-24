package com.stawisha.maziwa.erpz.services;

import com.stawisha.maziwa.erpz.exceptions.TenantNotFoundException;
import com.stawisha.maziwa.erpz.model.Tenant;
import com.stawisha.maziwa.erpz.repositories.TenantRepository;
import java.util.List;
import java.util.Optional;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author samuel
 */
@Service
public class TenantService {

    @Autowired
    private TenantRepository tenantRepository;

    public void save(Tenant tenant) {
        tenant.setId(RandomString.make(10));
        tenantRepository.save(tenant);
    }

    public Tenant findById(String tenantId) {
        Optional<Tenant> tenant = tenantRepository.findById(tenantId);
        return tenant.orElseThrow(() -> new TenantNotFoundException("Not found"));
    }

    public Iterable<Tenant> findAll() {
        return tenantRepository.findAll();
    }

    public String findByIdAlt(String t) {
    Optional<Tenant> tenant = tenantRepository.findById(t);    
    if(tenant.isPresent())
        return tenant.get().getName();
    else
        return "---";
    }
}
