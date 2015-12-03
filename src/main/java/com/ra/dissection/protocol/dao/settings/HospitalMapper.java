package com.ra.dissection.protocol.dao.settings;

import com.ra.dissection.protocol.domain.common.ImageData;
import com.ra.dissection.protocol.domain.settings.Hospital;

import java.util.List;
import java.util.Map;

/**
 * @author lukaszkaleta
 * @since 25.04.13 14:09
 */
public interface HospitalMapper {

    void insertHospital(Hospital hospital);
    void updateHospital(Hospital hospital);
    void deleteHospital(long id);
    Hospital selectHospital(long id);
    List<Hospital> selectHospitals();
    List<Hospital> filterHospitals(String pattern);

    void updateHospitalImage(Map<String, Object> hospitalImageUpdateMap);

    ImageData selectHospitalImage(long hospitalId);
}
