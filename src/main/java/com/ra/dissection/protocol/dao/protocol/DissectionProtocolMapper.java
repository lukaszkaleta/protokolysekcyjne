package com.ra.dissection.protocol.dao.protocol;

import com.ra.dissection.protocol.domain.common.Range;
import com.ra.dissection.protocol.domain.protocol.DissectionProtocol;
import com.ra.dissection.protocol.domain.protocol.DissectionProtocolProgress;
import com.ra.dissection.protocol.domain.search.UserSearch;
import com.ra.dissection.protocol.service.UserSearchService;

import java.util.List;

/**
 * This is the biggest mapper in application.
 * Contains all logic for dissection protocol.
 *
 * @author lukaszkaleta
 * @since 01.05.13 12:46
 */
public interface DissectionProtocolMapper {

    Long selectId(long dissectionProtocolId);

    void insert(DissectionProtocol dissectionProtocol);

    //
    // Select
    //
    DissectionProtocol selectStatus(long dissectionProtocolId);

    DissectionProtocol selectBasicData(long dissectionProtocolId);

    DissectionProtocol selectClinicalDiagnosis(long dissectionProtocolId);

    DissectionProtocol selectClinicalData(long dissectionProtocolId);

    DissectionProtocol selectMedicalPracticeAnalysis(long dissectionProtocolId);

    DissectionProtocolProgress selectProgress(long dissectionProtocolId);

    long selectNewest();

    //
    // Updates
    //
    void updateBasicData(DissectionProtocol dissectionProtocol);

    void updateClinicalDiagnosis(DissectionProtocol dissectionProtocol);

    void updateHistopatologicalExamination(DissectionProtocol dissectionProtocol);

    void updateClinicalData(DissectionProtocol dissectionProtocol);

    void updateMedicalPracticeAnalysis(DissectionProtocol dissectionProtocol);

    void updateStatus(DissectionProtocol dissectionProtocol);

    //
    // Search
    //
    List<DissectionProtocol> selectLatest(Range<Integer> range);

    List<DissectionProtocol> selectSearch(UserSearch userSearch);


    void deleteDissectionProtocol(long dissectionProtocolId);

    long insertClone(long dissectionProtocolId);

    List<DissectionProtocol> selectDissectionProtocolsBasicData(List<Long> dissectionProtocolIds);
}
