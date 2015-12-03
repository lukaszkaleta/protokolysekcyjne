package com.ra.dissection.protocol.service.impl;

import com.ra.dissection.protocol.dao.UserSearchMapper;
import com.ra.dissection.protocol.dao.security.UserAccountMapper;
import com.ra.dissection.protocol.dao.security.UserAuthorityMapper;
import com.ra.dissection.protocol.domain.security.UserAccount;
import com.ra.dissection.protocol.domain.security.UserAuthority;
import com.ra.dissection.protocol.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author lukaszkaleta
 * @since 04.05.13 23:19
 */
@Service(value = "accountService")
public class AccountServiceImpl implements AccountService, UserDetailsService {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserAccountMapper userAccountMapper;

    @Autowired
    private UserAuthorityMapper userAuthorityMapper;

    @Autowired
    private UserSearchMapper userSearchMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserAccount userAccount = loadUserAccount(username);
        if (userAccount == null) {
            return null;
        }

        List<UserAuthority> userAuthorities = userAuthorityMapper.selectAuthorityByUsername(username);
        Collection<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
        for (UserAuthority userAuthority : userAuthorities) {
            grantedAuthorities.add(new SimpleGrantedAuthority(userAuthority.getAuthority().name()));
        }

        return new User(username, userAccount.getPassword(), true, true, true, true, grantedAuthorities);
    }

    public UserAccount loadUserAccount(String username) {
        try {
            return userAccountMapper.selectUserByUsername(username);
        } catch (Exception e) {
            log.error("Error while selecting user by user name: " + e.getMessage(), e);
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void changePassword(String username, String newPassword) {
        UserAccount userAccount = userAccountMapper.selectUserByUsername(username);
        if (userAccount == null) {
            log.warn("Change password aborted since username {} does not exists", username);
            return;
        }
        userAccount.setPassword(newPassword);
        userAccountMapper.updateUserPassword(userAccount);
    }

    @Override
    public List<UserAccount> loadAllAccounts() {
        List<UserAccount> userAccounts = userAccountMapper.selectUserAccounts();
        for (UserAccount userAccount : userAccounts) {
            List<UserAuthority> userAuthorities = userAuthorityMapper.selectAuthorityByUsername(userAccount.getUsername());
            for (UserAuthority userAuthority : userAuthorities) {
                userAccount.addRole(userAuthority.getAuthority());
            }
        }
        return userAccounts;
    }

    @Override
    public boolean deleteAccount(String username) {
        List<UserAuthority> userAuthorities = userAuthorityMapper.selectAuthorityByUsername(username);
        boolean isAdmin = false;
        for (UserAuthority userAuthority : userAuthorities) {
            if (UserAuthority.Name.ADMIN.equals(userAuthority.getAuthority())) {
                isAdmin = true;
                break;
            }
        }
        if (isAdmin) {
            int count = userAuthorityMapper.countAdmins();
            if (count <= 1) {
                return false;
            }
        }
        userSearchMapper.deleteUserSearch(username);
        userAuthorityMapper.deleteUserAuthority(username);
        userAccountMapper.deleteUserAccount(username);
        return true;
    }

    public void createUserAccount(String username, Set<UserAuthority.Name> roles) {
        UserAccount userAccount = new UserAccount(username, username);
        userAccountMapper.insertUserAccount(userAccount);
        for (UserAuthority.Name role : roles) {
            UserAuthority userAuthority = new UserAuthority(username, role);
            userAuthorityMapper.insertUserAuthority(userAuthority);
        }
    }
}
