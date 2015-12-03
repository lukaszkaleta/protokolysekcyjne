package com.ra.dissection.protocol.dao.protocol;

import com.ra.dissection.protocol.domain.report.ReportFile;
import com.ra.dissection.protocol.domain.report.ReportInfo;

import java.util.Map;

/**
 * @author lukaszkaleta
 * @since 6.8.4.0-R04v44 08.06.13 22:55
 */
public interface ReportMapper {

    void insertEmptyReport(long newDissectionProtocolId);

    /**
     * Select report information for specified dissection protocol id.
     *
     * @param dissectionProtocolId id of dissection protocol.
     *
     * @return information about report.
     */
    ReportInfo selectReportInfo(long dissectionProtocolId);

    /**
     * Updates latin start page.
     * After this method full report update need to be called too.
     *
     * @param latinStartPageReportFile report file which contains latin start page.
     */
    void updateLatinStartPage(ReportFile latinStartPageReportFile);

    /**
     * Updates translated start page.
     * After this method full report update need to be called too.
     *
     * @param translatedReportFile report file which contains translated start page.
     */
    void updateTranslatedStartPage(ReportFile translatedReportFile);

    /**
     * Updates content pages.
     * After this method full report update need to be called too.
     *
     * @param contentPagesReportFile report file which contains content pages.
     */
    void updateContentPages(ReportFile contentPagesReportFile);

    /**
     * Updates full report.
     *
     * @param fullReportFile report file which contains full report (all pages)
     */
    void updateFullReport(ReportFile fullReportFile);

    /**
     * Select report file for specified dissection protocol which contains full report.
     *
     * @param dissectionProtocolId id of dissection protocol.
     *
     * @return report file with full report.
     */
    ReportFile selectFullReportData(long dissectionProtocolId);

    ReportFile selectLatinStartPagePart(long id);

    ReportFile selectTranslatedStartPagePart(long id);

    ReportFile selectContentPagesPart(long id);

    /**
     * Update status of report.
     *
     * @param statusUpdateMap contains two elements: status and id.
     */
    void updateStatus(Map<String, Object> statusUpdateMap);

    void deleteDissectionProtocolReport(long dissectionProtocolId);
}
