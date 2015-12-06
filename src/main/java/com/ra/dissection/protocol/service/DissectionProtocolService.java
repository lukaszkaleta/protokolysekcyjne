package com.ra.dissection.protocol.service;

import com.google.common.collect.Multimap;
import com.ra.dissection.protocol.domain.common.OrderSwitch;
import com.ra.dissection.protocol.domain.common.Range;
import com.ra.dissection.protocol.domain.protocol.*;
import com.ra.dissection.protocol.domain.search.UserSearch;
import com.ra.dissection.protocol.domain.settings.DescriptionPointSource;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Set;

/**
 * @author lukaszkaleta
 * @since 28.04.13 22:03
 */
public interface DissectionProtocolService {

    DissectionProtocol create(DissectionProtocol dissectionProtocol);

    DissectionProtocol loadStatus(long dissectionProtocolId);

    DissectionProtocol loadBasicData(long dissectionProtocolId);

    DissectionProtocol loadClinicalDiagnosis(long dissectionProtocolId);

    DissectionProtocol loadDescription(long dissectionProtocolId);

    DissectionProtocol loadDissectionDiagnosis(long dissectionProtocolId);

    DissectionProtocol loadHistopathologicalExamination(long dissectionProtocolId);

    DissectionProtocol loadClinicalData(long dissectionProtocolId);

    DissectionProtocol loadMedicalPracticeAnalysis(long dissectionProtocolId);

    void updateBasicData(DissectionProtocol dissectionProtocol);

    void updateClinicalDiagnosis(DissectionProtocol dissectionProtocol);

    void updateDescriptionPoint(DescriptionPoint descriptionPoint);

    void updateDescriptionPoints(List<DescriptionPoint> descriptionPoints);

    void updateDissectionDiagnose(DissectionDiagnose dissectionDiagnose);

    void updateHistopatologicalExamination(HistopathologicalExamination dissectionProtocol);

    void updateMedicalPracticeAnalysis(DissectionProtocol dissectionProtocol);

    void updateClinicalData(DissectionProtocol dissectionProtocol);

    void updateStatus(long dissectionProtocolId, DissectionProtocolStatus dissectionProtocolStatus);

    List<DissectionProtocol> loadLatest(Range<Integer> range);

    List<DissectionProtocol> search(String username);


    List<DissectionProtocol> search(UserSearch userSearch);

    DescriptionPoint getDescriptionPoint(long descriptionPointId);

    DissectionDiagnose getDissectionDiagnose(long dissectionDiagnoseId);

    /**
     * Create new dissection diagnose.
     * This diagnose will be added to protocol and dissection diagnose source will be created.
     *
     * @param dissectionDiagnose which is created.
     *
     * @return id of description point source which is empty
     */
    long createNewDissectionDiagnose(DissectionDiagnose dissectionDiagnose);

    /**
     * Reorder dissection diagnose within dissection protocol.
     *
     * @param dissectionProtocolId id of dissection protocol.
     * @param dissectionDiagnoseId id of dissection diagnose.
     * @param orderSwitch flag which determine if selected dissection diagnose must be moved up/down. true=up, false=down.
     */
    void reorderDissectionDiagnose(long dissectionProtocolId, long dissectionDiagnoseId, OrderSwitch orderSwitch);

    /**
     * Add existing dissection diagnose.

     * @param dissectionDiagnose existing dissection diagnose which will be added to protocol.
     *
     * @return Description Point Source which is associated with diagnose.
     */
    DescriptionPointSource addDissectionDiagnose(DissectionDiagnose dissectionDiagnose);


    DissectionProtocolProgress getProgress(long dissectionProtocolId);

    /**
     * Gets dissection diagnose which were added to dissection protocol from description point.
     *
     * @param dissectionProtocolId id of dissection protocol
     * @param descriptionPointId id of description point.
     *
     * @return list of dissection diagnoses
     */

    DissectionDiagnose loadClinicalDiagnoseForDescriptionPoint(long dissectionProtocolId, long descriptionPointId);
    /**
     * Deletes description point from protocol.
     *
     * @param descriptionPointId id of description point which will be deleted.
     *
     * @return description point which were deleted .. or null if such point does not exists.
     */

    DescriptionPoint deleteDescriptionPoint(long descriptionPointId);

    /**
     * Remove hospital ward from list of assigned hospital wards.
     *
     * @param dissectionProtocolId id of dissection protocol from which we remove hospital ward.
     * @param hospitalWardId id of assigned hospital which is removed.
     */
    void removeWard(long dissectionProtocolId, long hospitalWardId);

    /**
     * Delete whole dissection protocol.
     *
     * @param dissectionProtocolId id of removed dissection proto
     */
    void delete(long dissectionProtocolId);

    /**
     * Delete histopathological examination.
     *
     * @param histopathologicalExaminationId
     */
    void deleteHistopathologicalExamination(long histopathologicalExaminationId);

    /**
     * Clone protocol.
     * This operation creates new protocol based on existing.
     *
     * @param dissectionProtocolId id of protocol which is source for new protocol.
     *
     * @return id of newly created protocol
     */
    long clone(long dissectionProtocolId);

    /**
     * Delete dissection diagnose with specified id.
     * This dissection diagnose will be deleted from protocol.
     *
     * @param dissectionDiagnoseId id of dissection diagnose for deletion.
     */
    void deleteDissectionDiagnose(long dissectionDiagnoseId);

    /**
     * Load single dissection diagnose.
     * It may be needed to get further relations.
     *
     * @param dissectionDiagnoseId id of single dissection diagnose.
     *
     * @return dissection diagnose.
     */
    DissectionDiagnose loadSingleDissectionDiagnose(long dissectionDiagnoseId);

    /**
     * Get collections of dissection diagnose options.
     *
     * @param dissectionDiagnoseIds ids of dissection diagnoses already added to protocol.
     *
     * @return
     */
    Multimap<Long,DissectionDiagnoseOption> getDissectionDiagnoseOptions(Set<Long> dissectionDiagnoseIds);

    List<DissectionDiagnoseOption> getDissectionDiagnoseOptions(long dissectionDiagnoseId);

    void addDissectionDiagnoseOption(long dissectionDiagnoseId, long dissectionDiagnoseSourceOptionId);

    void deleteDissectionDiagnoseOption(long dissectionDiagnoseOptionId);

    void updateDissectionDiagnoseOption(DissectionDiagnoseOption dissectionDiagnoseOption);

    /**
     * Gets all dissection protocols which use dissection diagnose source.
     *
     * @param dissectionDiagnoseSourceId id of dissection diagnose which is used in protocols.
     *
     * @return list of dissection protocols.
     */
    List<DissectionProtocol> getDissectionDiagnoseProtocols(long dissectionDiagnoseSourceId);

    void reorderDissectionDiagnose(long protocolId, List<String> ordered);

    void addDissectionDiagnoseSpace(long dissectionDiagnoseId);

    void removeDissectionDiagnoseSpace(long dissectionDiagnoseId);
}
