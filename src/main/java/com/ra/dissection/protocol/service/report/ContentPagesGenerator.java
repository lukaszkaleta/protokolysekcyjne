package com.ra.dissection.protocol.service.report;

import com.google.common.collect.Multimap;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.ra.dissection.protocol.dao.protocol.DissectionProtocolMapper;
import com.ra.dissection.protocol.dao.protocol.DescriptionPointMapper;
import com.ra.dissection.protocol.dao.protocol.HistopathologicalExaminationMapper;
import com.ra.dissection.protocol.domain.protocol.DescriptionPoint;
import com.ra.dissection.protocol.domain.protocol.DissectionProtocol;
import com.ra.dissection.protocol.domain.protocol.HistopathologicalExamination;
import com.ra.dissection.protocol.domain.report.AdultReport;
import com.ra.dissection.protocol.domain.report.DissectionProtocolReport;
import com.ra.dissection.protocol.domain.report.FetusReport;
import com.ra.dissection.protocol.domain.report.NewbornReport;
import com.ra.dissection.protocol.domain.settings.DescriptionPointSource;
import com.ra.dissection.protocol.service.report.components.ReportChunks;
import com.ra.dissection.protocol.service.report.components.ReportFonts;
import com.ra.dissection.protocol.service.report.components.ReportPage;
import com.ra.dissection.protocol.service.report.components.ReportSection;
import com.ra.dissection.protocol.service.support.DescriptionPointSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.ByteArrayOutputStream;
import java.util.*;
import java.util.List;

/**
 * @author lukaszkaleta
 * @since 08.06.13 22:19
 */
@Component
public class ContentPagesGenerator implements ReportPageGenerator, ITextGenerator {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private ReportFonts reportFonts;

    @Autowired
    private ReportSection protocolSection;

    @Autowired
    private ReportPage reportPage;

    @Override
    public byte[] generate(long reportId, DissectionProtocolReport dissectionProtocol) {
        return reportPage.createPage(dissectionProtocol, this);
    }

    @Override
    public void install(DissectionProtocolReport dissectionProtocol, PdfWriter instance) {
        String age = "";
        if (dissectionProtocol instanceof AdultReport) {
            age = "lat " + ((AdultReport) dissectionProtocol).getYearAge();
        }
        String header = String.format("Sekcja zw\u0142ok %s z dnia %s r. - %s %s",
                dissectionProtocol.getNumber(),
                ReportChunks.getDate(dissectionProtocol.getBasicData().getAutopsy().getDate()),
                dissectionProtocol.getBasicData().getPatient().getNiceName(),
                age
        );
        instance.setPageEvent(new HeaderFooter(header));
        instance.setBoxSize("art", new Rectangle(36, 54, 549, 815));
    }

    @Override
    public void setMargins(DissectionProtocolReport dissectionProtocol, Document document) {
        document.setMargins(20, 50, 50, 50);
    }


    public void addContent(DissectionProtocolReport dissectionProtocol, Document document) throws DocumentException {
        addDescriptionPoints(document, dissectionProtocol);
        addHistopatologicalExamination(document, dissectionProtocol);
        addClinicalData(document, dissectionProtocol);
        addMedicalPracticeAnalysis(document, dissectionProtocol);
    }

    private void addHistopatologicalExamination(Document document, DissectionProtocolReport dissectionProtocol) throws DocumentException {
        List<HistopathologicalExamination> histopathologicalExaminations = dissectionProtocol.getHistopathologicaExaminations();
        Collections.sort(histopathologicalExaminations, new Comparator<HistopathologicalExamination>() {
            @Override
            public int compare(HistopathologicalExamination o1, HistopathologicalExamination o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });

        for (HistopathologicalExamination histopathologicalExamination : histopathologicalExaminations) {
            switch (histopathologicalExamination.getName()) {
                case NORMAL:
                    String header = String.format("Badanie histopatologiczne nr %s (z dnia %s r.):", histopathologicalExamination.getNumber(), ReportChunks.getDate(histopathologicalExamination.getFromDay()));
                    addFormatedHistopathologicalExamination(document, header, histopathologicalExamination);
                    break;
                case DISSECTION_SECTOR:
                    header = String.format("Badanie histopatologiczne wycink\u00F3w sekcyjnych nr %s (z dnia %s r.):", histopathologicalExamination.getNumber(), ReportChunks.getDate(histopathologicalExamination.getFromDay()));
                    addFormatedHistopathologicalExamination(document, header, histopathologicalExamination);
                    break;
                case FETUS:
                    header = String.format("Badanie histopatologiczne pop\u0142odu nr %s (z dnia %s r.):", histopathologicalExamination.getNumber(), ReportChunks.getDate(histopathologicalExamination.getFromDay()));
                    protocolSection.addSimpleSection(document, header, histopathologicalExamination.getDescription());
                    break;
            }
        }
    }

    private void addFormatedHistopathologicalExamination(Document document, String header, HistopathologicalExamination histopathologicalExamination) throws DocumentException {
        addSingleSectionHeader(document, header);
        String description = histopathologicalExamination.getDescription();
        if (StringUtils.isEmpty(description)) {
            return;
        }
        String[] histopathologicalExaminations = description.split("\n");
        for (String singleHistopathologicalExamination : histopathologicalExaminations) {
            String[] split = singleHistopathologicalExamination.split("\\:");
            if (split.length > 1) {
                Paragraph paragraph = new Paragraph();
                paragraph.setIndentationLeft(38);
                paragraph.setFirstLineIndent(-14);
                Chunk chunk = new Chunk(split[0] + ":", reportFonts.getNormal(12));
                chunk.setUnderline(0.2f , -2f);
                paragraph.add(chunk);
                String text = "";
                for (int i = 1; i < split.length; i++) {
                    text += split[i];
                }
                paragraph.add(new Chunk(" " + ReportChunks.cleanPreposition(text), reportFonts.getNormal(12)));
                document.add(paragraph);
            } else {
                singleHistopathologicalExamination = ReportChunks.cleanPreposition(singleHistopathologicalExamination);
                protocolSection.addSimpleContent(document, singleHistopathologicalExamination);
            }
        }
    }

    private void addClinicalData(Document document, DissectionProtocolReport dissectionProtocol) throws DocumentException {
        protocolSection.addSimpleSection(document, "Dane kliniczne", ReportChunks.cleanPreposition(dissectionProtocol.getClinicalData()));
    }

    private void addMedicalPracticeAnalysis(Document document, DissectionProtocolReport dissectionProtocol) throws DocumentException {
        protocolSection.addSimpleSection(document, "Epikryza", ReportChunks.cleanPreposition(dissectionProtocol.getMedicalPracticeAnalysis()));
    }

    private void addSingleSectionHeader(Document document, String header) throws DocumentException {
        Paragraph paragraph = new Paragraph(header, reportFonts.getBoldItalic(13));
        paragraph.setSpacingBefore(10);
        paragraph.setSpacingAfter(5);
        document.add(paragraph);
    }

    private void addDescriptionPoints(Document document, DissectionProtocolReport dissectionProtocol) throws DocumentException {
        Multimap<Integer,DescriptionPoint> integerDescriptionPointMap = dissectionProtocol.getDescriptionPoints();
        for (Integer point : integerDescriptionPointMap.keySet()) {
            Collection<DescriptionPoint> pointDescriptionPoints = integerDescriptionPointMap.get(point);
            StringBuilder descriptionPointBuilder = new StringBuilder(String.valueOf(point));
            descriptionPointBuilder.append(". ");
            for (DescriptionPoint descriptionPoint : pointDescriptionPoints) {
                String description = descriptionPoint.getDescriptionPointSource().getDescription();
                description = ReportChunks.cleanPreposition(description);
                descriptionPointBuilder.append(description + " ");
            }
            protocolSection.addIndentContent(document, descriptionPointBuilder.toString(), point - 10 < 0 ? -14 : -21);
        }
    }

    class HeaderFooter extends PdfPageEventHelper {

        String header;
        int pagenumber = 1;

        public HeaderFooter(String header) {
            this.header = header;
        }

        public void onStartPage(PdfWriter writer, Document document) {
            pagenumber++;
        }

        public void onEndPage(PdfWriter writer, Document document) {
            Rectangle rect = writer.getBoxSize("art");

            float top = rect.getTop();
            float right;
            if (pagenumber % 2 == 0) {
                right = rect.getRight();
            } else {
                right = rect.getRight() + 30;
            }

            Paragraph paragraph = new Paragraph(header, reportFonts.getItalic(12));
            paragraph.setAlignment(Element.ALIGN_JUSTIFIED_ALL);
            ColumnText.showTextAligned(writer.getDirectContent(),
                    Element.ALIGN_RIGHT, paragraph,
                    right, top, 0);

            ColumnText.showTextAligned(writer.getDirectContent(),
                    Element.ALIGN_CENTER, new Phrase(String.format("- %d -", pagenumber), reportFonts.getNormal(10)),
                    (rect.getLeft() + rect.getRight()) / 2, rect.getBottom() - 18, 0);
        }
    }
}
