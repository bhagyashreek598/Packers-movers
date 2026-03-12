package com.backend.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

/**
 * Concrete principal for STAFF users.
 * Overrides getDisplayInfo() to include this staff member's role.
 *
 * Runtime Polymorphism Example:
 *   UserPrincipal p = new StaffPrincipal(...);
 *   p.getDisplayInfo();  // calls THIS override at runtime
 */
public class StaffPrincipal extends UserPrincipal {

    private final String staffRole;

    public StaffPrincipal(Long userId, String email, String password,
                           Collection<? extends GrantedAuthority> authorities,
                           String roleName, String staffRole) {
        super(userId, email, password, authorities, roleName);
        this.staffRole = staffRole;
    }

    public String getStaffRole() {
        return staffRole;
    }

    @Override
    public String getDisplayInfo() {
        return "Staff [id=" + getUserId() + ", email=" + getUsername()
                + ", staffRole=" + staffRole + "]";
    }
}
