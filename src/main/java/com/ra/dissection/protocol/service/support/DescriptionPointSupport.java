package com.ra.dissection.protocol.service.support;

import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Multimap;
import com.ra.dissection.protocol.domain.common.Pointing;
import com.ra.dissection.protocol.domain.protocol.DescriptionPoint;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;
import sun.security.krb5.internal.crypto.Des;

import java.util.*;

/**
 * @author lukaszkaleta
 * @since 12.05.13 18:51
 */
@Component
public class DescriptionPointSupport {

    public <P extends Pointing> Multimap<Integer, P> pointingMultimap(List<P> pointingList) {
        Multimap<Integer, P> pointingLinkedListMultimap = LinkedListMultimap.create();
        if (!CollectionUtils.isEmpty(pointingList)) {
            for (P pointing : pointingList) {
                pointingLinkedListMultimap.put(pointing.getPoint(), pointing);
            }
        }
        return pointingLinkedListMultimap;
    }

    public <P extends Pointing> Map<Integer, Collection<P>> pointingMap(List<P> pointingList) {
        if (CollectionUtils.isEmpty(pointingList)) {
            return Collections.emptyMap();
        }
        return pointingMultimap(pointingList).asMap();
    }

    public List<DescriptionPoint> changes(List<DescriptionPoint> current, List<DescriptionPoint> incoming) {
        Map<Long, DescriptionPoint> currentMap = new HashMap<>();
        for(DescriptionPoint descriptionPoint : current) {
            currentMap.put(descriptionPoint.getId(), descriptionPoint);
        }
        Map<Long, DescriptionPoint> incomingMap = new HashMap<>();
        for(DescriptionPoint descriptionPoint : incoming) {
            incomingMap.put(descriptionPoint.getId(), descriptionPoint);
        }

        List<DescriptionPoint> changed = new ArrayList<>();
        for(Map.Entry<Long, DescriptionPoint> incomingEntry : incomingMap.entrySet()) {
            Long incomingId = incomingEntry.getKey();
            DescriptionPoint incomingDescriptionPoint = incomingEntry.getValue();
            DescriptionPoint currentDescriptionPoint = currentMap.get(incomingId);
            if (!Objects.equals(incomingDescriptionPoint.getDescriptionPointSource().getDescription(), currentDescriptionPoint.getDescriptionPointSource().getDescription())) {
                currentDescriptionPoint.getDescriptionPointSource().setDescription(incomingDescriptionPoint.getDescriptionPointSource().getDescription());
                changed.add(currentDescriptionPoint);
            }
        }
        return changed;
    }
}
