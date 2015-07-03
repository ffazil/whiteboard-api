package org.blanc.whiteboard.init.defaults;

import org.blanc.whiteboard.domain.Authority;
import org.blanc.whiteboard.domain.User;
import org.blanc.whiteboard.init.builder.UserBuilder;

import java.util.HashSet;
import java.util.Set;

public class UsersDefault {
    public static User defaultIdemAdministrator() {
        Set<Authority> authorities = new HashSet<>();
        authorities.add(AuthoritiesDefault.idemAdministrator());
        User user = UserBuilder.anUserBuilder()
                .withUsername("admin")
                .withPassword("admin")
                .withAccountNonExpired(true)
                .withAccountNonLocked(true)
                .withCredentialsNonExpired(true)
                .withEnabled(true)
                .withAuthorities(authorities)
                .build();
        return user;
    }

    public static User defaultIdemAdministrator(Set<Authority> authorities) {
        User user = UserBuilder.anUserBuilder()
                .withUsername("admin")
                .withPassword("admin")
                .withAccountNonExpired(true)
                .withAccountNonLocked(true)
                .withCredentialsNonExpired(true)
                .withEnabled(true)
                .withAuthorities(authorities)
                .build();
        return user;
    }

    public static User defaultTenantAdministrator() {
                Set<Authority> authorities = new HashSet<>();
                authorities.add(AuthoritiesDefault.tenantAdministrator());
                User user = UserBuilder.anUserBuilder()
                        .withUsername("tenant")
                        .withPassword("tenant")
                        .withAccountNonExpired(true)
                        .withAccountNonLocked(true)
                        .withCredentialsNonExpired(true)
                        .withEnabled(true)
                        .withAuthorities(authorities)
                        .build();
                return user;
        }

    public static User defaultTenantAdministrator(Set<Authority> authorities) {
        User user = UserBuilder.anUserBuilder()
                .withUsername("tenant")
                .withPassword("tenant")
                .withAccountNonExpired(true)
                .withAccountNonLocked(true)
                .withCredentialsNonExpired(true)
                .withEnabled(true)
                .withAuthorities(authorities)
                .build();
        return user;
    }
}