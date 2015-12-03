package com.ra.dissection.protocol.dao.protocol;

import com.ra.dissection.protocol.domain.protocol.DescriptionPoint;
import com.ra.dissection.protocol.domain.settings.DescriptionPointSource;
import com.ra.dissection.protocol.domain.settings.DissectionProtocolCategory;

import java.util.List;
import java.util.Map;

/**
 * @author lukaszkaleta
 * @since 12.05.13 09:33
 */
public interface DescriptionPointMapper {
    void insertDescriptionPoint(DescriptionPoint descriptionPoint);
    void insertDescriptionPointsForNewProtocol(Map<String, Object> parameters);

    void insertCloneDescriptionPoint(DescriptionPoint descriptionPoint);

    void updateDescriptionPoint(DescriptionPoint descriptionPoint);
    void deleteDescriptionPoint(long id);
    DescriptionPoint selectDescriptionPoint(long id);
    List<DescriptionPoint> selectDescriptionPoints();

    /**
     * Select description points which belongs to specified dissection protocol.
     *
     * @param dissectionProtocolId id of dissection protocol.
     * @return list of description points.
     */
    List<DescriptionPoint> selectDissectionProtocolDescriptionPoints(long dissectionProtocolId);

    /**
     * Update description points which were added as GENERAL to protocol but have dissection diagnose from this description point.
     *
     * @param entity
     */
    void updateUntouchedDescriptionPoints(DescriptionPointSource entity);

    /**
     * Delete description points which belongs to dissection protocol.
     *
     * @param dissectionProtocolId id of dissection protocol.
     */
    void deleteDissectionProtocolDescriptionPoints(long dissectionProtocolId);
}
