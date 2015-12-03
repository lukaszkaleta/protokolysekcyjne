package com.ra.dissection.protocol.domain.report;

/**
 * @author lukaszkaleta
 * @since 08.06.13 17:40
 */
public enum ReportPart {

    /** Start page which has header and latin dissection diagnosis. */
    LATIN,
    /** Start page which has header and translated dissection diagnosis. */
    TRANSLATED,
    /** Report from second page. The actual content. */
    CONTENT,
    /** Full report. */
    FULL
}
