
package com.stawisha.maziwa.erpz.model;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author samuel
 */
public class EmployeeID implements Serializable{
     private String Id;
    private String tenant;

    public String getId() {
        return Id;
    }

    public void setId(String Id) {
        this.Id = Id;
    }

    public String getTenant() {
        return tenant;
    }

    public void setTenant(String tenant) {
        this.tenant = tenant;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final EmployeeID other = (EmployeeID) obj;
        if (!Objects.equals(this.Id, other.Id)) {
            return false;
        }
        return Objects.equals(this.tenant, other.tenant);
    }
   
}
