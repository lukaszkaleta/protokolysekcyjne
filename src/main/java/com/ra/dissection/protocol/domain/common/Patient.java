package com.ra.dissection.protocol.domain.common;

import java.io.Serializable;
import java.util.Date;

/**
 * @author lukaszkaleta
 * @since 28.04.13 21:22
 */
public class Patient implements Serializable {

    private static final long serialVersionUID = 2804201321220000001l;

    private String firstName;

    private String lastName;

    private String identificationNumber;

    private String yearsAge;

    private Date birthDate;

    private String description;

    private Address address = new Address();

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

    public String getIdentificationNumber() {
        return identificationNumber;
    }

    public void setIdentificationNumber(String identificationNumber) {
        this.identificationNumber = identificationNumber;
    }

    public String getYearsAge() {
        return yearsAge;
    }

    public void setYearsAge(String yearsAge) {
        this.yearsAge = yearsAge;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getNiceName() {
        String niceName = "";
        if (lastName != null) {
            niceName += lastName + " ";
        }
        if (firstName != null) {
            niceName += firstName + " ";
        }
        if (niceName.trim().isEmpty()) {
            niceName += description;
        }
        niceName = niceName.trim();
        return niceName;
    }
}
