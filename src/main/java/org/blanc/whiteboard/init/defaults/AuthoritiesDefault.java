package org.blanc.whiteboard.init.defaults;

import org.blanc.whiteboard.domain.Authority;
import org.blanc.whiteboard.init.builder.AuthorityBuilder;

public class AuthoritiesDefault {
    public static Authority idemAdministrator() {
        Authority authority = AuthorityBuilder.anAuthorityBuilder()
                .withRole("IDEM_ADMINISTRATOR")
                .build();
        return authority;
    }

    public static Authority tenantAdministrator() {
        Authority authority = AuthorityBuilder.anAuthorityBuilder()
                .withRole("TENANT_ADMINISTRATOR")
                .build();
        return authority;
    }
}