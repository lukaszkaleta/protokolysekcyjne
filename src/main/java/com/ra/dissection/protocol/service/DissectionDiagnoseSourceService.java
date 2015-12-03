package com.ra.dissection.protocol.service;

import com.google.common.collect.Multimap;
import com.ra.dissection.protocol.domain.settings.DissectionDiagnoseSource;
import com.ra.dissection.protocol.domain.settings.DissectionDiagnoseSourceOption;
import com.ra.dissection.protocol.domain.settings.DissectionProtocolCategory;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author lukaszkaleta
 * @since 11.05.13 08:27
 */
public interface DissectionDiagnoseSourceService extends GenericService<DissectionDiagnoseSource> {

    List<DissectionDiagnoseSource> getAll(DissectionProtocolCategory category, String letter);

    DissectionDiagnoseSource readByDescriptionPointSource(long descriptionPointSourceId);

    List<String> getLatinLetters();

    List<DissectionDiagnoseSourceOption> getOptions(long dissectionDiagnoseSourceId);

    Multimap<Long, DissectionDiagnoseSourceOption> getOptions(Set<Long> dissectionDiagnoseSourceIds);

    boolean isOptionAvailable(long dissectionDiagnoseSourceId);

    Map<Long, Boolean> getOptionAvailableMap(Set<Long> dissectionDiagnoseSourceIds);

    DissectionDiagnoseSourceOption readOption(long dissectionDiagnoseSourceOptionId);

    void updateOption(DissectionDiagnoseSourceOption dissectionDiagnoseSourceOption);

    void createOption(DissectionDiagnoseSourceOption dissectionDiagnoseSourceOption);

    void deleteOption(long dissectionDiagnoseSourceOptionId);
}
