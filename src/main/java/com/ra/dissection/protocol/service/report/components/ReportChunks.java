package com.ra.dissection.protocol.service.report.components;

import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author lukaszkaleta
 * @since 09.06.13 21:05
 */
public class ReportChunks {

    public static String getDate(Date date) {
        if (date == null) {
            return "";
        }
        return new SimpleDateFormat("dd.MM.yyyy").format(date);
    }

    public static String cleanPreposition(String text) {
        if (StringUtils.isEmpty(text)) {
            return text;
        }

        text = text.replaceAll(" o ", " o\u00a0");
        text = text.replaceAll(" z ", " z\u00a0");
        text = text.replaceAll(" w ", " w\u00a0");
        text = text.replaceAll(" u ", " u\u00a0");
        text = text.replaceAll(" i ", " i\u00a0");
        text = text.replaceAll(" a ", " a\u00a0");
        text = text.replaceAll(" na ", " na\u00a0");
        text = text.replaceAll(" do ", " do\u00a0");
        text = text.replaceAll(" od ", " od\u00a0");

        return text;
    }
}
