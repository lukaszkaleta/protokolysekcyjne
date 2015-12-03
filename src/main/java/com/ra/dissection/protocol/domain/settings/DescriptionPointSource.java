package com.ra.dissection.protocol.domain.settings;

import com.ra.dissection.protocol.domain.common.Pointing;

import java.io.Serializable;

/**
 * Source for description points.
 * It contains default values for descriptions.
 *
 * @author lukaszkaleta
 * @since 10.05.13 13:54
 */
public class DescriptionPointSource implements Serializable, Pointing {

    private static final long serialVersionUID = 13051013540000001l;

    /**
     * Unique id of description point source.
     */
    private long id;

    /**
     * Main point.
     * Positive ... values from 1 to 21 are possible in dissection protocols.
     * Where 21 is special point for which category should be set.
     */
    private int point;

    /**
     * Position in point.
     */
    private int position;

    /**
     * Default description.
     */
    private String description;

    /**
     * Optional category.
     * If this property is null description point source can be used in all types of dissection protocols.
     * If this property is not null description point source can be used only in specified type of dissection protocol.
     */
    private DissectionProtocolCategory category = new DissectionProtocolCategory();

    /**
     * Type of description point.
     */
    private DescriptionPointType type;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public DissectionProtocolCategory getCategory() {
        return category;
    }

    public void setCategory(DissectionProtocolCategory category) {
        this.category = category;
    }

    public DescriptionPointType getType() {
        return type;
    }

    public void setType(DescriptionPointType type) {
        this.type = type;
    }

    public String getDigitPosition() {
        return DescriptionPointDigit.get(position);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DescriptionPointSource that = (DescriptionPointSource) o;

        if (id != that.id) return false;
        if (point != that.point) return false;
        if (position != that.position) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (category != null ? !category.equals(that.category) : that.category != null) return false;
        return type == that.type;

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + point;
        result = 31 * result + position;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (category != null ? category.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }
}
