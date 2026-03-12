package com.backend.security;

import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backend.entities.Staff;
import com.backend.entities.User;
import com.backend.entities.UserRole;
import com.backend.repository.UserRepository;

import lombok.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@Transactional
@RequiredArgsConstructor
public class CustomUserDetailsServiceImpl implements UserDetailsService {

    private static final Logger log = LoggerFactory.getLogger(CustomUserDetailsServiceImpl.class);

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        log.info("********* in load user ");

        // 1. Fetch User (abstract entity)
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        // 2. Extract role name
        String roleName = user.getUserRole().name();
        var authority = List.of(new SimpleGrantedAuthority("ROLE_" + roleName));

        // -------------------------------------------------------------------
        // RUNTIME POLYMORPHISM (method overriding):
        //
        // Based on the actual type of user at runtime, we create a different
        // UserPrincipal subclass:
        //   - Staff  → StaffPrincipal  (getDisplayInfo returns staff + staffRole)
        //   - Others → CustomerPrincipal (getDisplayInfo returns customer info)
        //
        // The caller (e.g. JwtUtils.generateToken(UserPrincipal)) always sees
        // UserPrincipal — but getDisplayInfo() resolves to the correct subclass
        // version at RUNTIME. That is runtime polymorphism.
        // -------------------------------------------------------------------
        UserPrincipal principal;

        if (user.getUserRole() == UserRole.STAFF && user instanceof Staff) {
            Staff staff = (Staff) user;
            String staffRole = staff.getStaffRole() != null ? staff.getStaffRole().name() : "UNKNOWN";
            principal = new StaffPrincipal(user.getId(), user.getEmail(), user.getPassword(), authority, roleName, staffRole);
            log.info("Loaded as StaffPrincipal: {}", principal.getDisplayInfo());
        } else {
            // CUSTOMER or ADMIN - use CustomerPrincipal
            principal = new CustomerPrincipal(user.getId(), user.getEmail(), user.getPassword(), authority, roleName);
            log.info("Loaded as CustomerPrincipal: {}", principal.getDisplayInfo());
        }

        return principal;
    }
}
