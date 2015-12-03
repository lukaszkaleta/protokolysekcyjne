package com.ra.dissection.protocol.mvc.propertyeditors;

import com.ra.dissection.protocol.domain.common.Time;

import java.beans.PropertyEditorSupport;

/**
 * @author lukaszkaleta
 * @since 27.04.13 07:35
 */
public class TimePropertyEditor extends PropertyEditorSupport {

    @Override
    public String getAsText() {
        Time time = (Time) getValue();
        if (time == null) {
            return "";
        }
        Integer timeValue = time.getValue();
        if (timeValue == null || timeValue <= 0) {
            return "";
        }
        long hour = time.getHour();
        long minute = time.getMinute();
        String minuteText = String.valueOf(minute);
        if (minuteText.length() == 1) {
            minuteText = "0" + minuteText;
        }
        String hourText = String.valueOf(hour);
        if (hourText.length() == 1) {
            hourText = "0" + hourText;
        }
        return hourText + ":" + minuteText;
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        String[] timeSplited = text.split(":");
        if (timeSplited.length == 2) {
            String hourValue = timeSplited[0];
            String minuteValue = timeSplited[1];
            int hour = Integer.parseInt(hourValue);
            int minute = Integer.parseInt(minuteValue);
            setValue(new Time(hour * 60 + minute));
        }
    }
}
