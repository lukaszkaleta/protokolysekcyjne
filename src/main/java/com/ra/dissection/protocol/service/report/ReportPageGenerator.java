package com.ra.dissection.protocol.service.report;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import com.ra.dissection.protocol.domain.report.DissectionProtocolReport;

/**
 * @author lukaszkaleta
 * @since 08.06.13 22:21
 */
public interface ReportPageGenerator {

    byte[] generate(long reportId, DissectionProtocolReport dissectionProtocol);
}
