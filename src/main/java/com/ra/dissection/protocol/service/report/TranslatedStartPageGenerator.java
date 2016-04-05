package com.ra.dissection.protocol.service.report;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.ra.dissection.protocol.domain.protocol.DissectionDiagnose;
import com.ra.dissection.protocol.domain.protocol.DissectionDiagnoseOption;
import com.ra.dissection.protocol.domain.report.DissectionProtocolReport;
import com.ra.dissection.protocol.service.report.components.ReportChunks;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @author lukaszkaleta
 * @since 08.06.13 22:17
 */
@Component
public class TranslatedStartPageGenerator extends StartPage implements ReportPageGenerator {

    @Override
    public byte[] generate(long reportId, DissectionProtocolReport dissectionProtocol) {
        return reportPage.createPage(dissectionProtocol, this);
    }

    @Override
    protected Map<Long, StartPage.DissectionDiagnoseValue> getDissectionDiagnoseValues(DissectionProtocolReport dissectionProtocol) {
        Map<Long, DissectionDiagnose> dissectionDiagnoses = dissectionProtocol.getDissectionDiagnoses();
        Map<Long, StartPage.DissectionDiagnoseValue> dissectionDiagnoseLatin = new LinkedHashMap<Long, DissectionDiagnoseValue>();
        for (Map.Entry<Long, DissectionDiagnose> dissectionDiagnoseEntry : dissectionDiagnoses.entrySet()) {
            DissectionDiagnose value = dissectionDiagnoseEntry.getValue();
            dissectionDiagnoseLatin.put(dissectionDiagnoseEntry.getKey(), new StartPage.DissectionDiagnoseValue(
                    value.isSpaceAbove(),
                    value.isSpaceBelow(),
                    value.getName().getTranslated()));
        }
        return dissectionDiagnoseLatin;
    }

    @Override
    protected Collection<String> getDissectionDiagnoseOptionValues(Long dissectionDiagnoseId, DissectionProtocolReport dissectionProtocol) {
        Collection<DissectionDiagnoseOption> dissectionDiagnoseOptions = dissectionProtocol.getDissectionDiagnoseOptions(dissectionDiagnoseId);
        return Collections2.transform(dissectionDiagnoseOptions, new Function<DissectionDiagnoseOption, String>() {
            @Override
            public String apply(com.ra.dissection.protocol.domain.protocol.DissectionDiagnoseOption input) {
                return ReportChunks.cleanPreposition(input.getName().getTranslated());
            }
        });
    }
}
