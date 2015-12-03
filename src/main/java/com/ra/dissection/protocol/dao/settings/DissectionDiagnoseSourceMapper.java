package com.ra.dissection.protocol.dao.settings;

import com.ra.dissection.protocol.domain.settings.DissectionDiagnoseName;
import com.ra.dissection.protocol.domain.settings.DissectionDiagnoseSource;
import com.ra.dissection.protocol.domain.settings.DissectionProtocolCategory;

import java.util.List;
import java.util.Map;

/**
 * @author lukaszkaleta
 * @since 10.05.13 23:12
 */
public interface DissectionDiagnoseSourceMapper {

    void insertDissectionDiagnoseSource(DissectionDiagnoseSource dissectionDiagnoseSource);

    void insertDissectionDiagnoseName(DissectionDiagnoseName dissectionDiagnoseName);

    void updateDissectionDiagnoseSource(DissectionDiagnoseSource dissectionDiagnoseSource);

    void deleteDissectionDiagnoseSource(long id);

    void deleteDissectionDiagnoseSourceByDescriptionPointSource(long id);

    DissectionDiagnoseSource selectDissectionDiagnoseSource(long id);

    List<DissectionDiagnoseSource> selectDissectionDiagnoseSources();

    List<DissectionDiagnoseSource> selectDissectionDiagnoseSourcesForCategory(Map<String, Object> selectMap);

    DissectionDiagnoseSource selectDissectionDiagnoseSourceByDescriptionPointSource(long descriptionPointSourceId);

    List<DissectionDiagnoseSource> selectDissectionDiagnoseWithReplacementDescription(long descriptionPointSourceId);

    List<DissectionDiagnoseSource> selectDissectionDiagnoseSourceByLetter(String letter);

    List<String> selectFirstLetters();
}
