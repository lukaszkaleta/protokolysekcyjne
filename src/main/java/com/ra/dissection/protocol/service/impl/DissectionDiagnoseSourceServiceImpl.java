package com.ra.dissection.protocol.service.impl;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import com.ra.dissection.protocol.dao.settings.DescriptionPointSourceMapper;
import com.ra.dissection.protocol.dao.settings.DissectionDiagnoseSourceMapper;
import com.ra.dissection.protocol.dao.settings.DissectionDiagnoseSourceOptionMapper;
import com.ra.dissection.protocol.domain.settings.*;
import com.ra.dissection.protocol.service.DissectionDiagnoseSourceService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author lukaszkaleta
 * @since 11.05.13 08:27
 */
@Service
public class DissectionDiagnoseSourceServiceImpl implements DissectionDiagnoseSourceService {

    @Autowired
    private DissectionDiagnoseSourceMapper dissectionDiagnoseSourceMapper;

    @Autowired
    private DescriptionPointSourceMapper descriptionPointSourceMapper;

    @Autowired
    private DissectionDiagnoseSourceOptionMapper dissectionDiagnoseSourceOptionMapper;

    @Override
    public void create(DissectionDiagnoseSource entity) {
        DescriptionPointSource descriptionPointSource = new DescriptionPointSource();
        descriptionPointSource.setCategory(entity.getCategory());
        descriptionPointSource.setType(DescriptionPointType.DIAGNOSE);
        descriptionPointSourceMapper.insertDescriptionPointSource(descriptionPointSource);

        entity.setDescriptionPointSourceId(descriptionPointSource.getId());
        dissectionDiagnoseSourceMapper.insertDissectionDiagnoseSource(entity);
    }

    @Override
    public DissectionDiagnoseSource read(long id) {
        return dissectionDiagnoseSourceMapper.selectDissectionDiagnoseSource(id);
    }

    public DissectionDiagnoseSource readByDescriptionPointSource(long descriptionPointSourceId) {
        return dissectionDiagnoseSourceMapper.selectDissectionDiagnoseSourceByDescriptionPointSource(descriptionPointSourceId);
    }

    @Override
    public List<String> getLatinLetters() {
        return dissectionDiagnoseSourceMapper.selectFirstLetters();
    }

    @Override
    public List<DissectionDiagnoseSourceOption> getOptions(long dissectionDiagnoseSourceId) {
        return dissectionDiagnoseSourceOptionMapper.selectOptionsForDissectionDiagnoseSource(dissectionDiagnoseSourceId);
    }

    @Override
    public Multimap<Long, DissectionDiagnoseSourceOption> getOptions(Set<Long> dissectionDiagnoseSourceIds) {
        if (CollectionUtils.isEmpty(dissectionDiagnoseSourceIds)) {
            return LinkedHashMultimap.create();
        }
        List<Long> ddsIds = new ArrayList<Long>(dissectionDiagnoseSourceIds);
        List<DissectionDiagnoseSourceOption> options = dissectionDiagnoseSourceOptionMapper.selectOptionsForDissectionDiagnoseSources(ddsIds);
        Multimap<Long, DissectionDiagnoseSourceOption> optionMultimap = LinkedHashMultimap.create();
        for (DissectionDiagnoseSourceOption option : options) {
            optionMultimap.put(option.getDissectionDiagnoseSourceId(), option);
        }
        return optionMultimap;
    }

    @Override
    public boolean isOptionAvailable(long dissectionDiagnoseSourceId) {
        return dissectionDiagnoseSourceOptionMapper.selectOptionsAvailableForDissectionDiagnoseSource(dissectionDiagnoseSourceId);
    }

    @Override
    public Map<Long, Boolean> getOptionAvailableMap(Set<Long> dissectionDiagnoseSourceIds) {
        if (CollectionUtils.isEmpty(dissectionDiagnoseSourceIds)) {
            return Collections.emptyMap();
        }
        List<Long> ddsIds = new ArrayList<Long>(dissectionDiagnoseSourceIds);
        List<DissectionDiagnoseSourceOption> options = dissectionDiagnoseSourceOptionMapper.selectOptionsForDissectionDiagnoseSources(ddsIds);
        Map<Long, Boolean> optionAvailableMap = new HashMap<Long, Boolean>();
        for (DissectionDiagnoseSourceOption option : options) {
            optionAvailableMap.put(option.getDissectionDiagnoseSourceId(), Boolean.TRUE);
        }
        for (Long id : dissectionDiagnoseSourceIds) {
            if (!optionAvailableMap.containsKey(id)) {
                optionAvailableMap.put(id, Boolean.FALSE);
            }
        }
        return optionAvailableMap;
    }

    @Override
    public DissectionDiagnoseSourceOption readOption(long dissectionDiagnoseSourceOptionId) {
        return dissectionDiagnoseSourceOptionMapper.selectOption(dissectionDiagnoseSourceOptionId);
    }

    @Override
    public void updateOption(DissectionDiagnoseSourceOption dissectionDiagnoseSourceOption) {
        dissectionDiagnoseSourceOptionMapper.updateDissectionDiagnoseSourceOption(dissectionDiagnoseSourceOption);
    }

    @Override
    public void createOption(DissectionDiagnoseSourceOption dissectionDiagnoseSourceOption) {
        long dissectionDiagnoseSourceId = dissectionDiagnoseSourceOption.getDissectionDiagnoseSourceId();
        Integer sortIndex = dissectionDiagnoseSourceOptionMapper.selectMaximumSortIndex(dissectionDiagnoseSourceId);
        if (sortIndex == null) {
            sortIndex = 0;
        }
        dissectionDiagnoseSourceOption.setSortIndex(sortIndex + 1);
        dissectionDiagnoseSourceOptionMapper.insertDissectionDiagnoseSourceOption(dissectionDiagnoseSourceOption);
    }

    @Override
    @Transactional
    public void deleteOption(long dissectionDiagnoseSourceOptionId) {

        DissectionDiagnoseSourceOption willBeDeleted = dissectionDiagnoseSourceOptionMapper.selectOption(dissectionDiagnoseSourceOptionId);
        dissectionDiagnoseSourceOptionMapper.deleteDissectionDiagnoseSourceOption(dissectionDiagnoseSourceOptionId);

        if (willBeDeleted != null) {
            // Reorder sort indexes
            int sortIndex = willBeDeleted.getSortIndex();
            long dissectionDiagnoseSourceId = willBeDeleted.getDissectionDiagnoseSourceId();
            List<DissectionDiagnoseSourceOption> dissectionDiagnoseSourceOptions = dissectionDiagnoseSourceOptionMapper.selectOptionsForDissectionDiagnoseSource(dissectionDiagnoseSourceId);
            for (DissectionDiagnoseSourceOption ddso : dissectionDiagnoseSourceOptions) {
                int ddsosi = ddso.getSortIndex();
                if (ddsosi > sortIndex) {
                    dissectionDiagnoseSourceOptionMapper.decrementDissectionDiagnoseSourceOptionSortIndex(ddso.getId());
                }
            }
        }
    }

    @Override
    public void update(DissectionDiagnoseSource entity) {
        dissectionDiagnoseSourceMapper.updateDissectionDiagnoseSource(entity);
    }

    @Override
    public DissectionDiagnoseSource delete(long id) {
        DissectionDiagnoseSource dissectionDiagnoseSource = dissectionDiagnoseSourceMapper.selectDissectionDiagnoseSource(id);
        dissectionDiagnoseSourceMapper.deleteDissectionDiagnoseSource(id);
        if (dissectionDiagnoseSource.getDescriptionPointSourceId() > 0) {
            descriptionPointSourceMapper.deleteDescriptionPointSource(dissectionDiagnoseSource.getDescriptionPointSourceId());
        }
        dissectionDiagnoseSourceOptionMapper.deleteDissectionDiagnoseSourceOptionByDissectionDiagnoseSource(id);
        return null;
    }

    @Override
    public List<DissectionDiagnoseSource> getAll() {
        return dissectionDiagnoseSourceMapper.selectDissectionDiagnoseSources();
    }

    @Override
    public List<DissectionDiagnoseSource> getFiltered(String filter) {
        return null;
    }

    @Override
    public List<DissectionDiagnoseSource> getAll(DissectionProtocolCategory category, String letter) {
        if (category.all()) {
            return dissectionDiagnoseSourceMapper.selectDissectionDiagnoseSourceByLetter(letter);
        } else {
            Map<String, Object> selectMap = new HashMap<String, Object>();
            selectMap.put("letter", letter);
            selectMap.put("adult", category.isAdult());
            selectMap.put("newborn", category.isNewborn());
            selectMap.put("fetus", category.isFetus());
            return dissectionDiagnoseSourceMapper.selectDissectionDiagnoseSourcesForCategory(selectMap);
        }
    }
}
