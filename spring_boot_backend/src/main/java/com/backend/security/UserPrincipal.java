package com.backend.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Abstract base class for authenticated user principals.
 * Runtime polymorphism is achieved here:
 *   - CustomerPrincipal and StaffPrincipal are the concrete subclasses.
 *   - getDisplayInfo() is overridden differently in each subclass.
 *   - JwtUtils.generateToken(UserPrincipal) works with ANY subclass via polymorphism.
 */
public abstract class UserPrincipal implements UserDetails {

    private Long userId;
    private String email;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;
    private String roleName;

    public UserPrincipal(Long userId, String email, String password,
                         Collection<? extends GrantedAuthority> authorities,
                         String roleName) {
        this.userId = userId;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
        this.roleName = roleName;
    }

    // ---------------------------------------------------------------
    // Abstract method - each subclass MUST provide its own version.
    // This is the POLYMORPHIC method.
    // ---------------------------------------------------------------
    public abstract String getDisplayInfo();

    // ---------------------------------------------------------------
    // Spring Security UserDetails methods
    // ---------------------------------------------------------------
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    public Long getUserId() {
        return this.userId;
    }

    public String getRoleName() {
        return this.roleName;
    }

    @Override public boolean isAccountNonExpired()   { return true; }
    @Override public boolean isAccountNonLocked()    { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled()             { return true; }
}