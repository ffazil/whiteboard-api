package org.blanc.whiteboard.rest.assembler.resource;

import com.tracebucket.tron.assembler.ResourceAssembler;
import org.blanc.whiteboard.domain.Authority;
import org.blanc.whiteboard.rest.resource.AuthorityResource;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

@Component
public class AuthorityResourceAssembler extends ResourceAssembler<AuthorityResource, Authority> {
    @Override
    public AuthorityResource toResource(Authority entity, Class<AuthorityResource> resourceClass) {
        AuthorityResource authorityResource = null;
        try {
            authorityResource = resourceClass.newInstance();
            if (entity != null) {
                authorityResource.setUid(entity.getEntityId().getId());
                authorityResource.setRole(entity.getRole());
                authorityResource.setPassive(entity.isPassive());
            }
        } catch (InstantiationException ie) {

        } catch (IllegalAccessException iae) {

        }
        return authorityResource;
    }

    @Override
    public Set<AuthorityResource> toResources(Collection<Authority> entities, Class<AuthorityResource> resourceClass) {
        Set<AuthorityResource> authorities = new HashSet<AuthorityResource>();
        if(entities != null && entities.size() > 0) {
            Iterator<Authority> iterator = entities.iterator();
            if(iterator.hasNext()) {
                Authority authority = iterator.next();
                authorities.add(toResource(authority, AuthorityResource.class));
            }
        }
        return authorities;
    }
}