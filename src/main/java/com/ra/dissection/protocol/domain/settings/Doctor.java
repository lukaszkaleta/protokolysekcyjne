package com.ra.dissection.protocol.domain.settings;

import java.io.Serializable;

/**
 * @author lukaszkaleta
 * @since 25.04.13 11:08
 */
public class Doctor implements Serializable {

    private static final Long serialVersionUID = 25041311080000001l;

    private long id;

    private String firstName;

    private String lastName;

    private String title;

    private String phone;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return firstName + " " + lastName;
    }
}
