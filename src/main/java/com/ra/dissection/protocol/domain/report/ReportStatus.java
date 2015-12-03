package com.ra.dissection.protocol.domain.report;

/**
 * @author lukaszkaleta
 * @since 6.8.4.0-R04v44 08.06.13 22:46
 */
public enum ReportStatus {

    GENERATING(false),

    ACTUAL(false),

    NEED_FIRST_PAGE(true),

    NEED_CONTENT_PAGES(true),

    NEED_ALL(true);

    /**
     * Flag which determine if status can be set when dissection protocol changes.
     */
    private final boolean updateOnReportChange;

    private ReportStatus(boolean updateOnReportChange) {
        this.updateOnReportChange = updateOnReportChange;
    }

    public boolean isUpdateOnReportChange() {
        return updateOnReportChange;
    }
}
