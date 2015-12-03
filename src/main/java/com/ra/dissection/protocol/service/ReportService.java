package com.ra.dissection.protocol.service;

import com.ra.dissection.protocol.domain.report.ReportStatus;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author lukaszkaleta
 * @since 13.05.13 22:38
 */
public interface ReportService {

    /**
     * Update status of report from dissection protocol.
     * This method should be called only and only if dissection protocol is changed.
     *
     * @param dissectionProtocolId id of dissection protocol
     * @param requiredStatus new status which is required.
     */
    void updateStatus(long dissectionProtocolId, ReportStatus requiredStatus);

    /**
     * Generate report (if it is needed)
     *
     * @param dissectionProtocolId id of dissection protocol for which we generate report.
     */
    void generateIfNeeded(long dissectionProtocolId);

    /**
     * Generate report.
     *
     * @param dissectionProtocolId
     */
    void generate(long dissectionProtocolId);

    /**
     * Input stream which gets full report.
     *
     * @param dissectionProtocolId
     * @return
     */
    InputStream getFullReport(long dissectionProtocolId);

    /**
     * Generate report name.
     *
     * @param dissectionProtocolId
     * @return
     */
    String getReportName(long dissectionProtocolId);
}
