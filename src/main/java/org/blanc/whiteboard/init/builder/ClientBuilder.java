package org.blanc.whiteboard.init.builder;


import org.blanc.whiteboard.domain.Authority;
import org.blanc.whiteboard.domain.Client;

import java.util.*;

public class ClientBuilder {
    private String clientId;
    private String clientSecret;
    private Set<String> scope = Collections.emptySet();
    private Set<String> resourceIds = Collections.emptySet();
    private Set<String> authorizedGrantTypes = Collections.emptySet();
    private Set<String> registeredRedirectUris;
    private Set<String> autoApproveScopes;
    private List<Authority> authorities = Collections.emptyList();
    private Integer accessTokenValiditySeconds;
    private Integer refreshTokenValiditySeconds;
    private Map<String, String> additionalInformation = new LinkedHashMap<>();

    private ClientBuilder() {}

    public static ClientBuilder aClientBuilder() {
        return new ClientBuilder();
    }

    public ClientBuilder withClientId(String clientId) {
        this.clientId = clientId;
        return this;
    }

    public ClientBuilder withClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
        return this;
    }

    public ClientBuilder withScope(Set<String> scope) {
        this.scope = scope;
        return this;
    }

    public ClientBuilder withResourceIds(Set<String> resourceIds) {
        this.resourceIds = resourceIds;
        return this;
    }

    public ClientBuilder withAuthorizedGrantTypes(Set<String> authorizedGrantTypes) {
        this.authorizedGrantTypes = authorizedGrantTypes;
        return this;
    }

    public ClientBuilder withRegisteredRedirectUris(Set<String> registeredRedirectUris) {
        this.registeredRedirectUris = registeredRedirectUris;
        return this;
    }

    public ClientBuilder withAutoApproveScopes(Set<String> autoApproveScopes) {
        this.autoApproveScopes = autoApproveScopes;
        return this;
    }

    public ClientBuilder withAuthorities(List<Authority> authorities) {
        this.authorities = authorities;
        return this;
    }

    public ClientBuilder withAccessTokenValiditySeconds(Integer accessTokenValiditySeconds) {
        this.accessTokenValiditySeconds = accessTokenValiditySeconds;
        return this;
    }

    public ClientBuilder withRefreshTokenValiditySeconds(Integer refreshTokenValiditySeconds) {
        this.refreshTokenValiditySeconds = refreshTokenValiditySeconds;
        return this;
    }

    public ClientBuilder withAdditionalInformation(Map<String, String> additionalInformation) {
        this.additionalInformation = additionalInformation;
        return this;
    }

    public Client build() {
        Client client = new Client();
        client.setAccessTokenValiditySeconds(this.accessTokenValiditySeconds);
        client.setAdditionalInformation(this.additionalInformation);
        client.setAuthorities(this.authorities);
        client.setAuthorizedGrantTypes(this.authorizedGrantTypes);
        client.setAutoApproveScopes(this.autoApproveScopes);
        client.setClientId(this.clientId);
        client.setClientSecret(this.clientSecret);
        client.setRefreshTokenValiditySeconds(this.refreshTokenValiditySeconds);
        client.setRegisteredRedirectUri(this.registeredRedirectUris);
        client.setResourceIds(this.resourceIds);
        client.setScope(this.scope);
        return client;
    }
}
