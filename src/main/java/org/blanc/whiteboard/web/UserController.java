package org.blanc.whiteboard.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.security.Principal;

/**
 * @author ffl
 * @since 11-03-2015
 */
@RestController
@SessionAttributes("authorizationRequest")
public class UserController {

    @RequestMapping("/user")
    public Principal user(Principal user) {
        return user;
    }
}
