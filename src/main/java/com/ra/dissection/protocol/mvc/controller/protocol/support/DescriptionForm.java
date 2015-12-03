package com.ra.dissection.protocol.mvc.controller.protocol.support;

import com.ra.dissection.protocol.domain.protocol.DescriptionPoint;

import java.util.List;

/**
 * Contains all description points which are saved in one run.
 *
 * Created by lka on 14.11.15.
 */
public class DescriptionForm {

    private List<DescriptionPoint> descriptionPoints;

    public DescriptionForm() {
        // Empty constructor for binding
    }

    public DescriptionForm(List<DescriptionPoint> descriptionPointList) {
        this.descriptionPoints = descriptionPointList;
    }

    public List<DescriptionPoint> getDescriptionPoints() {
        return descriptionPoints;
    }

    public void setDescriptionPoints(List<DescriptionPoint> descriptionPoints) {
        this.descriptionPoints = descriptionPoints;
    }
}
