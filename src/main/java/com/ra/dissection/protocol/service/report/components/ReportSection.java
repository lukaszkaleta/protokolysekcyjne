package com.ra.dissection.protocol.service.report.components;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.ra.dissection.protocol.service.report.components.ReportFonts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Provides basic possibility for section.
 *
 * @author lukaszkaleta
 * @since 11.06.13 19:56
 */
@Component
public class ReportSection {

    private final ReportFonts reportFonts;

    @Autowired
    public ReportSection(ReportFonts reportFonts) {
        this.reportFonts = reportFonts;
    }

    public void addSimpleSection(Document document, String header, String content) throws DocumentException {
        addHeader(document, header);
        addSimpleContent(document, content);
    }

    public void addLineHeader(Document document, String header) throws DocumentException {
        addSeparator(document);
        Paragraph paragraph = new Paragraph(header, reportFonts.getBoldItalic(13));
        paragraph.setSpacingBefore(-4);
        paragraph.setSpacingAfter(0);
        document.add(paragraph);
    }

    public void addHeader(Document document, String header) throws DocumentException {
        Paragraph paragraph = new Paragraph(header, reportFonts.getBoldItalic(13));
        paragraph.setSpacingBefore(10);
        paragraph.setSpacingAfter(5);
        document.add(paragraph);
    }

    public void addSimpleContent(Document document, String content) throws DocumentException {
        Paragraph paragraph = new Paragraph(content, reportFonts.getNormal(12));
        paragraph.setAlignment(Element.ALIGN_JUSTIFIED);
        paragraph.setIndentationLeft(24);
        document.add(paragraph);
    }

    public void addIndentContent(Document document, String content, int indent) throws DocumentException {
        Paragraph descriptionPointParagraph = new Paragraph(content, reportFonts.getNormal(12));
        descriptionPointParagraph.setIndentationLeft(38);
        descriptionPointParagraph.setFirstLineIndent(indent);
        descriptionPointParagraph.setAlignment(Element.ALIGN_JUSTIFIED);
        document.add(descriptionPointParagraph);
    }

    public void addSeparator(Document document) throws DocumentException {
        addSeparator(document, 11);
    }

    public void addSeparator(Document document, int space) throws DocumentException {
        LineSeparator lineSeparator = new LineSeparator();
        Paragraph paragraph = new Paragraph();
        paragraph.setLeading(space);
        paragraph.add(new Chunk(lineSeparator));
        document.add(paragraph);
    }
}
