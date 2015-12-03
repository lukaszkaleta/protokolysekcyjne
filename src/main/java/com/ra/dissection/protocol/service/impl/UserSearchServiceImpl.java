package com.ra.dissection.protocol.service.impl;

import com.ra.dissection.protocol.dao.UserSearchMapper;
import com.ra.dissection.protocol.domain.search.UserSearch;
import com.ra.dissection.protocol.service.UserSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lukaszkaleta
 * @since 26.04.13 14:34
 */
@Service
public class UserSearchServiceImpl implements UserSearchService {

    @Autowired
    private UserSearchMapper userSearchMapper;

    @Override
    public UserSearch accept(UserSearch userSearch) {
        long userSearchId = userSearch.getId();
        if (userSearchId <= 0) {
            userSearchMapper.insertSearch(userSearch);
            userSearchId = userSearch.getId();
        } else {
            userSearchMapper.updateSearch(userSearch);
        }
        userSearch = userSearchMapper.selectSearch(userSearchId);
        if (userSearch == null) {

        }
        return userSearch;
    }

    @Override
    public UserSearch current(String username) {
        List<UserSearch> userSearch = userSearchMapper.selectUserSearch(username);
        if (userSearch.isEmpty()) {
            return new UserSearch();
        } else {
            return userSearch.get(userSearch.size() - 1);
        }
    }

    @Override
    public void reset(String username) {
        List<UserSearch> userSearches = userSearchMapper.selectUserSearch(username);
        for (UserSearch userSearch : userSearches) {
            userSearchMapper.deleteSearch(userSearch.getId());
        }
    }
}
