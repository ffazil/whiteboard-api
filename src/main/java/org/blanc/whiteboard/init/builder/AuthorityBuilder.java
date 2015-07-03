package org.blanc.whiteboard.init.builder;

import org.blanc.whiteboard.domain.Authority;

/**
 * @author sadath
 * @since 13-03-15
 */
public class AuthorityBuilder {
    private String role;

    private AuthorityBuilder() {

    }

    public static AuthorityBuilder anAuthorityBuilder() {
        return new AuthorityBuilder();
    }

    public AuthorityBuilder withRole(String role){
        this.role = role;
        return this;
    }

    public Authority build(){
        Authority authority = new Authority(this.role);
        return authority;
    }
}
