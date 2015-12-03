package com.ra.dissection.protocol.service.impl;

import com.ra.dissection.protocol.dao.settings.DoctorMapper;
import com.ra.dissection.protocol.domain.settings.Doctor;
import com.ra.dissection.protocol.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lukaszkaleta
 * @since 25.04.13 11:23, 6.8.3.0-R04v33
 */
@Service
class DoctorServiceImpl implements DoctorService {

    @Autowired
    private DoctorMapper doctorMapper;

    @Override
    public void create(Doctor entity) {
        doctorMapper.insertDoctor(entity);
    }

    @Override
    public Doctor read(long id) {
        return doctorMapper.selectDoctor(id);
    }

    @Override
    public void update(Doctor entity) {
        doctorMapper.updateDoctor(entity);
    }

    @Override
    public Doctor delete(long id) {
        doctorMapper.deleteDoctor(id);
        return null;
    }

    @Override
    public List<Doctor> getAll() {
        return doctorMapper.selectDoctors();
    }

    @Override
    public List<Doctor> getFiltered(String filter) {
        return doctorMapper.filterDoctors(filter);
    }
}
