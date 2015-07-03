package org.blanc.whiteboard.domain;

import com.tracebucket.tron.ddd.domain.BaseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.util.*;

/**
 * @author FFL
 * @since 12-03-2015
 */
@Entity
@Table(name = "CLIENT")
public class Client extends BaseEntity implements ClientDetails{

    @Column(name = "CLIENT_ID", unique = true, nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private String clientId;

    @Column(name = "CLIENT_SECRET", nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private String clientSecret;

    @ElementCollection(fetch = FetchType.EAGER)
    @JoinTable(name = "CLIENT_SCOPE", joinColumns = @JoinColumn(name = "CLIENT__ID"))
    private Set<String> scope = new HashSet<String>(0);

    @ElementCollection(fetch = FetchType.EAGER)
    @JoinTable(name = "CLIENT_RESOURCE_IDS", joinColumns = @JoinColumn(name = "CLIENT__ID"))
    private Set<String> resourceIds = new HashSet<String>(0);

    @ElementCollection(fetch = FetchType.EAGER)
    @JoinTable(name = "CLIENT_AUTHORISED_GRANT_TYPES", joinColumns = @JoinColumn(name = "CLIENT__ID"))
    private Set<String> authorizedGrantTypes = new HashSet<String>(0);

    @ElementCollection(fetch = FetchType.EAGER)
    @JoinTable(name = "CLIENT_REGISTERED_REDIRECT_URIS", joinColumns = @JoinColumn(name = "CLIENT__ID"))
    private Set<String> registeredRedirectUris = new HashSet<String>(0);

    @ElementCollection(fetch = FetchType.EAGER)
    @JoinTable(name = "CLIENT_AUTO_APPROVES_SCOPES", joinColumns = @JoinColumn(name = "CLIENT__ID"))
    private Set<String> autoApproveScopes = new HashSet<String>(0);

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinTable(
            name = "CLIENT_AUTHORITY",
            joinColumns = { @JoinColumn(name = "CLIENT__ID", referencedColumnName = "ID") },
            inverseJoinColumns={ @JoinColumn(name = "AUTHORITY__ID", referencedColumnName = "ID", unique = true) }
    )
    private List<Authority> authorities = new ArrayList<Authority>();

    @Column(name = "ACCESS_TOKEN_VALIDITY_SECONDS", nullable = true)
    @Basic(fetch = FetchType.EAGER)
    private Integer accessTokenValiditySeconds;

    @Column(name = "REFRESH_TOKEN_VALIDITY_SECONDS", nullable = true)
    @Basic(fetch = FetchType.EAGER)
    private Integer refreshTokenValiditySeconds;

    @ElementCollection
    @MapKeyColumn(name = "MESSAGE")
    @Column(name = "INFORMATION", nullable = true)
    @CollectionTable(name = "CLIENT_ADDITIONAL_INFORMATION", joinColumns = @JoinColumn(name = "CLIENT__ID"))
    private Map<String, String> additionalInformation = new LinkedHashMap<String, String>();

    public Client() {
    }

    public Client(ClientDetails prototype) {
        this();
        setAccessTokenValiditySeconds(prototype.getAccessTokenValiditySeconds());
        setRefreshTokenValiditySeconds(prototype
                .getRefreshTokenValiditySeconds());
        setAuthorities(prototype.getAuthorities());
        setAuthorizedGrantTypes(prototype.getAuthorizedGrantTypes());
        setClientId(prototype.getClientId());
        setClientSecret(prototype.getClientSecret());
        setRegisteredRedirectUri(prototype.getRegisteredRedirectUri());
        setScope(prototype.getScope());
        setResourceIds(prototype.getResourceIds());
    }

    public Client(String clientId, String resourceIds,
                             String scopes, String grantTypes, String authorities) {
        this(clientId, resourceIds, scopes, grantTypes, authorities, null);
    }

    public Client(String clientId, String resourceIds,
                             String scopes, String grantTypes, String authorities,
                             String redirectUris) {

        this.clientId = clientId;

        if (StringUtils.hasText(resourceIds)) {
            Set<String> resources = StringUtils
                    .commaDelimitedListToSet(resourceIds);
            if (!resources.isEmpty()) {
                this.resourceIds = resources;
            }
        }

        if (StringUtils.hasText(scopes)) {
            Set<String> scopeList = StringUtils.commaDelimitedListToSet(scopes);
            if (!scopeList.isEmpty()) {
                this.scope = scopeList;
            }
        }

        if (StringUtils.hasText(grantTypes)) {
            this.authorizedGrantTypes = StringUtils
                    .commaDelimitedListToSet(grantTypes);
        } else {
            this.authorizedGrantTypes = new HashSet<String>(Arrays.asList(
                    "authorization_code", "refresh_token"));
        }

        if (StringUtils.hasText(authorities)) {
            AuthorityUtils.commaSeparatedStringToAuthorityList(authorities)
                    .stream()
                    .forEach(grantedAuthority -> this.authorities.add((Authority) grantedAuthority));
        }

        if (StringUtils.hasText(redirectUris)) {
            this.registeredRedirectUris = StringUtils
                    .commaDelimitedListToSet(redirectUris);
        }
    }

    @Override
    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public void setAutoApproveScopes(Collection<String> autoApproveScopes) {
        if(autoApproveScopes != null && autoApproveScopes.size() > 0) {
            this.autoApproveScopes.addAll(autoApproveScopes);
        }
    }

    @Override
    public boolean isAutoApprove(String scope) {
        if (autoApproveScopes == null) {
            return false;
        }
        for (String auto : autoApproveScopes) {
            if (auto.equals("true") || scope.matches(auto)) {
                return true;
            }
        }
        return false;
    }


    public Set<String> getAutoApproveScopes() {
        return autoApproveScopes;
    }

    @Override
    public boolean isSecretRequired() {
        return this.clientSecret != null;
    }

    @Override
    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    @Override
    public boolean isScoped() {
        return this.scope != null && !this.scope.isEmpty();
    }

    @Override
    public Set<String> getScope() {
        return scope;
    }

    public void setScope(Collection<String> scope) {
        this.scope = scope == null ? Collections.<String> emptySet()
                : new LinkedHashSet<String>(scope);
    }

    @Override
    public Set<String> getResourceIds() {
        return resourceIds;
    }

    public void setResourceIds(Collection<String> resourceIds) {
        this.resourceIds = resourceIds == null ? Collections
                .<String> emptySet() : new LinkedHashSet<String>(resourceIds);
    }

    @Override
    public Set<String> getAuthorizedGrantTypes() {
        return authorizedGrantTypes;
    }

    public void setAuthorizedGrantTypes(Collection<String> authorizedGrantTypes) {
        this.authorizedGrantTypes = new LinkedHashSet<String>(
                authorizedGrantTypes);
    }

    @Override
    public Set<String> getRegisteredRedirectUri() {
        return registeredRedirectUris;
    }


    public void setRegisteredRedirectUri(Set<String> registeredRedirectUris) {
        this.registeredRedirectUris = registeredRedirectUris == null ? null
                : new LinkedHashSet<String>(registeredRedirectUris);
    }


    private List<String> getAuthoritiesAsStrings() {
        return new ArrayList<String>(
                AuthorityUtils.authorityListToSet(authorities));
    }

    private void setAuthoritiesAsStrings(Set<String> values) {
        setAuthorities(AuthorityUtils.createAuthorityList(values
                .toArray(new String[values.size()])));
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> grantedAuthorities = new HashSet<GrantedAuthority>();
        this.authorities.forEach(authority -> grantedAuthorities.add(authority));
        return grantedAuthorities;
    }

    public void setAuthorities(
            Collection<? extends GrantedAuthority> authorities) {
        List<Authority> authorityList = new ArrayList<>(0);
        authorities.forEach(authority -> authorityList.add((Authority) authority));
        this.authorities = authorityList;
    }

    @Override
    public Integer getAccessTokenValiditySeconds() {
        return accessTokenValiditySeconds;
    }

    public void setAccessTokenValiditySeconds(Integer accessTokenValiditySeconds) {
        this.accessTokenValiditySeconds = accessTokenValiditySeconds;
    }

    @Override
    public Integer getRefreshTokenValiditySeconds() {
        return refreshTokenValiditySeconds;
    }

    public void setRefreshTokenValiditySeconds(
            Integer refreshTokenValiditySeconds) {
        this.refreshTokenValiditySeconds = refreshTokenValiditySeconds;
    }

    public void setAdditionalInformation(Map<String, String> additionalInformation) {
        this.additionalInformation = new LinkedHashMap<String, String>(
                additionalInformation);
    }

    @Override
    public Map<String, Object> getAdditionalInformation() {
        return Collections.unmodifiableMap(this.additionalInformation);
    }

    public void addAdditionalInformation(String key, String value) {
        this.additionalInformation.put(key, value);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime
                * result
                + ((accessTokenValiditySeconds == null) ? 0
                : accessTokenValiditySeconds);
        result = prime
                * result
                + ((refreshTokenValiditySeconds == null) ? 0
                : refreshTokenValiditySeconds);
        result = prime * result
                + ((authorities == null) ? 0 : authorities.hashCode());
        result = prime
                * result
                + ((authorizedGrantTypes == null) ? 0 : authorizedGrantTypes
                .hashCode());
        result = prime * result
                + ((clientId == null) ? 0 : clientId.hashCode());
        result = prime * result
                + ((clientSecret == null) ? 0 : clientSecret.hashCode());
        result = prime
                * result
                + ((registeredRedirectUris == null) ? 0
                : registeredRedirectUris.hashCode());
        result = prime * result
                + ((resourceIds == null) ? 0 : resourceIds.hashCode());
        result = prime * result + ((scope == null) ? 0 : scope.hashCode());
        result = prime * result + ((additionalInformation == null) ? 0 : additionalInformation.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Client other = (Client) obj;
        if (accessTokenValiditySeconds != other.accessTokenValiditySeconds)
            return false;
        if (refreshTokenValiditySeconds != other.refreshTokenValiditySeconds)
            return false;
        if (authorities == null) {
            if (other.authorities != null)
                return false;
        } else if (!authorities.equals(other.authorities))
            return false;
        if (authorizedGrantTypes == null) {
            if (other.authorizedGrantTypes != null)
                return false;
        } else if (!authorizedGrantTypes.equals(other.authorizedGrantTypes))
            return false;
        if (clientId == null) {
            if (other.clientId != null)
                return false;
        } else if (!clientId.equals(other.clientId))
            return false;
        if (clientSecret == null) {
            if (other.clientSecret != null)
                return false;
        } else if (!clientSecret.equals(other.clientSecret))
            return false;
        if (registeredRedirectUris == null) {
            if (other.registeredRedirectUris != null)
                return false;
        } else if (!registeredRedirectUris.equals(other.registeredRedirectUris))
            return false;
        if (resourceIds == null) {
            if (other.resourceIds != null)
                return false;
        } else if (!resourceIds.equals(other.resourceIds))
            return false;
        if (scope == null) {
            if (other.scope != null)
                return false;
        } else if (!scope.equals(other.scope))
            return false;
        if (additionalInformation == null) {
            if (other.additionalInformation != null)
                return false;
        } else if (!additionalInformation.equals(other.additionalInformation))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Client [clientId=" + clientId + ", clientSecret="
                + clientSecret + ", scope=" + scope + ", resourceIds="
                + resourceIds + ", authorizedGrantTypes="
                + authorizedGrantTypes + ", registeredRedirectUris="
                + registeredRedirectUris + ", authorities=" + authorities
                + ", accessTokenValiditySeconds=" + accessTokenValiditySeconds
                + ", refreshTokenValiditySeconds="
                + refreshTokenValiditySeconds + "]";
    }
}
