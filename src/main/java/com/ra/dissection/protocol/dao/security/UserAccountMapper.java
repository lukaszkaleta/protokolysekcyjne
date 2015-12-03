package com.ra.dissection.protocol.dao.security;

import com.ra.dissection.protocol.domain.security.UserAccount;

import java.util.List;
import java.util.Map;

/**
 * @author lukaszkaleta
 * @since 03.05.13 06:16
 */
public interface UserAccountMapper {

    void insertUserAccount(UserAccount userAccount);

    void updateUsername(Map<String, String> updateUsernameMap);

    void updateUserPassword(UserAccount userAccount);

    void deleteUserAccount(String username);

    UserAccount selectUserByUsername(String username);

    List<UserAccount> selectUserAccounts();
}
