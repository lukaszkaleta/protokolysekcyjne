package com.ra.dissection.protocol.domain.common;

import java.io.Serializable;

/**
 * @author lukaszkaleta
 * @since 27.04.13 07:28
 */
public class Time implements Serializable {

    private static final long serialVersionUID = 2013042707280000001l;

    private Integer value;

    public Time() {
    }

    public Time(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public Integer getHour() {
        if (value != null) {
            return value / 60;
        } else {
            return null;
        }
    }

    public Integer getMinute() {
        Integer hour = getHour();
        if (hour != null) {
            return value - (hour * 60);
        } else {
            return null;
        }
    }

    public String getMinuteAsText() {
        Integer minute = getMinute();
        if (minute == null) {
            return null;
        } else {
            return String.format("%02d", minute);
        }
    }
    public String getHoursAsText() {
        Integer hour = getHour();
        if (hour == null) {
            return null;
        } else {
            return String.format("%02d", hour);
        }
    }

    public Integer getMilliseconds() {
        if (value == null) {
            return null;
        } else {
            return value * 60 * 1000;
        }
    }

    @Override
    public String toString() {
        return "Time{" +
                "value=" + value +
                '}';
    }
}
