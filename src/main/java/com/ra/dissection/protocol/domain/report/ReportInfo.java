package com.ra.dissection.protocol.domain.report;

import java.io.Serializable;

/**
 * @author lukaszkaleta
 * @since 08.06.13 23:09
 */
public class ReportInfo implements Serializable {

    private static final long serialVersionUID = 2013060823090000001l;

    private long id;

    private long dissectionProtocolId;

    private ReportStatus reportStatus;

    private boolean hasLatinStartPage;

    private boolean hasTranslatedStartPage;

    private boolean hasContentPages;

    private boolean hasReport;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getDissectionProtocolId() {
        return dissectionProtocolId;
    }

    public void setDissectionProtocolId(long dissectionProtocolId) {
        this.dissectionProtocolId = dissectionProtocolId;
    }

    public ReportStatus getReportStatus() {
        return reportStatus;
    }

    public void setReportStatus(ReportStatus reportStatus) {
        this.reportStatus = reportStatus;
    }

    public boolean isHasLatinStartPage() {
        return hasLatinStartPage;
    }

    public void setHasLatinStartPage(boolean hasLatinStartPage) {
        this.hasLatinStartPage = hasLatinStartPage;
    }

    public boolean isHasTranslatedStartPage() {
        return hasTranslatedStartPage;
    }

    public void setHasTranslatedStartPage(boolean hasTranslatedStartPage) {
        this.hasTranslatedStartPage = hasTranslatedStartPage;
    }

    public boolean isHasContentPages() {
        return hasContentPages;
    }

    public void setHasContentPages(boolean hasContentPages) {
        this.hasContentPages = hasContentPages;
    }

    public boolean isHasReport() {
        return hasReport;
    }

    public void setHasReport(boolean hasReport) {
        this.hasReport = hasReport;
    }
}
