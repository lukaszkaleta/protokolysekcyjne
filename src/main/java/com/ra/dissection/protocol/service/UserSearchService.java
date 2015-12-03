package com.ra.dissection.protocol.service;

import com.ra.dissection.protocol.domain.search.UserSearch;

/**
 * @author lukaszkaleta
 * @since 25.04.13 22:32
 */
public interface UserSearchService {
    UserSearch accept(UserSearch userSearch);

    UserSearch current(String username);

    void reset(String username);
}
