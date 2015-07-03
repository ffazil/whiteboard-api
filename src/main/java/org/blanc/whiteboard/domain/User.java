package org.blanc.whiteboard.domain;

import com.tracebucket.tron.ddd.domain.BaseEntity;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;

/**
 * @author FFL
 * @since 12-03-2015
 */
@Entity
@Table(name = "USER")
public class User extends BaseEntity implements UserDetails, CredentialsContainer, TenantAware{

    @Column(name = "PASSWORD", nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private String password;

    @Column(name = "USERNAME", unique = true, nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private String username;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name="USER_AUTHORITY",
            joinColumns={ @JoinColumn(name="USER__ID", referencedColumnName="ID") },
            inverseJoinColumns={ @JoinColumn(name="AUTHORITY__ID", referencedColumnName="ID"/*, unique=true*/) }
    )
    private Set<Authority> authorities = new HashSet<Authority>(0);

    @ManyToMany(mappedBy = "members", fetch = FetchType.EAGER)
    private Set<Group> groups = new HashSet<>(0);

    @Column(name = "ACCOUNT_NON_EXPIRED", columnDefinition = "boolean default false")
    @Basic(fetch = FetchType.EAGER)
    private boolean accountNonExpired;

    @Column(name = "ACCOUNT_NON_LOCKED", columnDefinition = "boolean default false")
    @Basic(fetch = FetchType.EAGER)
    private boolean accountNonLocked;

    @Column(name = "CREDENTIALS_NON_EXPIRED", columnDefinition = "boolean default false")
    @Basic(fetch = FetchType.EAGER)
    private boolean credentialsNonExpired;

    @Column(name = "ENABLED", columnDefinition = "boolean default false")
    @Basic(fetch = FetchType.EAGER)
    private boolean enabled;

    @ElementCollection(fetch = FetchType.EAGER)
    @MapKeyColumn(name = "MESSAGE")
    @Column(name = "TENANT_INFO", nullable = true)
    @CollectionTable(name = "USER_TENANT_INFORMATION", joinColumns = @JoinColumn(name = "USER__ID"))
    private Map<String, String> tenantInformation = new LinkedHashMap<String, String>();

    public User() {}

    public User(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        this(username, password, true, true, true, true, authorities);
    }

    public User(String username, String password, boolean enabled, boolean accountNonExpired,
                boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {

        if (((username == null) || "".equals(username)) || (password == null)) {
            throw new IllegalArgumentException("Cannot pass null or empty values to constructor");
        }

        Set<Authority> authorityList = new HashSet<Authority>();
        authorities.forEach(authority -> authorityList.add((Authority) authority));


        this.username = username;
        this.password = password;
        this.enabled = enabled;
        this.accountNonExpired = accountNonExpired;
        this.credentialsNonExpired = credentialsNonExpired;
        this.accountNonLocked = accountNonLocked;
        this.authorities = authorityList;
    }


    @Override
    public void eraseCredentials() {
        password = null;

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> grantedAuthorities = new HashSet<GrantedAuthority>();
        this.authorities.forEach(authority -> grantedAuthorities.add(authority));
        return grantedAuthorities;
    }

    public Set<Authority> getRawAuthorities() {
        return this.authorities;
    }

    public void setAuthorities(
            Collection<? extends GrantedAuthority> authorities) {
        Set<Authority> authorityList = new HashSet<>(0);
        authorities.forEach(authority -> authorityList.add((Authority) authority));
        this.authorities = authorityList;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setAuthorities(Set<Authority> authorities) {
        this.authorities = authorities;
    }

    public Set<Group> getGroups() {
        return groups;
    }

    public void setGroups(Set<Group> groups) {
        this.groups = groups;
        if(this.groups != null && this.groups.size() > 0) {
            for(Group group : groups) {
                group.getMembers().add(this);
                this.getGroups().add(group);
            }
        }
    }

    public void setAccountNonExpired(boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }



    @Override
    public boolean equals(Object rhs) {
        if (rhs instanceof User) {
            return username.equals(((User) rhs).username);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return username.hashCode();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString()).append(": ");
        sb.append("Username: ").append(this.username).append("; ");
        sb.append("Password: [PROTECTED]; ");
        sb.append("Enabled: ").append(this.enabled).append("; ");
        sb.append("AccountNonExpired: ").append(this.accountNonExpired).append("; ");
        sb.append("credentialsNonExpired: ").append(this.credentialsNonExpired).append("; ");
        sb.append("AccountNonLocked: ").append(this.accountNonLocked).append("; ");

        if (!authorities.isEmpty()) {
            sb.append("Granted Authorities: ");

            boolean first = true;
            for (GrantedAuthority auth : authorities) {
                if (!first) {
                    sb.append(",");
                }
                first = false;

                sb.append(auth);
            }
        } else {
            sb.append("Not granted any authorities");
        }

        return sb.toString();
    }

    @Override
    public Map<String, Object> getTenantInformation() {
        return Collections.unmodifiableMap(this.tenantInformation);
    }

    public void setAdditionalInformation(Map<String, String> tenantInformation) {
        this.tenantInformation = new LinkedHashMap<String, String>(
                tenantInformation);
    }

    public void addTenantInformation(String key, String value) {
        this.tenantInformation.put(key, value);
    }
}
