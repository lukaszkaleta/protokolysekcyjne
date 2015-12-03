package com.ra.dissection.protocol.service;

import com.ra.dissection.protocol.domain.settings.Hospital;
import com.ra.dissection.protocol.domain.settings.HospitalWard;

import java.io.InputStream;
import java.util.Collection;
import java.util.Map;

/**
 * @author lukaszkaleta
 * @since 25.04.13 14:12
 */
public interface HospitalService extends GenericService<Hospital> {

    Map<Long,Collection<HospitalWard>> getHospitalWards();

    HospitalWard readHospitalWard(long hospitalWardId);

    void createWard(HospitalWard hospitalWard);

    void updateWard(HospitalWard hospitalWard);

    HospitalWard createWardWithNameCheck(HospitalWard hospitalWard);

    void deleteWard(long hospitalWardId);

    void updateHospitalImage(long hospitalId, byte[] hospitalImage);

    void updateHospitalWardImage(long hospitalWardId, byte[] hospitalWardImage);

    InputStream getHospitalImage(long hospitalId);

    InputStream getHospitalWardImage(long hospitalWardId);
}
