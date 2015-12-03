package com.ra.dissection.protocol.service.report;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.ra.dissection.protocol.domain.common.Patient;
import com.ra.dissection.protocol.domain.protocol.*;
import com.ra.dissection.protocol.domain.report.AdultReport;
import com.ra.dissection.protocol.domain.report.DissectionProtocolReport;
import com.ra.dissection.protocol.domain.report.FetusReport;
import com.ra.dissection.protocol.domain.report.NewbornReport;
import com.ra.dissection.protocol.domain.settings.Doctor;
import com.ra.dissection.protocol.domain.settings.Hospital;
import com.ra.dissection.protocol.domain.settings.HospitalWard;
import com.ra.dissection.protocol.service.report.components.*;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.List;

/**
 * @author lukaszkaleta
 * @since 08.06.13 22:17
 */
abstract class StartPage implements ITextGenerator {

    @Autowired
    protected ReportFonts reportFonts;

    @Autowired
    protected ReportSection reportSection;

    @Autowired
    protected ReportPage reportPage;

    protected abstract Map<Long, String> getDissectionDiagnoseValues(DissectionProtocolReport dissectionProtocol);

    protected abstract Collection<String> getDissectionDiagnoseOptionValues(Long dissectionDiagnoseId, DissectionProtocolReport dissectionProtocol);

    @Override
    public void install(DissectionProtocolReport dissectionProtocol, PdfWriter instance) {
    }

    @Override
    public void setMargins(DissectionProtocolReport dissectionProtocol, Document document) {
        document.setMargins(50, 20, 24, 24);
    }

    @Override
    public void addContent(DissectionProtocolReport dissectionProtocol, Document document) throws DocumentException {
        addCreator(document, dissectionProtocol);
        addProtocolNumber(document, dissectionProtocol);
        addAutopsy(document, dissectionProtocol);
        addDeathHospital(document, dissectionProtocol);
        addPatient(document, dissectionProtocol);
        addDeathStoryEntries(document, dissectionProtocol);
        addClinicalDiagnose(document, dissectionProtocol);
        addDissectionDiagnose(document, dissectionProtocol);
    }

    private void addCreator(Document document, DissectionProtocolReport dissectionProtocol) throws DocumentException {
        PdfPTable table = new PdfPTable(3);
        float x = 65;
        table.setWidthPercentage(new float[]{x, x, PageSize.A4.getWidth() - (x*2)}, PageSize.A4);
        table.getDefaultCell().setBorder(0);
        addCentrallyAlignedImage(dissectionProtocol.getHospitalImage(), table);
        addCentrallyAlignedImage(dissectionProtocol.getHospitalWardImage(), table);

        PdfPTable pdfPTable = new PdfPTable(1);
        pdfPTable.getDefaultCell().setVerticalAlignment(Element.ALIGN_TOP);
        pdfPTable.getDefaultCell().setBorder(0);
        PdfPCell pdfPCell = new PdfPCell();
        pdfPCell.setBackgroundColor(BaseColor.BLUE);
        pdfPCell.setBorder(0);
        if (dissectionProtocol.getHospitalWard() != null) {
            Paragraph paragraph = new Paragraph(dissectionProtocol.getHospitalWard().getName(), reportFonts.getWhiteBoldFont(12));
            paragraph.setAlignment(Element.ALIGN_CENTER);
            paragraph.setLeading(8);
            paragraph.setSpacingAfter(0);
            pdfPCell.addElement(paragraph);
        }
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell();
        pdfPCell.setBorder(0);
        Hospital hospital = dissectionProtocol.getHospital();
        if (hospital != null) {
            Paragraph paragraph = new Paragraph();
            paragraph.setLeading(10);
            paragraph.add(new Chunk(hospital.getName(), reportFonts.getBlueBoldItalic(10)));
            paragraph.add(Chunk.NEWLINE);
            paragraph.add(new Chunk(hospital.getAddress().getValue(), reportFonts.getBlueBoldItalic(10)));
            paragraph.add(Chunk.NEWLINE);
            paragraph.add(new Chunk(hospital.getAddress().getPostCode() + " " + hospital.getAddress().getCity(), reportFonts.getBlueBoldItalic(10)));
            pdfPCell.addElement(paragraph);

            paragraph = new Paragraph();
            paragraph.setLeading(0);
            paragraph.add(new Chunk("tel: " + hospital.getPhone(), reportFonts.getBlueBoldItalic(10)));
            paragraph.setAlignment(Element.ALIGN_RIGHT);
            pdfPCell.addElement(paragraph);
        }
        pdfPTable.addCell(pdfPCell);

        table.addCell(pdfPTable);
        document.add(table);
    }

    private void addCentrallyAlignedImage(byte[] img, PdfPTable table) {
        try {
            if (img != null) {
                Jpeg jpeg = new Jpeg(img);
                jpeg.setScaleToFitHeight(true);
                jpeg.setScaleToFitLineWhenOverflow(false);
                PdfPCell pdfPCell = new PdfPCell();
                pdfPCell.setFixedHeight(50);
                pdfPCell.setBorder(0);
                pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                pdfPCell.addElement(jpeg);
                table.addCell(pdfPCell);
            } else {
                table.addCell("x");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addProtocolNumber(Document document, DissectionProtocolReport dissectionProtocol) throws DocumentException {
        PdfPTable table = new PdfPTable(1);
        table.setWidthPercentage(100);
        table.setSpacingAfter(6);

        PdfPCell pdfPCell = new PdfPCell();
        pdfPCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        pdfPCell.setBorder(0);
        pdfPCell.setBorderColorTop(BaseColor.BLACK);
        pdfPCell.setBorderWidthTop(1);
        pdfPCell.setPaddingBottom(2);

        String text = "PROTOK\u00D3\u0141 SEKCYJNY ";
        Paragraph paragraph = new Paragraph(text + dissectionProtocol.getNumber(), reportFonts.getBold(16));
        paragraph.setAlignment(Element.ALIGN_CENTER);
        paragraph.setLeading(13);
        pdfPCell.addElement(paragraph);
        table.addCell(pdfPCell);
        document.add(table);
    }

    private void addAutopsy(Document document, DissectionProtocolReport dissectionProtocol) throws DocumentException {
        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100);
        table.setSpacingAfter(2);

        PdfPCell pdfPCell = new PdfPCell();
        pdfPCell.setBorder(0);
        Paragraph paragraph = new Paragraph("Lekarz wykonuj\u0105cy sekcj\u0119:", reportFonts.getBoldItalic(10));
        paragraph.setLeading(4);
        pdfPCell.addElement(paragraph);
        Doctor doctor = dissectionProtocol.getAutopsyDoctor();
        if (doctor != null) {
            paragraph = new Paragraph(doctor.getName(), reportFonts.getBoldItalic(12));
            paragraph.setAlignment(Element.ALIGN_CENTER);
            pdfPCell.addElement(paragraph);
        }
        table.addCell(pdfPCell);

        pdfPCell = new PdfPCell();
        pdfPCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        paragraph = new Paragraph("Autopsja wykonana w dniu: " + ReportChunks.getDate(dissectionProtocol.getBasicData().getAutopsy().getDate()) + " r.", reportFonts.getBoldItalic(13));
        paragraph.setLeading(9);
        pdfPCell.addElement(paragraph);
        String autopsyAfterText = "Godzin po ";
        if (dissectionProtocol instanceof FetusReport) {
            autopsyAfterText = autopsyAfterText + "porodzie dziecka martwego: " + ((FetusReport) dissectionProtocol).getHoursAfterDeathChildBirth();
        } else if (dissectionProtocol instanceof AdultReport) {
            autopsyAfterText = autopsyAfterText + "zgonie: " + ((AdultReport) dissectionProtocol).getHoursAfterDeath();
        } else if (dissectionProtocol instanceof NewbornReport) {
            autopsyAfterText = autopsyAfterText + "zgonie: " + ((NewbornReport) dissectionProtocol).getHoursAfterDeath();
        }
        pdfPCell.addElement(new Paragraph(autopsyAfterText, reportFonts.getBoldItalic(11)));
        table.addCell(pdfPCell);
        document.add(table);
    }

    private void addPatient(Document document, DissectionProtocolReport dissectionProtocol) throws DocumentException {
        Patient patient = dissectionProtocol.getBasicData().getPatient();
        Paragraph paragraph = new Paragraph();
        paragraph.setLeading(19);
        paragraph.add(new Chunk("Nazwisko i imi\u0119, wiek: ", reportFonts.getItalic(10)));
        paragraph.add(new Chunk(patient.getNiceName(), reportFonts.getBoldItalic(15)));
        if (dissectionProtocol instanceof AdultReport) {
            String yearAge = ((AdultReport) dissectionProtocol).getYearAge();
            paragraph.add(new Chunk(" lat " + yearAge, reportFonts.getBoldItalic(15)));
            paragraph.add(Chunk.NEWLINE);
            paragraph.add(new Chunk("                        PESEL: ", reportFonts.getItalic(10)));
        } else if (dissectionProtocol instanceof NewbornReport) {
            // nothing
            paragraph.add(Chunk.NEWLINE);
            paragraph.add(new Chunk("               PESEL matki: ", reportFonts.getItalic(10)));
        } else if (dissectionProtocol instanceof FetusReport) {
//            paragraph.add(Chunk.NEWLINE);
//            paragraph.add(new Chunk("                                 wewnątrz maciczne obumarcie płodu", reportFonts.getBoldItalic(12)));
            paragraph.add(new Chunk("   wewn\u0105trzmaciczne obumarcie p\u0142odu", reportFonts.getBoldItalic(9)));
            paragraph.add(Chunk.NEWLINE);
            paragraph.add(new Chunk("               PESEL matki: ", reportFonts.getItalic(10)));
        }
        if (patient.getIdentificationNumber() != null) {
            paragraph.add(new Chunk(patient.getIdentificationNumber(), reportFonts.getBoldItalic(15)));
        }
        document.add(paragraph);

        paragraph = new Paragraph();
        paragraph.setLeading(10);
        paragraph.setAlignment(Element.ALIGN_RIGHT);
        paragraph.setSpacingBefore(-10f);
        paragraph.setSpacingAfter(-5f);
        paragraph.add(new Chunk("Obecno\u015B\u0107 lekarza klinicznego przy sekcji:    ", reportFonts.getItalic(10)));
        paragraph.add(Chunk.NEWLINE);
        paragraph.add(new Chunk(dissectionProtocol.getBasicData().getAutopsy().getDoctorExecutorPresence(), reportFonts.getBoldItalic(10)));
        document.add(paragraph);

        paragraph = new Paragraph();
        paragraph.setLeading(10);
        paragraph.add(new Chunk("Adres: ", reportFonts.getItalic(10)));
        String address = String.format("%s %s %s", patient.getAddress().getPostCode(), patient.getAddress().getCity(), patient.getAddress().getValue());
        paragraph.add(new Chunk(address, reportFonts.getBoldItalic(12)));
        document.add(paragraph);
    }

    private void addDeathHospital(Document document, DissectionProtocolReport dissectionProtocol) throws DocumentException {
        reportSection.addSeparator(document, 3);
        Hospital deathHospital = dissectionProtocol.getDeathHospital();

        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(new float[]{PageSize.A4.getWidth() - 150, 150f}, PageSize.A4);
        table.setWidthPercentage(100);

        PdfPCell hospitalCell = new PdfPCell();
        hospitalCell.setBorder(0);
        if (deathHospital != null) {
            Paragraph hospitalParagraph = new Paragraph(deathHospital.getName(), reportFonts.getBoldItalic(12));
            hospitalParagraph.setIndentationLeft(-2);
            hospitalParagraph.setLeading(12);
            hospitalCell.addElement(hospitalParagraph);
        }
        table.addCell(hospitalCell);


        String bookNumber = "";
        DeathStory.BookType bookType = dissectionProtocol.getBasicData().getDeathStory().getBookType();
        if (bookType != null) {
            switch (bookType) {
                case AM:
                    bookNumber += "L. Ks. Amb. ";
                    break;
                case MAIN:
                    bookNumber += "L. Ks. Gł. ";
                    break;
            }
        }
        String bookNumberValue = dissectionProtocol.getBasicData().getDeathStory().getBookNumber();
        if (!StringUtils.isEmpty(bookNumberValue)) {
            bookNumber += bookNumberValue;
        }

        Paragraph bookNumberParagraph = new Paragraph(bookNumber, reportFonts.getBoldItalic(12));
        bookNumberParagraph.setAlignment(Element.ALIGN_RIGHT);
        bookNumberParagraph.setLeading(12);
        PdfPCell bookNumberCell = new PdfPCell();
        bookNumberCell.setBorder(0);
        bookNumberCell.addElement(bookNumberParagraph);
        table.addCell(bookNumberCell);

        document.add(table);

        List<HospitalWard> hospitalWards = dissectionProtocol.getDeathHospitalWards();
        for (HospitalWard  hospitalWard : hospitalWards) {
            Paragraph hospitalWardParagraph = new Paragraph(hospitalWard.getName(), reportFonts.getBoldItalic(12));
            hospitalWardParagraph.setLeading(12);
            document.add(hospitalWardParagraph);
        }
    }

    private void addDeathStoryEntries(Document document, DissectionProtocolReport dissectionProtocol) throws DocumentException {
        Set<DeathStoryName> storyEntrieNames = dissectionProtocol.getDeathStoryNames();
        if (CollectionUtils.isNotEmpty(storyEntrieNames)) {

            for (DeathStoryName deathStoryName : storyEntrieNames) {
                Paragraph deathStoryEntryParagraph = new Paragraph();
                String deathStoryEntryName = "";
                switch (deathStoryName) {
                    case ADMISSION:
                        deathStoryEntryName = "Przyj\u0119cie do szpitala: ";
                        break;
                    case CHILD_BIRTH:
                        deathStoryEntryName = "Por\u00F3d dziecka: ";
                        break;
                    case DEATH:
                        deathStoryEntryName = "Zgon: ";
                        break;
                    case DEATH_CHILD_BIRTH:
                        deathStoryEntryName = "Por\u00F3d dziecka obumar\u0142ego wewn\\u0105trzmacicznie: ";
                        break;
                    case MOTHER_ADMISSION:
                        deathStoryEntryName = "Przyj\u0119cie matki do szpitala: ";
                        break;
                }
                deathStoryEntryParagraph.add(new Chunk(deathStoryEntryName, reportFonts.getItalic(10)));

                String storyDateTimePattern = "%s r. godz. %s";
                String dateFor = "";
                String hourFor = "--";

                Date date = dissectionProtocol.getDateFor(deathStoryName);
                if (date != null) {
                    dateFor = ReportChunks.getDate(date);
                }
                String hour = dissectionProtocol.getHourFor(deathStoryName);
                if (hour != null) {
                    hourFor = hour;
                }

                String deathStoryEntryDateTime =  String.format(storyDateTimePattern, dateFor, hourFor);
                deathStoryEntryParagraph.add(new Chunk(deathStoryEntryDateTime, reportFonts.getBoldItalic(11)));

                String minutesFor = dissectionProtocol.getMinuteFor(deathStoryName);
                if (minutesFor != null) {
                    Chunk deathStoryMinuteChunk = new Chunk(minutesFor, reportFonts.getBoldItalic(7));
                    deathStoryMinuteChunk.setTextRise(5f);
                    deathStoryEntryParagraph.add(deathStoryMinuteChunk);
                }

                String descriptionFor = dissectionProtocol.getDescriptionFor(deathStoryName);
                if (!StringUtils.isEmpty(descriptionFor)) {
                    deathStoryEntryParagraph.add(new Chunk(descriptionFor, reportFonts.getBoldItalic(10)));
                }
                document.add(deathStoryEntryParagraph);
            }
        }
    }


    private void addClinicalDiagnose(Document document, DissectionProtocolReport dissectionProtocol) throws DocumentException {
        reportSection.addLineHeader(document, "Rozpoznanie kliniczne:");
        String clinicalDiagnosis = dissectionProtocol.getClinicalDiagnosis();
        if (clinicalDiagnosis != null) {
            String[] clinicalDiagnosisLines = clinicalDiagnosis.split("\r");
            for (String clinicalDiagnosisLine : clinicalDiagnosisLines) {
                reportSection.addIndentContent(document, clinicalDiagnosisLine, -14);
            }
        }
    }

    public void addDissectionDiagnose(Document document, DissectionProtocolReport dissectionProtocol) throws DocumentException {
        Map<Long, String> dissectionDiagnoseValues = getDissectionDiagnoseValues(dissectionProtocol);
        reportSection.addLineHeader(document, "Rozpoznania sekcyjne:");
        for (Map.Entry<Long, String> dissectionDiagnoseEntry : dissectionDiagnoseValues.entrySet()) {
            reportSection.addIndentContent(document, dissectionDiagnoseEntry.getValue(), -14);
            Collection<String> dissectionDiagnoseOptionValues = getDissectionDiagnoseOptionValues(dissectionDiagnoseEntry.getKey(), dissectionProtocol);
            if (!dissectionDiagnoseOptionValues.isEmpty()) {
                com.itextpdf.text.List list = new com.itextpdf.text.List(com.itextpdf.text.List.UNORDERED);
                list.setIndentationLeft(40);
                list.setListSymbol("\u2022 ");
                for (String dissectionDiagnoseOptionName : dissectionDiagnoseOptionValues) {
                    list.add(new ListItem(dissectionDiagnoseOptionName, reportFonts.getNormal(10)));
                }
                document.add(list);
            }
        }
    }
}
