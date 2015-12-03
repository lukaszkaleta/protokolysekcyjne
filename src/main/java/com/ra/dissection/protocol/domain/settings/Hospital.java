package com.ra.dissection.protocol.domain.settings;

import com.ra.dissection.protocol.domain.common.Address;

import java.io.Serializable;

/**
 * @author lukaszkaleta
 * @since 25.04.13 11:11
 */
public class Hospital implements Serializable {

    private long id;

    private String name;

    private Address address = new Address();

    private String phone;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
