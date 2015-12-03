package com.ra.dissection.protocol.service.report;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import com.ra.dissection.protocol.domain.protocol.DissectionProtocol;
import com.ra.dissection.protocol.domain.report.DissectionProtocolReport;

/**
 * @author lukaszkaleta
 * @since 11.06.13 21:17
 */
public interface ITextGenerator {

    void install(DissectionProtocolReport dissectionProtocol, PdfWriter instance);

    void addContent(DissectionProtocolReport dissectionProtocol, Document document) throws DocumentException;

    void setMargins(DissectionProtocolReport dissectionProtocol, Document document);
}
