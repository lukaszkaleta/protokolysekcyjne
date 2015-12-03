package com.ra.dissection.protocol.domain.settings;

/**
 * @author lukaszkaleta
 * @since 28.04.13 21:44, 6.8.3.0-R04v33
 */
public class HospitalWard {

    /** Unique id */
    private long id;

    /** Id of hospital to which this ward belongs. */
    private long hospitalId;

    /** Name of the hospital ward. */
    private String name;

    /** Flag which determine if this ward has a dissection possibilities */
    private boolean dissection;

    /** Phone to hospital ward. */
    private String phone;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(long hospitalId) {
        this.hospitalId = hospitalId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isDissection() {
        return dissection;
    }

    public void setDissection(boolean dissection) {
        this.dissection = dissection;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
