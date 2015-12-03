package com.ra.dissection.protocol.dao.settings;

import com.ra.dissection.protocol.domain.common.ImageData;
import com.ra.dissection.protocol.domain.settings.HospitalWard;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author lukaszkaleta
 * @since 08.05.13 23:04
 */
public interface HospitalWardMapper {
    HospitalWard selectHospitalWard(long hospitalWardId);
    void insertHospitalWard(HospitalWard hospitalWard);
    void updateHospitalWard(HospitalWard hospitalWard);
    void deleteHospitalWard(long hospitalWardId);
    List<HospitalWard> selectHospitalWards();
    List<HospitalWard> selectWardsInHospital(long hospitalId);

    List<HospitalWard> selectHospitalWardByName(HospitalWard hospitalWard);

    List<HospitalWard> selectDissectionHospitalWard(Long hospitalId);

    List<HospitalWard> selectHospitalWardsByIds(Long[] ids);

    void updateHospitalWardImage(Map<String, Object> hospitalWardImageUpdateMap);

    ImageData selectHospitalWardImage(long hospitalWardId);
}
