package com.ra.dissection.protocol.domain.security;

/**
 * @author lukaszkaleta
 * @since 04.05.13 23:55
 */
public class UserAuthority {

    public enum Name {
        ADMIN,
        USER
    }

    private String username;
    private Name authority;

    public UserAuthority() {
    }

    public UserAuthority(String username, Name authority) {
        this.username = username;
        this.authority = authority;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Name getAuthority() {
        return authority;
    }

    public void setAuthority(Name authority) {
        this.authority = authority;
    }
}
