
package com.stawisha.maziwa.erpz.exceptions;

/**
 *
 * @author samuel
 */
public class TenantNotFoundException extends RuntimeException {

    public TenantNotFoundException(String exceptionMessage) {
        super(exceptionMessage);
    }
    
}
