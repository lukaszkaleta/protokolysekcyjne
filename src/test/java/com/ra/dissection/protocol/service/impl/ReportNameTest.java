package com.ra.dissection.protocol.service.impl;

import com.ra.dissection.protocol.domain.common.Patient;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by lka on 12/15/16.
 */
public class ReportNameTest {

    @Test
    public void stygar() throws Exception {
        Patient patient = new Patient();
        patient.setDescription("Stygar \"s\" Beaty - Bartosz - 4 dni 6 godz. 10 minut");
        String generate = new ReportName(patient).generate();
        assertEquals("Stygar", generate);
    }

    @Test
    public void slazak() throws Exception {
        Patient patient = new Patient();
        patient.setDescription("Ślązak \"c\" Bernadetty");
        String generate = new ReportName(patient).generate();
        assertEquals("Slazak", generate);
    }
}
