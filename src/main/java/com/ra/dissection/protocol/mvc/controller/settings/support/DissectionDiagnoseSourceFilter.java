package com.ra.dissection.protocol.mvc.controller.settings.support;

import com.ra.dissection.protocol.domain.settings.DissectionProtocolCategory;

/**
 * @author lukaszkaleta
 * @since 22.05.13 09:17
 */
public class DissectionDiagnoseSourceFilter {

    private DissectionProtocolCategory dissectionProtocolCategory;

    private String letter;

    public DissectionProtocolCategory getDissectionProtocolCategory() {
        return dissectionProtocolCategory;
    }

    public void setDissectionProtocolCategory(DissectionProtocolCategory dissectionProtocolCategory) {
        this.dissectionProtocolCategory = dissectionProtocolCategory;
    }

    public String getLetter() {
        return letter;
    }

    public void setLetter(String letter) {
        this.letter = letter;
    }

}
