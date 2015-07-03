package org.blanc.whiteboard.rest.assembler.resource;

import com.tracebucket.tron.assembler.AssemblerResolver;
import com.tracebucket.tron.assembler.ResourceAssembler;
import org.blanc.whiteboard.domain.Authority;
import org.blanc.whiteboard.domain.Group;
import org.blanc.whiteboard.rest.resource.AuthorityResource;
import org.blanc.whiteboard.rest.resource.GroupResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

@Component
public class GroupResourceAssembler extends ResourceAssembler<GroupResource, Group> {

    @Autowired
    private AssemblerResolver assemblerResolver;

    @Override
    public GroupResource toResource(Group entity, Class<GroupResource> resourceClass) {
        GroupResource group = null;
        try {
            group = resourceClass.newInstance();
            if (entity != null) {
                group.setUid(entity.getEntityId().getId());
                group.setPassive(entity.isPassive());
                group.setDescription(entity.getDescription());
                group.setImage(entity.getImage());
                group.setName(entity.getName());
                group.setAuthorities(assemblerResolver.resolveResourceAssembler(AuthorityResource.class, Authority.class).toResources(entity.getAuthorities(), AuthorityResource.class));
                //group.setMembers(assemblerResolver.resolveResourceAssembler(UserResource.class, User.class).toResources(entity.getMembers(), UserResource.class));
            }
        } catch (InstantiationException ie) {

        } catch (IllegalAccessException iae) {

        }
        return group;
    }

    @Override
    public Set<GroupResource> toResources(Collection<Group> entities, Class<GroupResource> resourceClass) {
        Set<GroupResource> groups = new HashSet<GroupResource>();
        if(entities != null && entities.size() > 0) {
            Iterator<Group> iterator = entities.iterator();
            if(iterator.hasNext()) {
                Group group = iterator.next();
                groups.add(toResource(group, GroupResource.class));
            }
        }
        return groups;
    }
}