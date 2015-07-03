package org.blanc.whiteboard.rest.assembler.entity;

import com.tracebucket.tron.assembler.EntityAssembler;
import com.tracebucket.tron.ddd.domain.EntityId;
import org.blanc.whiteboard.domain.Authority;
import org.blanc.whiteboard.rest.resource.AuthorityResource;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by sadath on 13-May-15.
 */
@Component
public class AuthorityEntityAssembler extends EntityAssembler<Authority, AuthorityResource> {
    @Override
    public Authority toEntity(AuthorityResource resource, Class<Authority> entityClass) {
        Authority authority = null;
        if(resource != null) {
            authority = new Authority();
            if (resource.getUid() != null) {
                authority.setEntityId(new EntityId(resource.getUid()));
            }
            authority.setRole(resource.getRole());
            authority.setPassive(resource.isPassive());
        }
        return authority;
    }

    @Override
    public Set<Authority> toEntities(Collection<AuthorityResource> resources, Class<Authority> entityClass) {
        Set<Authority> authorities = new HashSet<Authority>();
        if(resources != null) {
            Iterator<AuthorityResource> iterator = resources.iterator();
            if(iterator.hasNext()) {
                AuthorityResource authorityResource = iterator.next();
                authorities.add(toEntity(authorityResource, Authority.class));
            }
        }
        return authorities;
    }
}