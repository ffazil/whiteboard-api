package org.blanc.whiteboard.init.defaults;

import org.blanc.whiteboard.domain.Authority;
import org.blanc.whiteboard.domain.Client;
import org.blanc.whiteboard.init.builder.ClientBuilder;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ClientDefault {
    public static Client defaultClient() {
        Set<String> authorizedGrantTypes = new HashSet<String>();
        authorizedGrantTypes.add("authorization_code");
        authorizedGrantTypes.add("refresh_token");
        authorizedGrantTypes.add("password");

        Set<String> scopes = new HashSet<String>();
        scopes.add("idem-read");
        scopes.add("idem-write");
        Client client = ClientBuilder.aClientBuilder()
                .withClientId("idem-admin")
                .withClientSecret("idem-admin-secret")
                .withScope(scopes)
                .withAuthorizedGrantTypes(authorizedGrantTypes)
                .build();
        return client;
    }

    public static Client defaultClient(List<Authority> authorities) {
        Set<String> authorizedGrantTypes = new HashSet<String>();
        authorizedGrantTypes.add("authorization_code");
        authorizedGrantTypes.add("refresh_token");
        authorizedGrantTypes.add("password");

        Set<String> scopes = new HashSet<String>();
        scopes.add("idem-read");
        scopes.add("idem-write");
        Client client = ClientBuilder.aClientBuilder()
                .withClientId("idem-admin")
                .withClientSecret("idem-admin-secret")
                .withScope(scopes)
                .withAuthorities(authorities)
                .withAuthorizedGrantTypes(authorizedGrantTypes)
                .build();
        return client;
    }
}