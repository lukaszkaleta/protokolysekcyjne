package com.ra.dissection.protocol.dao.settings;

import com.ra.dissection.protocol.domain.settings.Doctor;

import java.util.List;

/**
 * @author lukaszkaleta
 * @since 25.04.13 11:19
 */
public interface DoctorMapper {
    void insertDoctor(Doctor doctor);
    void updateDoctor(Doctor doctor);
    void deleteDoctor(long id);
    Doctor selectDoctor(long id);
    List<Doctor> selectDoctors();
    List<Doctor> filterDoctors(String pattern);
}
