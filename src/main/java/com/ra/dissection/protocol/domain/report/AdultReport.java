package com.ra.dissection.protocol.domain.report;

import com.ra.dissection.protocol.domain.protocol.DeathStoryName;
import com.ra.dissection.protocol.domain.settings.DissectionProtocolCategory;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author lukaszkaleta
 * @since 18.05.13 17:32
 */
public class AdultReport extends DissectionProtocolReport {

    public AdultReport() {
        super(DissectionProtocolCategory.Name.ADULT);
    }

    //
    // Basic data specific
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

    public Date getDeathDate() {
        return super.getDateFor(DeathStoryName.DEATH);
    }

    public String getDeathHour() {
        return super.getHourFor(DeathStoryName.DEATH);
    }

    public String getDeathMinute() {
        return super.getMinuteFor(DeathStoryName.DEATH);
    }

    public String getHoursAfterDeath() {
        return super.getAutopsyHoursAfter(DeathStoryName.DEATH);
    }

    public String getYearAge() {
        String identificationNumberYearAfter = super.getIdentificationNumberYearAfter(DeathStoryName.DEATH);
        String yearsAge = getBasicData().getPatient().getYearsAge();
        return identificationNumberYearAfter.isEmpty() ? (yearsAge != null ? yearsAge : "") : identificationNumberYearAfter;
    }
}
