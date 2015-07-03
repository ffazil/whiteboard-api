package org.blanc.whiteboard.rest.controller;

import com.tracebucket.tron.assembler.AssemblerResolver;
import org.blanc.whiteboard.domain.Authority;
import org.blanc.whiteboard.rest.resource.AuthorityResource;
import org.blanc.whiteboard.service.impl.AuthorityServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * Created by sadath on 13-May-15.
 */
@RestController(value = "idemAuthorityController")
@RequestMapping("/admin")
public class AuthorityController {

    @Autowired
    private AssemblerResolver assemblerResolver;

    @Autowired
    private AuthorityServiceImpl authorityServiceImpl;

    @RequestMapping(value = "/authority", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthorityResource> save(@RequestBody AuthorityResource authorityResource) {
        Authority authority = assemblerResolver.resolveEntityAssembler(Authority.class, AuthorityResource.class).toEntity(authorityResource, Authority.class);
        authority = authorityServiceImpl.save(authority);
        if(authority != null) {
            authorityResource = assemblerResolver.resolveResourceAssembler(AuthorityResource.class, Authority.class).toResource(authority, AuthorityResource.class);
            return new ResponseEntity<AuthorityResource>(authorityResource, HttpStatus.OK);
        }
        return new ResponseEntity<AuthorityResource>(new AuthorityResource(), HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/authority", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthorityResource> update(@RequestBody AuthorityResource authorityResource) {
        Authority authority = assemblerResolver.resolveEntityAssembler(Authority.class, AuthorityResource.class).toEntity(authorityResource, Authority.class);
        authority = authorityServiceImpl.save(authority);
        if(authority != null) {
            authorityResource = assemblerResolver.resolveResourceAssembler(AuthorityResource.class, Authority.class).toResource(authority, AuthorityResource.class);
            return new ResponseEntity<AuthorityResource>(authorityResource, HttpStatus.OK);
        }
        return new ResponseEntity<AuthorityResource>(new AuthorityResource(), HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/authority/{uid}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthorityResource> findOne(@PathVariable("uid") String uid) {
        Authority authority = authorityServiceImpl.findOne(uid);
        if(authority != null) {
            AuthorityResource authorityResource = assemblerResolver.resolveResourceAssembler(AuthorityResource.class, Authority.class).toResource(authority, AuthorityResource.class);
            return new ResponseEntity<AuthorityResource>(authorityResource, HttpStatus.OK);
        }
        return new ResponseEntity<AuthorityResource>(new AuthorityResource(), HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/authorities", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Set<AuthorityResource>> findAll() {
        List<Authority> authorities = authorityServiceImpl.findAll();
        if(authorities != null && authorities.size() > 0) {
            Set<AuthorityResource> authorityResources = assemblerResolver.resolveResourceAssembler(AuthorityResource.class, Authority.class).toResources(authorities, AuthorityResource.class);
            return new ResponseEntity<Set<AuthorityResource>>(authorityResources, HttpStatus.OK);
        }
        return new ResponseEntity<Set<AuthorityResource>>(Collections.emptySet(), HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/authority/{uid}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> delete(@PathVariable("uid") String uid) {
        return new ResponseEntity<Boolean>(authorityServiceImpl.delete(uid), HttpStatus.OK);
    }

    @RequestMapping(value = "/authorities", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> deleteAll() {
        return new ResponseEntity<Boolean>(authorityServiceImpl.deleteAll(), HttpStatus.OK);
    }
}