package com.ra.dissection.protocol.domain.settings;

/**
 * @author lukaszkaleta
 * @since 23.07.13 11:56
 */
public class DescriptionPointDigit {

    public static final String[] DIGIT = {"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","r","s","t","u","w","z","y","z"};

    public static String get(int position) {
        int p = position - 1;
        if (p < 0 || p >= DIGIT.length) {
            return "a";
        }
        return DIGIT[p];
    }
}
