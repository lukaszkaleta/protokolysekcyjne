package com.ra.dissection.protocol.dao;

import com.ra.dissection.protocol.domain.search.UserSearch;

import java.util.List;

/**
 * @author lukaszkaleta
 * @since 25.04.13 22:32
 */
public interface UserSearchMapper {

    void insertSearch(UserSearch userSearch);

    void updateSearch(UserSearch userSearch);

    UserSearch selectSearch(long id);

    List<UserSearch> selectUserSearch(String username);

    void deleteSearch(long id);

    void deleteUserSearch(String username);
}
