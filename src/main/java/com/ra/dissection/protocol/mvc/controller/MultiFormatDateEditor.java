package com.ra.dissection.protocol.mvc.controller;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.util.StringUtils;

import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author lukaszkaleta
 * @since 05.07.13 18:19
 */
public class MultiFormatDateEditor extends PropertyEditorSupport {

    private final List<CustomDateEditor> customDateEditors;

    private final CustomDateEditor mainCustomDateEditor;

    public MultiFormatDateEditor(List<CustomDateEditor> customDateEditors) {
        this.customDateEditors = customDateEditors;
        this.mainCustomDateEditor = customDateEditors.get(0);
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        boolean set = false;
        for (CustomDateEditor customDateEditor : customDateEditors) {
            try {
                customDateEditor.setAsText(text);
                setValue(customDateEditor.getValue());
                set = true;
                break;
            } catch (IllegalArgumentException e) {
            }
        }
        if (!set) {
            throw new IllegalArgumentException("Can not find proper custom date editor which will allow to set date: " + text);
        }
    }

    /**
     * Format the Date as String, using the specified DateFormat.
     */
    @Override
    public String getAsText() {
        Object value = getValue();
        if (value != null) {
            mainCustomDateEditor.setValue(value);
            return mainCustomDateEditor.getAsText();
        }
        return "";
    }
}
