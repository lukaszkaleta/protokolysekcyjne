package com.ra.dissection.protocol.domain.settings;

/**
 * @author lukaszkaleta
 * @since 11.05.13 06:53
 */
public enum DescriptionPointType {

    /** This is default type of description point. */
    GENERAL,
    /** This description point will have diagnose from which he was created. */
    DIAGNOSE,
    /** This description point will mean that either GENERAL or DIAGNOSE original description was changed. It applies only to runtime data. */
    CUSTOM
}
