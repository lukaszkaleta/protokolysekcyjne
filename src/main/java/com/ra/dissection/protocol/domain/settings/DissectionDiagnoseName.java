package com.ra.dissection.protocol.domain.settings;

import java.io.Serializable;

/**
 * Name of dissection diagnose.
 * Contains original latin name as well ass translated name.
 *
 * @author lukaszkaleta
 * @since 10.05.13 19:07
 */
public class DissectionDiagnoseName implements Serializable {

    private static final long serialVersionUID = 2013051019070000001l;

    /**
     * Latin name for dissection diagnose source.
     */
    private String latin;

    /**
     * This name is a translated version of latin name.
     * For example: in poland it will be polish name.
     */
    private String translated;

    public String getLatin() {
        return latin;
    }

    public void setLatin(String latin) {
        this.latin = latin;
    }

    public String getTranslated() {
        return translated;
    }

    public void setTranslated(String translated) {
        this.translated = translated;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DissectionDiagnoseName that = (DissectionDiagnoseName) o;

        if (latin != null ? !latin.equals(that.latin) : that.latin != null) return false;
        if (translated != null ? !translated.equals(that.translated) : that.translated != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = latin != null ? latin.hashCode() : 0;
        result = 31 * result + (translated != null ? translated.hashCode() : 0);
        return result;
    }
}
