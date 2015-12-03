package com.ra.dissection.protocol.service;

import com.ra.dissection.protocol.domain.security.UserAccount;
import com.ra.dissection.protocol.domain.security.UserAuthority;

import java.util.List;
import java.util.Set;

/**
 * @author lukaszkaleta
 * @since 04.05.13 23:19
 */
public interface AccountService {

    UserAccount loadUserAccount(String username);

    void createUserAccount(String username, Set<UserAuthority.Name> roles);

    void changePassword(String username, String newPassword);

    List<UserAccount> loadAllAccounts();

    boolean deleteAccount(String username);
}
