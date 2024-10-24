
package com.stawisha.maziwa.erpz.security;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.stawisha.maziwa.erpz.model.Employee;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author samuel
 */
public class UserDetailsImpl implements UserDetails {
    private String phone;
    private String firstName;
    private String secondName;
    @JsonIgnore
    private String password;
    private Collection<? extends GrantedAuthority> authorities;
    private boolean enabled;
    private boolean locked;
    private String email;

    public UserDetailsImpl(String phone, String firstName, String secondName, String password, Collection<? extends GrantedAuthority> authorities, boolean enabled,boolean locked,String email) {
        this.phone=phone;
        this.firstName = firstName;
        this.secondName = secondName;
        this.password = password;
        this.authorities = authorities;
        this.enabled = enabled;
        this.locked=locked;
        this.email=email;
    }

    public static UserDetailsImpl build(Employee user) {
        List<GrantedAuthority> authorities = user.getEmployeeRoles().stream().map(userRole -> new SimpleGrantedAuthority(userRole.getRole().toString())).collect(Collectors.toList());
        return new UserDetailsImpl(user.getPhone(), user.getFirstName(), user.getSecondName(), user.getLoginAccess(), authorities, user.isEnabled(),user.isLocked(),user.getEmail());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return phone;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return (!this.locked);
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }
}
