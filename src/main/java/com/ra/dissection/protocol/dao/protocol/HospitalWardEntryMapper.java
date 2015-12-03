package com.ra.dissection.protocol.dao.protocol;

import com.ra.dissection.protocol.domain.protocol.HospitalWardEntry;

import java.util.List;
import java.util.Map;

/**
 * @author lukaszkaleta
 * @since 17.05.13 09:09
 */
public interface HospitalWardEntryMapper {

    void insertHospitalWardEntry(HospitalWardEntry hospitalWardEntry);

    List<HospitalWardEntry> selectProtocolHospitalWardEntries(long dissectionProtocolId);

    void deleteHospitalWardEntries(long dissectionProtocolId);

    void deleteHospitalWardFromDissectionProtocol(Map<String, Object> removeParams);
}
