package com.ra.dissection.protocol.domain.protocol;

import com.ra.dissection.protocol.domain.common.Idable;
import com.ra.dissection.protocol.domain.common.Pointing;
import com.ra.dissection.protocol.domain.settings.DescriptionPointSource;

/**
 * Runtime data which contains description points for protocol.
 *
 * @author lukaszkaleta
 * @since 11.05.13 07:05
 */
public class DescriptionPoint implements Pointing, Idable {

    private long id;

    /**
     * Id of protocol to which description point belongs.
     */
    private long dissectionProtocolId;

    /**
     * Order index of dissection diagnose description point.
     * This is useful when two dissection diagnosis are added to the same point.
     * In other words super.point and super.position are doubled and this value distinguish
     * two description points for the same position(and point) in description.
     */
    private int index = 1;

    /**
     * Flag which determine if source of description was modified.
     * True value means that description was edited.
     * False value means that description was not edited.
     */
    private boolean customization;

    /**
     * Contains description point data.
     */
    private DescriptionPointSource descriptionPointSource;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getDissectionProtocolId() {
        return dissectionProtocolId;
    }

    public void setDissectionProtocolId(long dissectionProtocolId) {
        this.dissectionProtocolId = dissectionProtocolId;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public boolean isCustomization() {
        return customization;
    }

    public void setCustomization(boolean customization) {
        this.customization = customization;
    }

    public DescriptionPointSource getDescriptionPointSource() {
        return descriptionPointSource;
    }

    public void setDescriptionPointSource(DescriptionPointSource descriptionPointSource) {
        this.descriptionPointSource = descriptionPointSource;
    }

    @Override
    public int getPoint() {
        return descriptionPointSource.getPoint();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DescriptionPoint that = (DescriptionPoint) o;

        if (id != that.id) return false;
        if (dissectionProtocolId != that.dissectionProtocolId) return false;
        if (index != that.index) return false;
        if (customization != that.customization) return false;
        return descriptionPointSource.equals(that.descriptionPointSource);

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (int) (dissectionProtocolId ^ (dissectionProtocolId >>> 32));
        result = 31 * result + index;
        result = 31 * result + (customization ? 1 : 0);
        result = 31 * result + descriptionPointSource.hashCode();
        return result;
    }
}
