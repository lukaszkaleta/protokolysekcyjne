package com.ra.dissection.protocol.domain.security;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lukaszkaleta
 * @since 04.05.13 23:34
 */
public class UserAccount {

    private String username;
    private String password;
    private boolean enabled;

    private List<UserAuthority.Name> roles = new ArrayList<UserAuthority.Name>();

    public UserAccount() {
    }

    public UserAccount(String username, String password) {
        this.username = username;
        this.password = password;
        this.enabled = true;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void addRole(UserAuthority.Name role) {
        this.roles.add(role);
    }

    public void setRoles(List<UserAuthority.Name> roles) {
        this.roles = roles;
    }

    public List<UserAuthority.Name> getRoles() {
        return roles;
    }
}
