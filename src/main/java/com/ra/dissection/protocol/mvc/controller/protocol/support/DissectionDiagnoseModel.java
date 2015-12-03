package com.ra.dissection.protocol.mvc.controller.protocol.support;

import com.ra.dissection.protocol.domain.protocol.DissectionDiagnose;
import com.ra.dissection.protocol.domain.settings.DissectionDiagnoseSource;

/**
 * This object extends dissection diagnose and contains some support for view "logic" fields.
 *
 * @author lukaszkaleta
 * @since 6.8.4.0-R04v44 15.06.13 13:20
 */
public class DissectionDiagnoseModel {

    /**
     * Diagnose which this model describes.
     */
    private DissectionDiagnose dissectionDiagnose;

    /**
     * Flag which determine if together with dissection diagnose update or create dissection diagnose source should be
     * created.
     */
    private boolean createSource;

    public DissectionDiagnoseModel() {
    }

    public DissectionDiagnoseModel(DissectionDiagnose dissectionDiagnose) {
        this.dissectionDiagnose = dissectionDiagnose;
    }

    public DissectionDiagnose getDissectionDiagnose() {
        return dissectionDiagnose;
    }

    public void setDissectionDiagnose(DissectionDiagnose dissectionDiagnose) {
        this.dissectionDiagnose = dissectionDiagnose;
    }

    public boolean isCreateSource() {
        return createSource;
    }

    public void setCreateSource(boolean createSource) {
        this.createSource = createSource;
    }
}
