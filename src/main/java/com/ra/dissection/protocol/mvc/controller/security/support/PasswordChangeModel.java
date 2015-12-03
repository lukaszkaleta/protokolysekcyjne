package com.ra.dissection.protocol.mvc.controller.security.support;

/**
 * @author lukaszkaleta
 * @since 06.07.13 08:54
 */
public class PasswordChangeModel {

    private String username;
    private String currentPassword;
    private String newPassword;
    private String confirmPassword;

    public PasswordChangeModel() {
    }

    public PasswordChangeModel(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
