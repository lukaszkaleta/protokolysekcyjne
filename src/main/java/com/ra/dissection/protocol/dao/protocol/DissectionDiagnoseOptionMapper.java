package com.ra.dissection.protocol.dao.protocol;

import com.ra.dissection.protocol.domain.protocol.DissectionDiagnoseOption;

import java.util.List;

/**
 * @author lukaszkaleta
 * @since 22.07.13 07:40
 */
public interface DissectionDiagnoseOptionMapper {

    void insertDissectionDiagnoseOption(DissectionDiagnoseOption dissectionDiagnoseOption);

    void deleteDissectionDiagnoseOption(long dissectionDiagnoseOptionId);

    /**
     * Delete options by dissection diagnose id.
     *
     * @param dissectionDiagnoseId id of dissection diagnose.
     */
    void deleteDissectionDiagnoseOptionByDissectionDiagnose(long dissectionDiagnoseId);

    List<DissectionDiagnoseOption> selectOptionsForDissectionDiagnose(long dissectionDiagnoseId);

    /**
     * Select dissection diagnose options by dissection diagnose ids.
     *
     * @param dissectionDiagnoseIds ids of dissection diagnose.
     *
     * @return list of dissection diagnose options.
     */
    List<DissectionDiagnoseOption> selectOptionsForDissectionDiagnoses(List<Long> dissectionDiagnoseIds);

    void updateDissectionDiagnoseOption(DissectionDiagnoseOption dissectionDiagnoseOption);
}
