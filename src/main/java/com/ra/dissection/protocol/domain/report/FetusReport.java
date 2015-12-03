package com.ra.dissection.protocol.domain.report;

import com.ra.dissection.protocol.domain.protocol.DeathStoryName;
import com.ra.dissection.protocol.domain.settings.DissectionProtocolCategory;

import java.util.Date;

/**
 * @author lukaszkaleta
 * @since 18.05.13 17:32
 */
public class FetusReport extends DissectionProtocolReport {

    public FetusReport() {
        super(DissectionProtocolCategory.Name.FETUS);
    }

    public String getHoursAfterDeathChildBirth() {
        return super.getAutopsyHoursAfter(DeathStoryName.DEATH_CHILD_BIRTH);
    }

    public Date getMotherAdmissionDate() {
        return super.getDateFor(DeathStoryName.MOTHER_ADMISSION);
    }

    public String getMotherAdmissionHour() {
        return super.getHourFor(DeathStoryName.MOTHER_ADMISSION);
    }

    public String getMotherAdmissionMinute() {
        return super.getMinuteFor(DeathStoryName.MOTHER_ADMISSION);
    }

    public Date getDeathChildBirthDate() {
        return super.getDateFor(DeathStoryName.DEATH_CHILD_BIRTH);
    }

    public String getDeathChildBirthHour() {
        return super.getHourFor(DeathStoryName.DEATH_CHILD_BIRTH);
    }

    public String getDeathChildBirthMinute() {
        return super.getMinuteFor(DeathStoryName.DEATH_CHILD_BIRTH);
    }

}
