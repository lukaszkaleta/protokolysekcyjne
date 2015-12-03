package com.ra.dissection.protocol.util;

import com.ra.dissection.protocol.domain.protocol.BasicData;
import com.ra.dissection.protocol.domain.protocol.DeathStoryEntry;
import com.ra.dissection.protocol.domain.protocol.DeathStoryName;
import org.springframework.util.StringUtils;

import java.util.Calendar;
import java.util.Date;

/**
 * @author lukaszkaleta
 * @since 31.07.13 07:40
 */
public class PatientDateCalculator {

    public static int getPatientYearsAge(BasicData basicData) {
        DeathStoryEntry storyEntry = basicData.getDeathStory().getStoryEntry(DeathStoryName.DEATH);
        String identificationNumber = basicData.getPatient().getIdentificationNumber();
        if (storyEntry == null || storyEntry.getDate() == null) {
            storyEntry = new DeathStoryEntry();
            storyEntry.setDate(new Date());
        }
        if (StringUtils.isEmpty(identificationNumber)) {
            return 0;
        }
        PeselValidator peselValidator = new PeselValidator(identificationNumber);
        if (!peselValidator.isValid()) {
            return 0;
        }
        int birthYear = peselValidator.getBirthYear();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(storyEntry.getDate());
        int storyYear = calendar.get(Calendar.YEAR);
        return storyYear - birthYear;
    }
}
