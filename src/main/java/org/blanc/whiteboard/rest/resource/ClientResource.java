package org.blanc.whiteboard.rest.resource;

import com.tracebucket.tron.assembler.BaseResource;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by sadath on 29-Apr-15.
 */
public class ClientResource extends BaseResource {
    private String clientId;
    private String clientSecret;
    private Set<String> scope = new HashSet<String>(0);
    private Set<String> resourceIds = new HashSet<String>(0);
    private Set<String> authorizedGrantTypes = new HashSet<String>(0);
    private Set<String> registeredRedirectUris = new HashSet<String>(0);
    private Set<String> autoApproveScopes = new HashSet<String>(0);
    private Set<AuthorityResource> authorities = new HashSet<AuthorityResource>();
    private Integer accessTokenValiditySeconds;
    private Integer refreshTokenValiditySeconds;
    private Map<String, String> additionalInformation = new LinkedHashMap<String, String>();

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public Set<String> getScope() {
        return scope;
    }

    public void setScope(Set<String> scope) {
        this.scope = scope;
    }

    public Set<String> getResourceIds() {
        return resourceIds;
    }

    public void setResourceIds(Set<String> resourceIds) {
        this.resourceIds = resourceIds;
    }

    public Set<String> getAuthorizedGrantTypes() {
        return authorizedGrantTypes;
    }

    public void setAuthorizedGrantTypes(Set<String> authorizedGrantTypes) {
        this.authorizedGrantTypes = authorizedGrantTypes;
    }

    public Set<String> getRegisteredRedirectUris() {
        return registeredRedirectUris;
    }

    public void setRegisteredRedirectUris(Set<String> registeredRedirectUris) {
        this.registeredRedirectUris = registeredRedirectUris;
    }

    public Set<String> getAutoApproveScopes() {
        return autoApproveScopes;
    }

    public void setAutoApproveScopes(Set<String> autoApproveScopes) {
        this.autoApproveScopes = autoApproveScopes;
    }

    public Set<AuthorityResource> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<AuthorityResource> authorities) {
        this.authorities = authorities;
    }

    public Integer getAccessTokenValiditySeconds() {
        return accessTokenValiditySeconds;
    }

    public void setAccessTokenValiditySeconds(Integer accessTokenValiditySeconds) {
        this.accessTokenValiditySeconds = accessTokenValiditySeconds;
    }

    public Integer getRefreshTokenValiditySeconds() {
        return refreshTokenValiditySeconds;
    }

    public void setRefreshTokenValiditySeconds(Integer refreshTokenValiditySeconds) {
        this.refreshTokenValiditySeconds = refreshTokenValiditySeconds;
    }

    public Map<String, String> getAdditionalInformation() {
        return additionalInformation;
    }

    public void setAdditionalInformation(Map<String, String> additionalInformation) {
        this.additionalInformation = additionalInformation;
    }
}