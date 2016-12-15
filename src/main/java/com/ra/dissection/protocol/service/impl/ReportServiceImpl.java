package com.ra.dissection.protocol.service.impl;

import com.google.common.base.Function;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.ra.dissection.protocol.util.AsciiString;
import com.ra.dissection.protocol.dao.protocol.DissectionProtocolMapper;
import com.ra.dissection.protocol.dao.protocol.HospitalWardEntryMapper;
import com.ra.dissection.protocol.dao.protocol.ReportMapper;
import com.ra.dissection.protocol.dao.settings.DoctorMapper;
import com.ra.dissection.protocol.dao.settings.HospitalMapper;
import com.ra.dissection.protocol.dao.settings.HospitalWardMapper;
import com.ra.dissection.protocol.domain.common.ImageData;
import com.ra.dissection.protocol.domain.common.Patient;
import com.ra.dissection.protocol.domain.protocol.*;
import com.ra.dissection.protocol.domain.report.*;
import com.ra.dissection.protocol.domain.settings.Doctor;
import com.ra.dissection.protocol.domain.settings.Hospital;
import com.ra.dissection.protocol.domain.settings.HospitalWard;
import com.ra.dissection.protocol.service.DissectionProtocolService;
import com.ra.dissection.protocol.service.ReportService;
import com.ra.dissection.protocol.service.report.*;
import com.ra.dissection.protocol.service.support.DescriptionPointSupport;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.*;

/**
 * @author lukaszkaleta
 * @since 13.05.13 22:44
 */
@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private ReportMapper reportMapper;

    @Autowired
    private LatinStartPageGenerator latinStartPageGenerator;

    @Autowired
    private TranslatedStartPageGenerator translatedStartPageGenerator;

    @Autowired
    private FullReportGenerator fullReportGenerator;

    @Autowired
    private ContentPagesGenerator contentPagesGenerator;

    @Autowired
    private DissectionProtocolService dissectionProtocolService;

    @Autowired
    private DescriptionPointSupport descriptionPointSupport;

    @Autowired
    private HospitalWardMapper hospitalWardMapper;

    @Autowired
    private DoctorMapper doctorMapper;

    @Autowired
    private HospitalMapper hospitalMapper;

    @Autowired
    private HospitalWardEntryMapper hospitalWardEntryMapper;

    @Autowired
    private DissectionProtocolMapper dissectionProtocolMapper;

    public DissectionProtocolReport getDissectionProtocolReport(long dissectionProtocolId) {

        DissectionProtocol dissectionProtocol = dissectionProtocolService.loadBasicData(dissectionProtocolId);
        if (dissectionProtocol == null) {
            throw new IllegalStateException(String.format("Report with id <%s> does not exists", dissectionProtocolId));
        }
        DissectionProtocolReport dissectionProtocolReport;
        switch (dissectionProtocol.getCategory()) {
            case NEWBORN:
                dissectionProtocolReport = new NewbornReport();
                break;
            case FETUS:
                dissectionProtocolReport = new FetusReport();
                break;
            case ADULT:
                dissectionProtocolReport = new AdultReport();
                break;
            default:
                throw new UnsupportedOperationException(String.format("Report for category <%s> is not supported", dissectionProtocol.getCategory()));
        }

        //
        // Protocol data
        //
        Hospital hospital = null;
        byte[] hospitalImage = null;
        HospitalWard hospitalWard = null;
        byte[] hospitalWardImage = null;

        Long hospitalId = dissectionProtocol.getHospitalId();
        if (hospitalId != null) {
            hospital = hospitalMapper.selectHospital(hospitalId);
            ImageData imageData = hospitalMapper.selectHospitalImage(hospitalId);
            if (imageData != null) {
                hospitalImage = imageData.getData();
            }
            List<HospitalWard> hospitalWards = hospitalWardMapper.selectDissectionHospitalWard(hospitalId);
            if (CollectionUtils.isNotEmpty(hospitalWards)) {
                hospitalWard = hospitalWards.get(0);
                ImageData imageData1 = hospitalWardMapper.selectHospitalWardImage(hospitalWard.getId());
                if (imageData1 != null) {
                    hospitalWardImage = imageData1.getData();
                }
            }
        }

        dissectionProtocolReport.installProtocol(hospital, hospitalImage, hospitalWard, hospitalWardImage, dissectionProtocol.getNumber());

        //
        // Basic Data
        //
        Doctor autopsyDoctor = null;
        Hospital deathHospital = null;
        List<HospitalWard> deathHospitalWards = null;

        BasicData basicData = dissectionProtocol.getBasicData();
        Long doctorExecutorId = basicData.getAutopsy().getDoctorExecutorId();
        if (doctorExecutorId != null) {
            autopsyDoctor = doctorMapper.selectDoctor(doctorExecutorId);
        }
        Long deathHospitalId = basicData.getDeathStory().getHospitalId();
        if (deathHospitalId != null) {
            deathHospital = hospitalMapper.selectHospital(deathHospitalId);
        }

        List<HospitalWardEntry> hospitalWardEntries = hospitalWardEntryMapper.selectProtocolHospitalWardEntries(dissectionProtocolId);
        if (CollectionUtils.isNotEmpty(hospitalWardEntries)) {
            List<Long> hospitalWardIds = new ArrayList<>();
            for (HospitalWardEntry hospitalWardEntry : hospitalWardEntries) {
                hospitalWardIds.add(hospitalWardEntry.getHospitalWardId());
            }
            deathHospitalWards = hospitalWardMapper.selectHospitalWardsByIds(hospitalWardIds.toArray(new Long[hospitalWardIds.size()]));
            // We need to order them as they were added.
            Map<Long, HospitalWard> hospitalWardsMap = Maps.uniqueIndex(deathHospitalWards, new Function<HospitalWard, Long>() {
                @Override
                public Long apply(HospitalWard hospitalWard) {
                    return hospitalWard.getId();
                }
            });
            deathHospitalWards.clear();
            for(Long hospitalWardId : hospitalWardIds) {
                deathHospitalWards.add(hospitalWardsMap.get(hospitalWardId));
            }
        }

        dissectionProtocolReport.installBasicData(basicData, autopsyDoctor, deathHospital, deathHospitalWards);

        //
        // Clinical diagnosis
        //
        dissectionProtocol = dissectionProtocolService.loadClinicalDiagnosis(dissectionProtocolId);
        dissectionProtocolReport.installClinicalDiagnosis(dissectionProtocol.getClinicalDiagnosis());

        //
        // Dissection diagnosis
        //
        dissectionProtocol = dissectionProtocolService.loadDissectionDiagnosis(dissectionProtocolId);
        List<DissectionDiagnose> dissectionDiagnoseList = dissectionProtocol.getDissectionDiagnoseList();
        Map<Long, DissectionDiagnose> longDissectionDiagnoseMap = new LinkedHashMap<Long, DissectionDiagnose>();
        for (DissectionDiagnose dissectionDiagnose : dissectionDiagnoseList) {
            longDissectionDiagnoseMap.put(dissectionDiagnose.getId(), dissectionDiagnose);
        }
        Multimap<Long, DissectionDiagnoseOption> dissectionDiagnoseOptions = dissectionProtocolService.getDissectionDiagnoseOptions(longDissectionDiagnoseMap.keySet());
        dissectionProtocolReport.installDissectionDiagnosis(longDissectionDiagnoseMap, dissectionDiagnoseOptions);

        // Description points
        dissectionProtocol = dissectionProtocolService.loadDescription(dissectionProtocolId);
        Multimap<Integer,DescriptionPoint> integerDescriptionPointMultimap = descriptionPointSupport.pointingMultimap(dissectionProtocol.getDescriptionPointList());
        dissectionProtocolReport.installDescriptionPoints(integerDescriptionPointMultimap);

        // Histopathological examination
        dissectionProtocol = dissectionProtocolService.loadHistopathologicalExamination(dissectionProtocolId);
        dissectionProtocolReport.installHistopathologicalExamination(dissectionProtocol.getHistopathologicalExaminations());

        // Clinical data
        dissectionProtocol = dissectionProtocolService.loadClinicalData(dissectionProtocolId);
        dissectionProtocolReport.installClinicalData(dissectionProtocol.getClinicalData());

        // Medical Practice Analysis
        dissectionProtocol = dissectionProtocolService.loadMedicalPracticeAnalysis(dissectionProtocolId);
        dissectionProtocolReport.installMedicalPracticeAnalysis(dissectionProtocol.getMedicalPracticeAnalysis());

        return dissectionProtocolReport;
    }

    @Override
    public void updateStatus(long dissectionProtocolId, ReportStatus requiredStatus) {
        if (dissectionProtocolId <= 0) {
            throw new IllegalArgumentException("Dissection protocol is required");
        }
        if (requiredStatus == null) {
            throw new IllegalArgumentException("Require status is not provided");
        }
        if (!requiredStatus.isUpdateOnReportChange()) {
            throw new IllegalArgumentException("Required status is not acceptable on report change");
        }
        ReportInfo reportInfo = reportMapper.selectReportInfo(dissectionProtocolId);
        if (reportInfo == null) {
            reportMapper.insertEmptyReport(dissectionProtocolId);
            reportInfo = reportMapper.selectReportInfo(dissectionProtocolId);
        }
        ReportStatus reportStatus = reportInfo.getReportStatus();
        if (reportStatus.equals(requiredStatus)) {
            return;
        }

        ReportStatus newStatus = null;

        switch (requiredStatus) {
            case NEED_CONTENT_PAGES:

                switch (reportStatus) {
                    case NEED_FIRST_PAGE:
                        newStatus = ReportStatus.NEED_ALL;
                        break;
                    case ACTUAL:
                        newStatus = requiredStatus;
                        break;
                    case NEED_ALL:
                        newStatus = reportStatus;
                        break;
                    case NEED_CONTENT_PAGES:
                        // Should not occur since it is handled
                        break;
                    case GENERATING:
                        // We leave it like it is. Report may not be actual.
                        break;
                }

                break;
            case NEED_FIRST_PAGE:

                switch (reportStatus) {
                    case NEED_CONTENT_PAGES:
                        newStatus = ReportStatus.NEED_ALL;
                        break;
                    case ACTUAL:
                        newStatus = requiredStatus;
                        break;
                    case NEED_ALL:
                        newStatus = reportStatus;
                        break;
                    case NEED_FIRST_PAGE:
                        // Should not occur since it is handled
                        break;
                    case GENERATING:
                        // We leave it like it is. Report may not be actual.
                        break;
                }

                break;
            case NEED_ALL:
                newStatus = ReportStatus.NEED_ALL;
        }

        if (newStatus != null) {
            Map<String, Object> statusUpdateMap = new HashMap<String, Object>();
            statusUpdateMap.put("status", newStatus.name());
            statusUpdateMap.put("id", reportInfo.getId());
            reportMapper.updateStatus(statusUpdateMap);
        }
    }

    @Override
    public void generateIfNeeded(long dissectionProtocolId) {
        ReportInfo reportInfo = reportMapper.selectReportInfo(dissectionProtocolId);
        ReportStatus reportStatus = reportInfo.getReportStatus();

        DissectionProtocolReport dissectionProtocol = null;

        switch (reportStatus) {
            case ACTUAL:
                boolean hasReport = reportInfo.isHasReport();
                if (hasReport) {
                    return;
                }
                // This is something illegal - if status is actual and has no report then lets generate all
            case NEED_ALL:
                generateAllPages(reportInfo, dissectionProtocol = getDissectionProtocolReport(dissectionProtocol, dissectionProtocolId));
                break;
            case NEED_FIRST_PAGE:
                generateFirstPage(reportInfo, dissectionProtocol = getDissectionProtocolReport(dissectionProtocol, dissectionProtocolId));
                break;
            case NEED_CONTENT_PAGES:
                generateContentPages(reportInfo, dissectionProtocol = getDissectionProtocolReport(dissectionProtocol, dissectionProtocolId));
                break;
        }
        generateFullReport(reportInfo, dissectionProtocol = getDissectionProtocolReport(dissectionProtocol, dissectionProtocolId));
    }

    @Override
    public void generate(long dissectionProtocolId) {
        ReportInfo reportInfo = reportMapper.selectReportInfo(dissectionProtocolId);
        DissectionProtocolReport dissectionProtocol = getDissectionProtocolReport(dissectionProtocolId);
        generateAllPages(reportInfo, dissectionProtocol);
        generateFullReport(reportInfo, dissectionProtocol);
    }

    private DissectionProtocolReport getDissectionProtocolReport(DissectionProtocolReport dissectionProtocolReport, long id) {
        if (dissectionProtocolReport == null) {
            return getDissectionProtocolReport(id);
        } else {
            return dissectionProtocolReport;
        }
    }

    @Override
    public InputStream getFullReport(long dissectionProtocolId) {
        ReportFile reportFile = reportMapper.selectFullReportData(dissectionProtocolId);
        return new ByteArrayInputStream(reportFile.getData());
    }

    @Override
    public String getReportName(long dissectionProtocolId) {
        DissectionProtocol dissectionProtocol = dissectionProtocolMapper.selectBasicData(dissectionProtocolId);
        // Take patient
        Patient patient = dissectionProtocol.getBasicData().getPatient();
        return new ReportName(patient).generate();
    }

    private void generateFirstPage(ReportInfo reportInfo, DissectionProtocolReport dissectionProtocol) {
        generateLatinStartPage(reportInfo, dissectionProtocol);
        generateTranslatedStartPage(reportInfo, dissectionProtocol);
    }

    private void generateAllPages(ReportInfo reportInfo, DissectionProtocolReport dissectionProtocol) {
        generateFirstPage(reportInfo, dissectionProtocol);
        generateContentPages(reportInfo, dissectionProtocol);
    }

    private void generateFullReport(ReportInfo reportInfo, DissectionProtocolReport dissectionProtocol) {
        ReportFile fullReportFile = generate(reportInfo, dissectionProtocol, fullReportGenerator);
        fullReportFile.setReportStatus(ReportStatus.ACTUAL);
        reportMapper.updateFullReport(fullReportFile);
    }

    private void generateContentPages(ReportInfo reportInfo, DissectionProtocolReport dissectionProtocol) {
        ReportFile contentPagesReportFile = generate(reportInfo, dissectionProtocol, contentPagesGenerator);
        reportMapper.updateContentPages(contentPagesReportFile);
    }

    private void generateTranslatedStartPage(ReportInfo reportInfo, DissectionProtocolReport dissectionProtocol) {
        ReportFile translatedReportFile = generate(reportInfo, dissectionProtocol, translatedStartPageGenerator);
        reportMapper.updateTranslatedStartPage(translatedReportFile);
    }

    private void generateLatinStartPage(ReportInfo reportInfo, DissectionProtocolReport dissectionProtocol) {
        ReportFile latinStartPageReportFile = generate(reportInfo, dissectionProtocol, latinStartPageGenerator);
        reportMapper.updateLatinStartPage(latinStartPageReportFile);
    }

    private ReportFile generate(ReportInfo reportInfo, DissectionProtocolReport dissectionProtocol, ReportPageGenerator reportPageGenerator) {
        long reportId = reportInfo.getId();
        long dissectionProtocolId = reportInfo.getDissectionProtocolId();
        byte[] data = reportPageGenerator.generate(reportId, dissectionProtocol);
        ReportFile reportFile = new ReportFile();
        reportFile.setId(reportId);
        reportFile.setDissectionProtocolId(dissectionProtocolId);
        reportFile.setData(data);
        reportFile.setReportStatus(ReportStatus.GENERATING);
        return reportFile;
    }
}
