package com.ra.dissection.protocol.dao.protocol;

import com.ra.dissection.protocol.domain.protocol.HistopathologicalExamination;

import java.util.List;
import java.util.Map;

/**
 * @author lukaszkaleta
 * @since 6.8.4.0-R04v44 10.06.13 07:03
 */
public interface HistopathologicalExaminationMapper {

    void deleteDissectionProtocolHistopathologicalExamination(long dissectionProtocolId);

    List<HistopathologicalExamination> selectHistopathologicalExaminationForDissectionProtocol(long dissectionProtocolId);

    void updateHistopathologicalExamination(HistopathologicalExamination histopathologicalExamination);

    void insertHistopathologicalExamination(HistopathologicalExamination histopathologicalExamination);

    void insertClone(Map<String, Long> dissectionProtocolIds);

    void deleteHistopathologicalExamination(long histopathologicalExaminationId);
}
