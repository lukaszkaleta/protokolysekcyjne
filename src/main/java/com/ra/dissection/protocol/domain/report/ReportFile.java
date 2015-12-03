package com.ra.dissection.protocol.domain.report;

import java.io.Serializable;

/**
 * @author lukaszkaleta
 * @since 09.06.13 06:44
 */
public class ReportFile implements Serializable {

    private static final long serialVersionUID = 2013060906440000001l;

    private long id;
    private long dissectionProtocolId;
    private byte[] data;
    private ReportPart reportPart;
    private ReportStatus reportStatus;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

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

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public ReportPart getReportPart() {
        return reportPart;
    }

    public void setReportPart(ReportPart reportPart) {
        this.reportPart = reportPart;
    }

    public ReportStatus getReportStatus() {
        return reportStatus;
    }

    public void setReportStatus(ReportStatus reportStatus) {
        this.reportStatus = reportStatus;
    }
}
