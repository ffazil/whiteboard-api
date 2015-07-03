package org.blanc.whiteboard.rest.controller;

import com.tracebucket.tron.assembler.AssemblerResolver;
import org.blanc.whiteboard.domain.User;
import org.blanc.whiteboard.rest.resource.UserResource;
import org.blanc.whiteboard.service.impl.UserDetailsManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by sadath on 29-Apr-15.
 */
@RestController(value = "idemUserController")
@RequestMapping("/admin")
public class UserController {
    @Autowired
    private AssemblerResolver assemblerResolver;

    @Autowired
    private UserDetailsManagerImpl userDetailsManagerImpl;

    @RequestMapping(value = "/user", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResource> createUser(@RequestBody UserResource userResource) {
        User user = assemblerResolver.resolveEntityAssembler(User.class, UserResource.class).toEntity(userResource, User.class);
        userDetailsManagerImpl.createUser(user);
        user = (User)userDetailsManagerImpl.loadUserByUsername(user.getUsername());
        if(user != null) {
            userResource = assemblerResolver.resolveResourceAssembler(UserResource.class, User.class).toResource(user, UserResource.class);
            return new ResponseEntity<UserResource>(userResource, HttpStatus.OK);
        }
        return new ResponseEntity<UserResource>(new UserResource(), HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/user", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResource> updateUser(@RequestBody UserResource userResource) {
        User user = assemblerResolver.resolveEntityAssembler(User.class, UserResource.class).toEntity(userResource, User.class);
        userDetailsManagerImpl.updateUser(user);
        user = (User)userDetailsManagerImpl.loadUserByUsername(user.getUsername());
        if(user != null) {
            userResource = assemblerResolver.resolveResourceAssembler(UserResource.class, User.class).toResource(user, UserResource.class);
            return new ResponseEntity<UserResource>(userResource, HttpStatus.OK);
        }
        return new ResponseEntity<UserResource>(new UserResource(), HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/user/password", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResource> changePassword(@RequestParam("oldPassword") String oldPassword, @RequestParam("newPassword") String newPassword) {
        userDetailsManagerImpl.changePassword(oldPassword, newPassword);
        User user = (User)userDetailsManagerImpl.getCurrentUser();
        if(user != null) {
            UserResource userResource = assemblerResolver.resolveResourceAssembler(UserResource.class, User.class).toResource(user, UserResource.class);
            return new ResponseEntity<UserResource>(userResource, HttpStatus.OK);
        }
        return new ResponseEntity<UserResource>(new UserResource(), HttpStatus.NOT_ACCEPTABLE);
    }

    @RequestMapping(value = "/user/{userName}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> deleteUser(@PathVariable("userName") String userName) {
        userDetailsManagerImpl.deleteUser(userName);
        User user = (User)userDetailsManagerImpl.loadUserByUsername(userName);
        return new ResponseEntity<Boolean>(user == null ? true : false, HttpStatus.OK);
    }

    @RequestMapping(value = "/user/{userName}/exists", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> userExists(@PathVariable("userName") String userName) {
        return new ResponseEntity<Boolean>(userDetailsManagerImpl.userExists(userName), HttpStatus.OK);
    }

    @RequestMapping(value = "/user/{userName}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResource> loadUserByUsername(@PathVariable("userName") String userName) {
        User user = (User)userDetailsManagerImpl.loadUserByUsername(userName);
        if(user != null) {
            UserResource userResource = assemblerResolver.resolveResourceAssembler(UserResource.class, User.class).toResource(user, UserResource.class);
            return new ResponseEntity<UserResource>(userResource, HttpStatus.OK);
        }
        return new ResponseEntity<UserResource>(new UserResource(), HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/user/{userName}/group/{groupName}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResource> addUserToGroup(@PathVariable("userName") String userName, @PathVariable("groupName") String groupName) {
        userDetailsManagerImpl.addUserToGroup(userName, groupName);
        User user = (User)userDetailsManagerImpl.loadUserByUsername(userName);
        if(user != null) {
            UserResource userResource = assemblerResolver.resolveResourceAssembler(UserResource.class, User.class).toResource(user, UserResource.class);
            return new ResponseEntity<UserResource>(userResource, HttpStatus.OK);
        }
        return new ResponseEntity<UserResource>(new UserResource(), HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/user/{userName}/group/{groupName}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResource> removeUserFromGroup(@PathVariable("userName") String userName, @PathVariable("groupName") String groupName) {
        userDetailsManagerImpl.removeUserFromGroup(userName, groupName);
        User user = (User)userDetailsManagerImpl.loadUserByUsername(userName);
        if(user != null) {
            UserResource userResource = assemblerResolver.resolveResourceAssembler(UserResource.class, User.class).toResource(user, UserResource.class);
            return new ResponseEntity<UserResource>(userResource, HttpStatus.OK);
        }
        return new ResponseEntity<UserResource>(new UserResource(), HttpStatus.NOT_FOUND);
    }
}