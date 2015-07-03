package org.blanc.whiteboard.rest.controller;

import com.tracebucket.tron.assembler.AssemblerResolver;
import org.blanc.whiteboard.domain.Authority;
import org.blanc.whiteboard.domain.Group;
import org.blanc.whiteboard.rest.resource.AuthorityResource;
import org.blanc.whiteboard.rest.resource.GroupResource;
import org.blanc.whiteboard.service.impl.UserDetailsManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@RestController(value = "idemGroupController")
@RequestMapping("/admin")
public class GroupController {

    @Autowired
    private AssemblerResolver assemblerResolver;

    @Autowired
    private UserDetailsManagerImpl userDetailsManagerImpl;

    @RequestMapping(value = "/group", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GroupResource> createGroup(@RequestBody GroupResource groupResource) {
        Group group = assemblerResolver.resolveEntityAssembler(Group.class, GroupResource.class).toEntity(groupResource, Group.class);
        List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
        if(group.getAuthorities() != null && group.getAuthorities().size() > 0) {
            group.getAuthorities().stream().forEach(authority -> grantedAuthorities.add(authority));
        }
        userDetailsManagerImpl.createGroup(group.getName(), grantedAuthorities);
        group = userDetailsManagerImpl.findGroupByName(group.getName());
        if(group != null) {
            groupResource = assemblerResolver.resolveResourceAssembler(GroupResource.class, Group.class).toResource(group, GroupResource.class);
            return new ResponseEntity<GroupResource>(groupResource, HttpStatus.OK);
        }
        return new ResponseEntity<GroupResource>(new GroupResource(), HttpStatus.NOT_ACCEPTABLE);
    }

    @RequestMapping(value = "/groups", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<String>> findAllGroups() {
        List<String> groups = userDetailsManagerImpl.findAllGroups();
        if(groups != null && groups.size() > 0) {
            return new ResponseEntity<List<String>>(groups, HttpStatus.OK);
        }
        return new ResponseEntity<List<String>>(Collections.emptyList(), HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/group/{groupName}/users", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<String>> findUsersInGroup(@PathVariable("groupName") String groupName) {
        List<String> usersInGroup = userDetailsManagerImpl.findUsersInGroup(groupName);
        if(usersInGroup != null && usersInGroup.size() > 0) {
            return new ResponseEntity<List<String>>(usersInGroup, HttpStatus.OK);
        }
        return new ResponseEntity<List<String>>(Collections.emptyList(), HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/group/{groupName}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> deleteGroup(@PathVariable("groupName") String groupName) {
        userDetailsManagerImpl.deleteGroup(groupName);
        Group group = userDetailsManagerImpl.findGroupByName(groupName);
        return new ResponseEntity<Boolean>(group == null ? true : false, HttpStatus.OK);
    }

    @RequestMapping(value = "/group/{oldName}/{newName}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GroupResource> renameGroup(@PathVariable("oldName") String oldName, @PathVariable("newName") String newName) {
        userDetailsManagerImpl.renameGroup(oldName, newName);
        Group group = userDetailsManagerImpl.findGroupByName(newName);
        if(group != null) {
            GroupResource groupResource = assemblerResolver.resolveResourceAssembler(GroupResource.class, Group.class).toResource(group, GroupResource.class);
            return new ResponseEntity<GroupResource>(groupResource, HttpStatus.OK);
        }
        return new ResponseEntity<GroupResource>(new GroupResource(), HttpStatus.NOT_ACCEPTABLE);
    }

    @RequestMapping(value = "/group/{groupName}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GroupResource> findGroupByName(@PathVariable("groupName") String groupName) {
        Group group = userDetailsManagerImpl.findGroupByName(groupName);
        if(group != null) {
            GroupResource groupResource = assemblerResolver.resolveResourceAssembler(GroupResource.class, Group.class).toResource(group, GroupResource.class);
            return new ResponseEntity<GroupResource>(groupResource, HttpStatus.OK);
        }
        return new ResponseEntity<GroupResource>(new GroupResource(), HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/group/{groupName}/authorities", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Set<AuthorityResource>> findGroupAuthorities(@PathVariable("groupName") String groupName) {
        List<GrantedAuthority> groupAuthorities = userDetailsManagerImpl.findGroupAuthorities(groupName);
        List<Authority> authorities = new ArrayList<Authority>();
        if(groupAuthorities != null && groupAuthorities.size() > 0) {
            groupAuthorities.stream().forEach(authority -> authorities.add((Authority)authority));
            Set<AuthorityResource> authorityResources = assemblerResolver.resolveResourceAssembler(AuthorityResource.class, Authority.class).toResources(authorities, AuthorityResource.class);
            return new ResponseEntity<Set<AuthorityResource>>(authorityResources, HttpStatus.OK);
        }
        return new ResponseEntity<Set<AuthorityResource>>(Collections.emptySet(), HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/group/{groupName}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GroupResource> addGroupAuthority(@PathVariable("groupName") String groupName, @RequestBody AuthorityResource authorityResource) {
        Authority authority = assemblerResolver.resolveEntityAssembler(Authority.class, AuthorityResource.class).toEntity(authorityResource, Authority.class);
        userDetailsManagerImpl.addGroupAuthority(groupName, authority);
        Group group = userDetailsManagerImpl.findGroupByName(groupName);
        if(group != null) {
            GroupResource groupResource = assemblerResolver.resolveResourceAssembler(GroupResource.class, Group.class).toResource(group, GroupResource.class);
            return new ResponseEntity<GroupResource>(groupResource, HttpStatus.OK);
        }
        return new ResponseEntity<GroupResource>(new GroupResource(), HttpStatus.NOT_ACCEPTABLE);
    }

    @RequestMapping(value = "/group/{groupName}/authority", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GroupResource> removeGroupAuthority(@PathVariable("groupName") String groupName, @RequestBody AuthorityResource authorityResource) {
        Authority authority = assemblerResolver.resolveEntityAssembler(Authority.class, AuthorityResource.class).toEntity(authorityResource, Authority.class);
        userDetailsManagerImpl.removeGroupAuthority(groupName, authority);
        Group group = userDetailsManagerImpl.findGroupByName(groupName);
        if(group != null) {
            GroupResource groupResource = assemblerResolver.resolveResourceAssembler(GroupResource.class, Group.class).toResource(group, GroupResource.class);
            return new ResponseEntity<GroupResource>(groupResource, HttpStatus.OK);
        }
        return new ResponseEntity<GroupResource>(new GroupResource(), HttpStatus.NOT_ACCEPTABLE);
    }
}