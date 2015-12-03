package com.ra.dissection.protocol.dao.settings;

import com.ra.dissection.protocol.domain.settings.DescriptionPointSource;
import com.ra.dissection.protocol.domain.settings.DescriptionPointType;
import com.ra.dissection.protocol.domain.settings.DissectionProtocolCategory;

import java.lang.String;
import java.util.List;
import java.util.Map;

/**
 * @author lukaszkaleta
 * @since 10.05.13 13:55
 */
public interface DescriptionPointSourceMapper {

    void insertDescriptionPointSource(DescriptionPointSource descriptionPointSource);

    void updateDescriptionPointSource(DescriptionPointSource descriptionPointSource);

    void deleteDescriptionPointSource(long id);

    DescriptionPointSource selectDescriptionPointSource(long id);

    List<DescriptionPointSource> selectDescriptionPointSources();

    List<DescriptionPointSource> selectDissectionDiagnoseSources(Map<String, Object> parameters);

    Integer selectNextGeneralPoint(Map<String, Object> parameters);

    Integer selectNextGeneralPosition(Map<String, Object> parameters);

    /**
     * Select all 'DIAGNOSE' description points which may replace 'GENERAL' Description.
     *
     * @param descriptionPointSourceId id of the description point source.
     *
     * @return
     */
    List<DescriptionPointSource> selectReplacementDiagnoseDescriptionPoints(long descriptionPointSourceId);

    List<DescriptionPointSource> selectSpecificDescriptionPoint(Map<String, Object> parameters);
}
