package com.ra.dissection.protocol.domain.protocol;

import com.ra.dissection.protocol.domain.common.Time;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

/**
 * @author lukaszkaleta
 * @since 01.05.13 09:25
 */
public class Autopsy implements Serializable {

    private static final long serialVersionUID = 2013050109250000001l;

    private Long doctorExecutorId;

    private String doctorExecutorPresence;

    private Date date;

    private Time time = new Time();

    public Long getDoctorExecutorId() {
        return doctorExecutorId;
    }

    public void setDoctorExecutorId(Long doctorExecutorId) {
        this.doctorExecutorId = doctorExecutorId;
    }

    public String getDoctorExecutorPresence() {
        return doctorExecutorPresence;
    }

    public void setDoctorExecutorPresence(String doctorExecutorPresence) {
        this.doctorExecutorPresence = doctorExecutorPresence;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public Date getDateTime() {
        if (date == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        if (time != null) {
            Integer value = time.getValue();
            if (value != null) {
                calendar.set(Calendar.MINUTE, value);
            }
        }
        return calendar.getTime();
    }
}
