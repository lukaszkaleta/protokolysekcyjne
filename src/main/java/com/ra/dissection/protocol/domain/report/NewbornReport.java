package com.ra.dissection.protocol.domain.report;

import com.ra.dissection.protocol.domain.protocol.DeathStoryName;
import com.ra.dissection.protocol.domain.settings.DissectionProtocolCategory;

import java.util.Date;

/**
 * @author lukaszkaleta
 * @since 18.05.13 17:33
 */
public class NewbornReport extends DissectionProtocolReport {

    public NewbornReport() {
        super(DissectionProtocolCategory.Name.NEWBORN);
    }

    //
    // Basic data specific
    //

    //
    // ADMISSION
    //

    public Date getAdmissionDate() {
        return super.getDateFor(DeathStoryName.ADMISSION);
    }

    public String getAdmissionHour() {
        return super.getHourFor(DeathStoryName.ADMISSION);
    }

    public String getAdmissionMinute() {
        return super.getMinuteFor(DeathStoryName.ADMISSION);
    }

    //
    // CHILD BIRTH
    //

    public Date getChildBirthDate() {
        return super.getDateFor(DeathStoryName.CHILD_BIRTH);
    }

    public String getChildBirthHour() {
        return super.getHourFor(DeathStoryName.CHILD_BIRTH);
    }

    public String getChildBirthMinute() {
        return super.getMinuteFor(DeathStoryName.CHILD_BIRTH);
    }

    //
    // DEATH
    //

    public Date getDeathDate() {
        return super.getDateFor(DeathStoryName.DEATH);
    }

    public String getDeathHour() {
        return super.getHourFor(DeathStoryName.DEATH);
    }

    public String getDeathMinute() {
        return super.getMinuteFor(DeathStoryName.DEATH);
    }

    //
    //
    //

    public String getHoursAfterDeath() {
        return super.getAutopsyHoursAfter(DeathStoryName.DEATH);
    }

    public String getYearAge() {
        return super.getIdentificationNumberYearAfter(DeathStoryName.DEATH);
    }

}
