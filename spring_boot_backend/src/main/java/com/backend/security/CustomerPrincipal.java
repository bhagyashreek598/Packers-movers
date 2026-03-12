package com.backend.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

/**
 * Concrete principal for CUSTOMER users.
 * Overrides getDisplayInfo() to return customer-specific information.
 *
 * Runtime Polymorphism Example:
 *   UserPrincipal p = new CustomerPrincipal(...);
 *   p.getDisplayInfo();  // calls THIS override at runtime
 */
public class CustomerPrincipal extends UserPrincipal {

    public CustomerPrincipal(Long userId, String email, String password,
                              Collection<? extends GrantedAuthority> authorities,
                              String roleName) {
        super(userId, email, password, authorities, roleName);
    }

    @Override
    public String getDisplayInfo() {
        return "Customer [id=" + getUserId() + ", email=" + getUsername() + "]";
    }
}
