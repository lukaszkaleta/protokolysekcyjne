package com.ra.dissection.protocol.domain.common;

import java.io.Serializable;

/**
 * @author lukaszkaleta
 * @since 25.04.13 13:50
 */
public class Range<T extends Serializable> implements Serializable {

    private T from;
    private T to;

    public Range() {
    }

    public Range(T defaultValue) {
        this(defaultValue, defaultValue);
    }

    public Range(T from, T to) {
        this.from = from;
        this.to = to;
    }

    public T getFrom() {
        return from;
    }

    public void setFrom(T from) {
        this.from = from;
    }

    public T getTo() {
        return to;
    }

    public void setTo(T to) {
        this.to = to;
    }
}
