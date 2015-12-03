package com.ra.dissection.protocol.domain.common;

import java.io.Serializable;

/**
 * @author lukaszkaleta
 * @since 25.04.13 21:23
 */
public class Address implements Serializable {

    private String value;

    private String postCode;

    private String city;

    private String province;

    private String country;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
