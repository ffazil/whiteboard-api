package org.blanc.whiteboard.domain;

import com.tracebucket.tron.ddd.domain.BaseEntity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @author ffl
 * @since 16-03-2015
 */
@Entity(name = "IDEM_GROUP")
@Table(name = "IDEM_GROUP")
public class Group extends BaseEntity {

    @Column(name = "NAME", unique = true, nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private String name;

    @Column(name = "DESCRIPTION")
    @Basic(fetch = FetchType.EAGER)
    private String description;

    @Column(name = "IMAGE")
    @Basic(fetch = FetchType.EAGER)
    private String image;

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinTable(
            name = "GROUP_AUTHORITY",
            joinColumns = { @JoinColumn(name = "GROUP__ID", referencedColumnName = "ID") },
            inverseJoinColumns={ @JoinColumn(name = "AUTHORITY__ID", referencedColumnName = "ID", unique = true) }
    )
    private Set<Authority> authorities = new HashSet<>(0);

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinTable(
            name = "GROUP_USER",
            joinColumns = { @JoinColumn(name = "GROUP__ID", referencedColumnName = "ID") },
            inverseJoinColumns={ @JoinColumn(name = "USER__ID", referencedColumnName = "ID", unique = true) }
    )
    private Set<User> members = new HashSet<>(0);

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Set<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<Authority> authorities) {
        this.authorities = authorities;
    }

    public Set<User> getMembers() {
        return members;
    }

    public void setMembers(Set<User> members) {
        this.members = members;
    }
}
