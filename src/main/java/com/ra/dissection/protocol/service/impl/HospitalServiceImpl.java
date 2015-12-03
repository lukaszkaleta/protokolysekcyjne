package com.ra.dissection.protocol.service.impl;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import com.ra.dissection.protocol.dao.settings.HospitalMapper;
import com.ra.dissection.protocol.dao.settings.HospitalWardMapper;
import com.ra.dissection.protocol.domain.common.ImageData;
import com.ra.dissection.protocol.domain.settings.Hospital;
import com.ra.dissection.protocol.domain.settings.HospitalWard;
import com.ra.dissection.protocol.service.HospitalService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.*;

/**
 * @author lukaszkaleta
 * @since 25.04.13 14:12
 */
@Service
@Transactional
class HospitalServiceImpl implements HospitalService {

    @Autowired
    private HospitalMapper hospitalMapper;

    @Autowired
    private HospitalWardMapper hospitalWardMapper;

    @Override
    public void create(Hospital entity) {
        hospitalMapper.insertHospital(entity);
    }

    @Override
    public Hospital read(long id) {
        return hospitalMapper.selectHospital(id);
    }

    @Override
    public void update(Hospital entity) {
        hospitalMapper.updateHospital(entity);
    }

    @Override
    public Hospital delete(long id) {
        hospitalMapper.deleteHospital(id);
        return null;
    }

    @Override
    public List<Hospital> getAll() {
        return hospitalMapper.selectHospitals();
    }

    @Override
    public List<Hospital> getFiltered(String filter) {
        return hospitalMapper.filterHospitals(filter);
    }

    @Override
    public Map<Long, Collection<HospitalWard>> getHospitalWards() {
        List<HospitalWard> hospitalWards = hospitalWardMapper.selectHospitalWards();
        if (CollectionUtils.isEmpty(hospitalWards)) {
            return Collections.emptyMap();
        }
        Multimap<Long, HospitalWard> hospitalWardMultimap = LinkedHashMultimap.create();
        for (HospitalWard hospitalWard : hospitalWards) {
            hospitalWardMultimap.put(hospitalWard.getHospitalId(), hospitalWard);
        }
        return hospitalWardMultimap.asMap();
    }

    @Override
    public HospitalWard readHospitalWard(long hospitalWardId) {
        return hospitalWardMapper.selectHospitalWard(hospitalWardId);
    }

    @Override
    public void createWard(HospitalWard hospitalWard) {
        hospitalWardMapper.insertHospitalWard(hospitalWard);
    }

    @Override
    public void updateWard(HospitalWard hospitalWard) {
        hospitalWardMapper.updateHospitalWard(hospitalWard);
    }

    @Override
    public HospitalWard createWardWithNameCheck(HospitalWard hospitalWard) {
        List<HospitalWard> hospitalWards = hospitalWardMapper.selectHospitalWardByName(hospitalWard);
        if (hospitalWards.isEmpty()) {
            createWard(hospitalWard);
            return hospitalWard;
        }
        return hospitalWards.get(0);
    }

    @Override
    public void deleteWard(long hospitalWardId) {
        hospitalWardMapper.deleteHospitalWard(hospitalWardId);
    }

    @Override
    public void updateHospitalImage(long hospitalId, byte[] hospitalImage) {
        Map<String, Object> hospitalImageUpdateMap = new HashMap<String, Object>();
        hospitalImageUpdateMap.put("id", hospitalId);
        hospitalImageUpdateMap.put("imageData", hospitalImage);
        hospitalMapper.updateHospitalImage(hospitalImageUpdateMap);
    }

    @Override
    public void updateHospitalWardImage(long hospitalWardId, byte[] hospitalWardImage) {
        Map<String, Object> hospitalImageUpdateMap = new HashMap<String, Object>();
        hospitalImageUpdateMap.put("id", hospitalWardId);
        hospitalImageUpdateMap.put("imageData", hospitalWardImage);
        hospitalWardMapper.updateHospitalWardImage(hospitalImageUpdateMap);
    }

    @Override
    public InputStream getHospitalImage(long hospitalId) {
        ImageData imageData = hospitalMapper.selectHospitalImage(hospitalId);
        if (imageData == null || imageData.getData() == null) {
            return null;
        } else {
            return new ByteArrayInputStream(imageData.getData());
        }
    }

    @Override
    public InputStream getHospitalWardImage(long hospitalWardId) {
        ImageData imageData = hospitalWardMapper.selectHospitalWardImage(hospitalWardId);
        if (imageData == null || imageData.getData() == null) {
            return null;
        } else {
            return new ByteArrayInputStream(imageData.getData());
        }
    }
}
