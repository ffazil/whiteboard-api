package org.blanc.whiteboard.rest.resource;

import org.blanc.whiteboard.domain.User;
import org.blanc.whiteboard.exception.AuthorizationException;
import org.blanc.whiteboard.repository.mongodb.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import javax.ws.rs.core.SecurityContext;

public class BaseResource {

    @Autowired
    private UserRepository userRepository;

    //TODO: Cache to reduce calls to userRepository
    protected User ensureUserIsAuthorized(SecurityContext securityContext, String userId) {
        User user = loadUserFromSecurityContext(securityContext);
        if (user != null && (user.getId().equals(userId) || user.getEmailAddress().equals(userId.toLowerCase()))) {
            return user;
        }
        throw new AuthorizationException("User not permitted to access this resource");

    }

    protected User loadUserFromSecurityContext(SecurityContext securityContext) {
        OAuth2Authentication requestingUser = (OAuth2Authentication) securityContext.getUserPrincipal();
        Object principal = requestingUser.getUserAuthentication().getPrincipal();
        User user = null;
        if(principal instanceof User) {
            user = (User)principal;
        } else {
            user = userRepository.findById((String)principal);
        }
        return user;
    }
}
