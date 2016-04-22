package com.ra.dissection.protocol.util;

import java.text.Normalizer;

/**
 * Created by lka on 22.04.16.
 */
public class AsciiString {

    private final String ascii;

    public AsciiString(String value) {
        value = value.replaceAll("Ą", "A");
        value = value.replaceAll("Ć", "C");
        value = value.replaceAll("Ę", "E");
        value = value.replaceAll("Ł", "L");
        value = value.replaceAll("Ń", "N");
        value = value.replaceAll("Ó", "O");
        value = value.replaceAll("Ś", "S");
        value = value.replaceAll("Ż", "Z");
        value = value.replaceAll("Ź", "Z");
        value = value.replaceAll("ą", "a");
        value = value.replaceAll("ć", "c");
        value = value.replaceAll("ę", "e");
        value = value.replaceAll("ł", "l");
        value = value.replaceAll("ń", "n");
        value = value.replaceAll("ó", "o");
        value = value.replaceAll("ś", "s");
        value = value.replaceAll("ż", "z");
        value = value.replaceAll("ź", "z");
        this.ascii = value;
    }

    public String get() {
        return ascii;
    }
}
