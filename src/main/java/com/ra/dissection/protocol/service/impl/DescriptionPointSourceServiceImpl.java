package com.ra.dissection.protocol.service.impl;

import com.google.common.collect.Multimap;
import com.ra.dissection.protocol.dao.protocol.DescriptionPointMapper;
import com.ra.dissection.protocol.dao.protocol.DissectionDiagnoseMapper;
import com.ra.dissection.protocol.dao.settings.DescriptionPointSourceMapper;
import com.ra.dissection.protocol.dao.settings.DissectionDiagnoseSourceMapper;
import com.ra.dissection.protocol.domain.protocol.DissectionDiagnose;
import com.ra.dissection.protocol.domain.settings.DescriptionPointSource;
import com.ra.dissection.protocol.domain.settings.DescriptionPointType;
import com.ra.dissection.protocol.domain.settings.DissectionDiagnoseSource;
import com.ra.dissection.protocol.domain.settings.DissectionProtocolCategory;
import com.ra.dissection.protocol.service.DescriptionPointSourceService;
import com.ra.dissection.protocol.service.support.DescriptionPointSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lukaszkaleta
 * @since 10.05.13 14:17
 */
@Service
public class DescriptionPointSourceServiceImpl implements DescriptionPointSourceService {

    @Autowired
    private DescriptionPointSourceMapper descriptionPointSourceMapper;

    @Autowired
    private DescriptionPointMapper descriptionPointMapper;

    @Autowired
    private DissectionDiagnoseMapper dissectionDiagnoseMapper;

    @Autowired
    private DissectionDiagnoseSourceMapper dissectionDiagnoseSourceMapper;

    @Autowired
    private DescriptionPointSupport descriptionPointSupport;

    @Override
    public List<DescriptionPointSource> getDescriptionPointsSources(DissectionProtocolCategory.Name categoryName, DescriptionPointType descriptionPointType) {
        if (categoryName == null) {
            throw new IllegalArgumentException("Please provide category for description point sources");
        }
        if (descriptionPointType == null) {
            throw new IllegalArgumentException("Please provide DescriptionPointType for description point sources");
        }
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("dissectionProtocolCategoryName", categoryName.name());
        parameters.put("descriptionPointType", descriptionPointType.name());
        List<DescriptionPointSource> descriptionPointSources = descriptionPointSourceMapper.selectDissectionDiagnoseSources(parameters);
        return descriptionPointSources;
    }

    public Multimap<Integer, DescriptionPointSource> getPointsForCategory(DissectionProtocolCategory.Name categoryName, DescriptionPointType descriptionPointType) {
        if (categoryName == null) {
            throw new IllegalArgumentException("Please provide category for description points");
        }
        List<DescriptionPointSource> descriptionPointSources = getDescriptionPointsSources(categoryName, descriptionPointType);
        return descriptionPointSupport.pointingMultimap(descriptionPointSources);
    }

    @Override
    public int getNextPoint(DissectionProtocolCategory.Name dissectionProtocolCategoryName) {
        Map<String, Object> selectParameters = new HashMap<String, Object>();
        selectParameters.put("dissectionProtocolCategoryName", dissectionProtocolCategoryName.name());
        selectParameters.put("descriptionPointType", DescriptionPointType.GENERAL);
        Integer nextPoint = descriptionPointSourceMapper.selectNextGeneralPoint(selectParameters);
        return nextPoint != null ? nextPoint : 1;
    }

    @Override
    public int getNextPosition(int point, DissectionProtocolCategory.Name dissectionProtocolCategoryName) {
        Map<String, Object> selectParameters = new HashMap<String, Object>();
        selectParameters.put("point", point);
        selectParameters.put("dissectionProtocolCategoryName", dissectionProtocolCategoryName.name());
        selectParameters.put("descriptionPointType", DescriptionPointType.GENERAL);
        Integer nextPosition = descriptionPointSourceMapper.selectNextGeneralPosition(selectParameters);
        return nextPosition != null ? nextPosition : 1;
    }

    @Override
    public List<DissectionDiagnoseSource> getDissectionDiagnoseWhichDescriptionReplacement(long generalDescriptionPointSourceId) {
        return dissectionDiagnoseSourceMapper.selectDissectionDiagnoseWithReplacementDescription(generalDescriptionPointSourceId);
    }

    @Override
    public void create(DescriptionPointSource entity) {
        if (entity.getType() != null && DescriptionPointType.DIAGNOSE.equals(entity.getType())) {
            throw new UnsupportedOperationException();
        }
        entity.setType(DescriptionPointType.GENERAL);
        descriptionPointSourceMapper.insertDescriptionPointSource(entity);
    }

    @Override
    public DescriptionPointSource read(long id) {
        return descriptionPointSourceMapper.selectDescriptionPointSource(id);
    }

    @Override
    @Transactional
    public void update(DescriptionPointSource entity) {
        descriptionPointSourceMapper.updateDescriptionPointSource(entity);

        if (DescriptionPointType.DIAGNOSE.equals(entity.getType())) {
            // Check if we need update protocol description points
            // We need to find all description points which were not modified and are set up in protocol at specified category.
            descriptionPointMapper.updateUntouchedDescriptionPoints(entity);
        }
    }

    @Override
    public DescriptionPointSource delete(long id) {
        DescriptionPointSource descriptionPointSource = descriptionPointSourceMapper.selectDescriptionPointSource(id);
        if (descriptionPointSource != null) {
            switch (descriptionPointSource.getType()) {
                case DIAGNOSE:
                    dissectionDiagnoseSourceMapper.deleteDissectionDiagnoseSourceByDescriptionPointSource(id);
                case GENERAL:
                    descriptionPointSourceMapper.deleteDescriptionPointSource(id);
            }
        }
        return descriptionPointSource;
    }

    @Override
    public List<DescriptionPointSource> getAll() {
        return descriptionPointSourceMapper.selectDescriptionPointSources();
    }

    @Override
    public List<DescriptionPointSource> getFiltered(String filter) {
        return null;
    }
}
