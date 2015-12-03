package com.ra.dissection.protocol.service.report.components;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfWriter;
import com.ra.dissection.protocol.domain.protocol.DissectionProtocol;
import com.ra.dissection.protocol.domain.report.DissectionProtocolReport;
import com.ra.dissection.protocol.service.report.ITextGenerator;
import com.ra.dissection.protocol.service.report.ReportPageGenerator;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;

/**
 * @author lukaszkaleta
 * @since 11.06.13 21:08
 */
@Component
public class ReportPage {

    public void init(DissectionProtocolReport dissectionProtocolReport, ITextGenerator iTextGenerator, Document document) {
        document.setPageSize(PageSize.A4);
        document.setMarginMirroring(true);
        iTextGenerator.setMargins(dissectionProtocolReport, document);
        document.open();
    }

    public byte[] createPage(DissectionProtocolReport dissectionProtocolReport, ITextGenerator iTextGenerator) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Document document = new Document();
        PdfWriter instance = null;
        try {
            instance = PdfWriter.getInstance(document, byteArrayOutputStream);

            iTextGenerator.install(dissectionProtocolReport, instance);
            init(dissectionProtocolReport, iTextGenerator, document);
            iTextGenerator.addContent(dissectionProtocolReport, document);

            document.close();
        } catch (DocumentException e) {
        }

        return byteArrayOutputStream.toByteArray();
    }
}
