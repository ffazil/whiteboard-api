package org.blanc.whiteboard.service;

import org.blanc.whiteboard.rest.resource.ApiUser;
import org.blanc.whiteboard.rest.resource.CreateUserRequest;
import org.blanc.whiteboard.rest.resource.UpdateUserRequest;

public interface UserService {

    public ApiUser createUser(final CreateUserRequest createUserRequest);

    public ApiUser authenticate(String username, String password);

    public ApiUser getUser(String userId);

    /**
     * Save User
     *
     * @param userId
     * @param request
     */
    public ApiUser saveUser(String userId, UpdateUserRequest request);

}
