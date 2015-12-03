package com.ra.dissection.protocol.service.report;

import com.ra.dissection.protocol.dao.protocol.ReportMapper;
import com.ra.dissection.protocol.domain.report.DissectionProtocolReport;
import com.ra.dissection.protocol.domain.report.ReportFile;
import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.util.PDFMergerUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.TimeUnit;

/**
 * @author lukaszkaleta
 * @since 09.06.13 07:32
 */
@Component
public class FullReportGenerator implements ReportPageGenerator {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private ReportMapper reportMapper;

    @Override
    public byte[] generate(long reportId, DissectionProtocolReport dissectionProtocol) {
        long start = System.nanoTime();
        ReportFile latinStartPage = reportMapper.selectLatinStartPagePart(reportId);
        ReportFile translatedStartPage = reportMapper.selectTranslatedStartPagePart(reportId);
        ReportFile contentStartPage = reportMapper.selectContentPagesPart(reportId);
        PDFMergerUtility pdfMergerUtility = new PDFMergerUtility();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        pdfMergerUtility.setDestinationStream(outputStream);
        if (latinStartPage.getData() != null) {
            pdfMergerUtility.addSource(new ByteArrayInputStream(latinStartPage.getData()));
        }
        if (translatedStartPage.getData() != null) {
            pdfMergerUtility.addSource(new ByteArrayInputStream(translatedStartPage.getData()));
        }
        if (contentStartPage.getData() != null) {
            pdfMergerUtility.addSource(new ByteArrayInputStream(contentStartPage.getData()));
        }
        try {
            pdfMergerUtility.mergeDocuments();
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (COSVisitorException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        long end = System.nanoTime();
        log.info("Generating full report took <" + TimeUnit.NANOSECONDS.toMillis(end - start) + "> ms");
        return outputStream.toByteArray();
    }
}
