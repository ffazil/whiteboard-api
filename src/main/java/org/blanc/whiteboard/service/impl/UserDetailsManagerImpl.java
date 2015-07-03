package org.blanc.whiteboard.service.impl;

import org.blanc.whiteboard.domain.Authority;
import org.blanc.whiteboard.domain.Group;
import org.blanc.whiteboard.domain.User;
import org.blanc.whiteboard.repository.jpa.AuthorityRepository;
import org.blanc.whiteboard.repository.jpa.GroupRepository;
import org.blanc.whiteboard.repository.jpa.UserRepository;
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
 * @since 16-03-2015
 */
@Service("userDetailsManagerImpl")
@Transactional
public class UserDetailsManagerImpl implements UserDetailsManager, GroupManager{
    private static final Logger logger = LoggerFactory.getLogger(UserDetailsManagerImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private AuthorityRepository authorityRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    private UserCache userCache = new NullUserCache();

    private boolean enableAuthorities = true;

    @Override
    public List<String> findAllGroups() {
        List<String> groupNames = new ArrayList<>(0);
        List<Group> groups = groupRepository.findAll();
        groups.forEach(group -> groupNames.add(group.getName()));
        return groupNames;
    }

    @Override
    public List<String> findUsersInGroup(String groupName) {
        Assert.hasText(groupName);
        List<String> userNames = new ArrayList<>(0);
        Set<User> users = groupRepository.findByName(groupName).getMembers();
        users.forEach(user -> userNames.add(user.getUsername()));
        return userNames;
    }

    @Override
    public void createGroup(String groupName, List<GrantedAuthority> authorities) {
        Assert.hasText(groupName);
        Assert.notNull(authorities);

        Group group = new Group();
        group.setName(groupName);

        if(authorities != null && authorities.size() > 0) {
            List<String> authorityNames = new ArrayList<>(0);
            authorities.forEach(authority -> authorityNames.add(authority.getAuthority()));
            List<Authority> authorityList = authorityRepository.findByRoleIn(authorityNames);
            group.getAuthorities().addAll(authorityList);
        }
        group = groupRepository.save(group);
    }

    @Override
    public void deleteGroup(String groupName) {
        Assert.hasText(groupName);
        Group group = groupRepository.findByName(groupName);
        if(group.getMembers() != null) {
            group.getMembers().stream().forEach(u -> u.getGroups().remove(group));
        }
        groupRepository.save(group);
        groupRepository.deleteByName(groupName);
    }

    @Override
    public void renameGroup(String oldName, String newName) {
        Assert.hasText(oldName);
        Assert.hasText(newName);

        Group group = groupRepository.findByName(oldName);
        group.setName(newName);
        group = groupRepository.save(group);

    }

    public Group findGroupByName(String name) {
        return  groupRepository.findByName(name);
    }

    @Override
    public void addUserToGroup(String username, String groupName) {
        Assert.hasText(username);
        Assert.hasText(groupName);

        User user = userRepository.findByUsername(username);
        Group group = groupRepository.findByName(groupName);

        user.getGroups().add(group);
        group.getMembers().add(user);
        user = userRepository.save(user);
    }

    @Override
    public void removeUserFromGroup(String username, String groupName) {
        Assert.hasText(username);
        Assert.hasText(groupName);

        Group group = groupRepository.findByName(groupName);
        User user = userRepository.findByUsername(username);
        user.getGroups().remove(group);
        group.getMembers().remove(user);
        groupRepository.save(group);
    }

    @Override
    public List<GrantedAuthority> findGroupAuthorities(String groupName) {
        Assert.hasText(groupName);

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>(0);
        Group group = groupRepository.findByName(groupName);
        group.getAuthorities().forEach(authority -> grantedAuthorities.add(authority));
        return grantedAuthorities;
    }

    @Override
    public void addGroupAuthority(String groupName, GrantedAuthority grantedAuthority) {
        Assert.hasText(groupName);
        Assert.notNull(grantedAuthority);

        Group group = groupRepository.findByName(groupName);
        Authority authority = authorityRepository.findByRole(grantedAuthority.getAuthority());
        group.getAuthorities().add(authority);
        group = groupRepository.save(group);
    }

    @Override
    public void removeGroupAuthority(String groupName, GrantedAuthority authority) {
        logger.debug("Removing authority '" + authority + "' from group '" + groupName + "'");
        Assert.hasText(groupName);
        Assert.notNull(authority);
        Authority authority1 = authorityRepository.findByRole(((Authority)authority).getAuthority());
        Group group = groupRepository.findByName(groupName);
        group.getAuthorities().remove(authority1);
        groupRepository.save(group);
    }

    @Override
    public void createUser(UserDetails user) {
        validateUserDetails(user);
        userRepository.save((User)user);
        if (getEnableAuthorities()) {
            insertUserAuthorities(user);
        }
    }

    @Override
    public void updateUser(UserDetails user) {
        validateUserDetails(user);
        userRepository.save((User)user);
        if (getEnableAuthorities()) {
            deleteUserAuthorities(user.getUsername());
            insertUserAuthorities(user);
        }
        userCache.removeUserFromCache(user.getUsername());
    }

    private void insertUserAuthorities(UserDetails user) {
        User user1 = userRepository.findByUsername(user.getUsername());
        for (GrantedAuthority auth : user.getAuthorities()) {
            Authority authority = authorityRepository.findByRole(((Authority)auth).getAuthority());
            user1.getRawAuthorities().add(authority);
        }
        userRepository.save(user1);
    }

    private void validateUserDetails(UserDetails user) {
        Assert.hasText(user.getUsername(), "Username may not be empty or null");
        validateAuthorities(user.getAuthorities());
    }

    private void validateAuthorities(Collection<? extends GrantedAuthority> authorities) {
        Assert.notNull(authorities, "Authorities list must not be null");

        for (GrantedAuthority authority : authorities) {
            Assert.notNull(authority, "Authorities list contains a null entry");
            Assert.hasText(authority.getAuthority(), "getAuthority() method must return a non-empty string");
        }
    }

    @Override
    public void deleteUser(String username) {
        if (getEnableAuthorities()) {
            deleteUserAuthorities(username);
        }
        User user = userRepository.findByUsername(username);
        user.getGroups().stream().forEach(g -> {g.getMembers().remove(user);});
        userRepository.save(user);
        userRepository.deleteByUsername(username);
        userCache.removeUserFromCache(username);
    }

    private void deleteUserAuthorities(String username) {
        User user = userRepository.findByUsername(username);
        user.setAuthorities(new HashSet<Authority>(0));
        userRepository.save(user);
    }

    protected boolean getEnableAuthorities() {
        return enableAuthorities;
    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {
        Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
        if (currentUser == null) {
            // This would indicate bad coding somewhere
            throw new AccessDeniedException("Can't change password as no Authentication object found in context " +
                    "for current user.");
        }
        String username = currentUser.getName();
        // If an authentication manager has been set, re-authenticate the user with the supplied password.
        if (authenticationManager != null) {
            logger.debug("Reauthenticating user '"+ username + "' for password change request.");

            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, oldPassword));
        } else {
            logger.debug("No authentication manager set. Password won't be re-checked.");
        }

        logger.debug("Changing password for user '"+ username + "'");

/*        User user = (User)loadUserByUsername(currentUser.getName());
        user.setPassword(newPassword);
        this.updateUser(user);*/
        userRepository.updatePassword(newPassword, username);

        SecurityContextHolder.getContext().setAuthentication(createNewAuthentication(currentUser, newPassword));

        userCache.removeUserFromCache(username);
    }

    public UserDetails getCurrentUser() {
        Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
        if (currentUser == null) {
            // This would indicate bad coding somewhere
            throw new AccessDeniedException("Can't change password as no Authentication object found in context " +
                    "for current user.");
        }
        User user = userRepository.findByUsername(currentUser.getName());
        org.springframework.security.core.userdetails.User u = new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), user.isEnabled(), user.isAccountNonExpired(),user.isCredentialsNonExpired(), user.isAccountNonLocked(), user.getAuthorities());
        return u;
    }

    public void setUserCache(UserCache userCache) {
        Assert.notNull(userCache, "userCache cannot be null");
        this.userCache = userCache;
    }

    protected Authentication createNewAuthentication(Authentication currentAuth, String newPassword) {
        UserDetails user = loadUserByUsername(currentAuth.getName());

        UsernamePasswordAuthenticationToken newAuthentication =
                new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        newAuthentication.setDetails(currentAuth.getDetails());

        return newAuthentication;
    }

    @Override
    public boolean userExists(String username) {
        return userRepository.findByUsername(username) != null ? true : false;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

                User user = userRepository.findByUsername(username);
        //org.springframework.security.core.userdetails.User u = new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), user.isEnabled(), user.isAccountNonExpired(),user.isCredentialsNonExpired(), user.isAccountNonLocked(), user.getAuthorities());
        return user;
    }

    protected UserDetails createUserDetails(String username, UserDetails userFromUserQuery,
                                            List<GrantedAuthority> combinedAuthorities) {
        String returnUsername = userFromUserQuery.getUsername();

        return new org.springframework.security.core.userdetails.User(returnUsername, userFromUserQuery.getPassword(), userFromUserQuery.isEnabled(),
                true, true, true, combinedAuthorities);
    }


}
