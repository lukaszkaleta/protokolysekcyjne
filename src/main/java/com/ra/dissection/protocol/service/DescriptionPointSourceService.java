package com.ra.dissection.protocol.service;

import com.google.common.collect.Multimap;
import com.ra.dissection.protocol.domain.settings.DescriptionPointSource;
import com.ra.dissection.protocol.domain.settings.DescriptionPointType;
import com.ra.dissection.protocol.domain.settings.DissectionDiagnoseSource;
import com.ra.dissection.protocol.domain.settings.DissectionProtocolCategory;

import java.util.List;

/**
 * @author lukaszkaleta
 * @since 10.05.13 14:17
 */
public interface DescriptionPointSourceService extends GenericService<DescriptionPointSource> {

    List<DescriptionPointSource> getDescriptionPointsSources(DissectionProtocolCategory.Name categoryName, DescriptionPointType descriptionPointType);

    Multimap<Integer, DescriptionPointSource> getPointsForCategory(DissectionProtocolCategory.Name categoryName, DescriptionPointType descriptionPointType);

    int getNextPoint(DissectionProtocolCategory.Name dissectionProtocolCategoryName);

    int getNextPosition(int point, DissectionProtocolCategory.Name dissectionProtocolCategoryName);

    List<DissectionDiagnoseSource> getDissectionDiagnoseWhichDescriptionReplacement(long generalDescriptionPointSourceId);
}
