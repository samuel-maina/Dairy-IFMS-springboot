package com.stawisha.maziwa.erpz.services;

import com.stawisha.maziwa.erpz.exceptions.NoLicenseFoundException;
import com.stawisha.maziwa.erpz.model.License;
import com.stawisha.maziwa.erpz.model.Tenant;
import com.stawisha.maziwa.erpz.repositories.LicenseRepository;
import java.time.LocalDate;
import java.util.List;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author samuel
 */
@Service
public class LicenseService {

    @Autowired
    private LicenseRepository licenseRepository;

    @Autowired
    private TenantService tenantService;

    public void save(License license, String tenantId) {
        Tenant tenant = tenantService.findById(tenantId);
        license.setId(RandomString.make(10));
        license.setTenant(tenant);
        LocalDate date = LocalDate.now();
        date.plusDays(license.getDuration());
        license.setExpirely(date);
        licenseRepository.save(license);
    }

    public List<License> getLicensesByTenantId(String tenantId) {
        return licenseRepository.getLicensesByTenantId(tenantId);

    }

    public boolean isLicenseExpired(String tenantId) {
        List<License> license = getLicensesByTenantId(tenantId);
        if (license.isEmpty()) {
            throw new NoLicenseFoundException();
        } else {
            if (license.get(license.size() - 1).getExpirely().isAfter(LocalDate.now())) {
                return false;
            }
        }

        return true;
    }

}
