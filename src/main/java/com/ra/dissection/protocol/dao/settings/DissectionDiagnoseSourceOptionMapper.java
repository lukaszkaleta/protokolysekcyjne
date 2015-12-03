package com.ra.dissection.protocol.dao.settings;

import com.ra.dissection.protocol.domain.settings.DissectionDiagnoseSourceOption;

import java.util.List;
import java.util.Set;

/**
 * @author lukaszkaleta
 * @since 19.07.13 20:40
 */
public interface DissectionDiagnoseSourceOptionMapper {

    void insertDissectionDiagnoseSourceOption(DissectionDiagnoseSourceOption dissectionDiagnoseSourceOption);

    void updateDissectionDiagnoseSourceOption(DissectionDiagnoseSourceOption dissectionDiagnoseSourceOption);

    boolean selectOptionsAvailableForDissectionDiagnoseSource(long dissectionDiagnoseSourceId);

    /**
     * Select list of options for dissection diagnose source.
     *
     * @param dissectionDiagnoseSourceId
     * @return
     */
    List<DissectionDiagnoseSourceOption> selectOptionsForDissectionDiagnoseSource(long dissectionDiagnoseSourceId);

    List<DissectionDiagnoseSourceOption> selectOptionsForDissectionDiagnoseSources(List<Long> dissectionDiagnoseSourceIds);

    void deleteDissectionDiagnoseSourceOption(long id);

    void deleteDissectionDiagnoseSourceOptionByDissectionDiagnoseSource(long dissectionDiagnoseSourceId);

    DissectionDiagnoseSourceOption selectOption(long dissectionDiagnoseSourceOptionId);

    /**
     * Select maximum sort index for options available withing dissection diagnose.
     *
     * @param dissectionDiagnoseSourceId
     * @return
     */
    Integer selectMaximumSortIndex(long dissectionDiagnoseSourceId);

    void decrementDissectionDiagnoseSourceOptionSortIndex(long id);
}
