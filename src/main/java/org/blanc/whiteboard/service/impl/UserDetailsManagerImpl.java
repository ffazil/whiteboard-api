package org.blanc.whiteboard.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserCache;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.cache.NullUserCache;
import org.springframework.security.provisioning.GroupManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.*;

/**
 * @author ffl
 * @since 11-06-2015
 */
@Service("userDetailsManagerImpl")
@Transactional
public class UserDetailsManagerImpl implements UserDetailsManager{
    private static final Logger logger = LoggerFactory.getLogger(UserDetailsManagerImpl.class);

    public void createUser(UserDetails userDetails) {

    }

    public void updateUser(UserDetails userDetails) {

    }

    public void deleteUser(String s) {

    }

    public void changePassword(String s, String s1) {

    }


    public boolean userExists(String s) {
        return false;
    }

    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return null;
    }
}
