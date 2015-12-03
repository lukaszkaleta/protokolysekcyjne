package com.ra.dissection.protocol.dao.security;

import com.ra.dissection.protocol.domain.security.UserAuthority;

import java.util.List;
import java.util.Map;

/**
 * @author lukaszkaleta
 * @since 04.05.13 23:59
 */
public interface UserAuthorityMapper {

    void insertUserAuthority(UserAuthority userAuthority);

    void updateUserAuthority(UserAuthority userAuthority);

    void deleteUserAuthority(String username);

    List<UserAuthority> selectAuthorityByUsername(String username);

    List<UserAuthority> selectAllUserAuthority();

    void updateUsername(Map<String, String> updateUsernameMap);

    int countAdmins();
}
