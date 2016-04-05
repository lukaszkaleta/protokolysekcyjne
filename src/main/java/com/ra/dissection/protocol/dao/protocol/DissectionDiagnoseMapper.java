package com.ra.dissection.protocol.dao.protocol;

import com.ra.dissection.protocol.domain.protocol.DissectionDiagnose;

import java.util.List;
import java.util.Map;

/**
 * @author lukaszkaleta
 * @since 12.05.13 22:08
 */
public interface DissectionDiagnoseMapper {
    void insertDissectionDiagnose(DissectionDiagnose dissectionDiagnose);

    void insertClone(Map<String, Long> dissectionProtocolIds);

    void updateDissectionDiagnose(DissectionDiagnose dissectionDiagnose);
    void deleteDissectionDiagnose(long id);
    DissectionDiagnose selectDissectionDiagnose(long id);
    List<DissectionDiagnose> selectDissectionDiagnoses();
    List<DissectionDiagnose> selectDissectionDiagnoseForDissectionProtocol(long dissectionProtocolId);

    List<DissectionDiagnose> selectDissectionDiagnoseFromDescriptionPoint(Map<String, Long> selectParams);

    Integer selectMaxSortIndexForNewDissectionDiagnose(long dissectionDiagnoseId);

    /**
     * Delete dissection diagnose which belongs to specified dissection protocol.
     *
     * @param dissectionProtocolId id of dissection protocol.
     */
    void deleteDissectionProtocolDissectionDiagnose(long dissectionProtocolId);

    /**
     * Update dissection diagnoses which are attached to this description point.
     * Diagnoses will not be anymore attached to it since it will be deleted.
     *
     * @param descriptionPointId id of description point which will be deleted.
     */
    void updateDeletedDescriptionPoint(long descriptionPointId);

    void updateDissectionDiagnoseSortIndex(DissectionDiagnose dissectionDiagnose);

    List<DissectionDiagnose> selectDissectionDiagnosesForSource(long dissectionDiagnoseSourceId);

    List<Long> selectDissectionProtocolIdsForSource(long dissectionDiagnoseSourceId);

    void updateDissectionDiagnoseSpaceBelow(DissectionDiagnose dissectionDiagnose);
    void updateDissectionDiagnoseSpaceAbove(DissectionDiagnose dissectionDiagnose);
}
