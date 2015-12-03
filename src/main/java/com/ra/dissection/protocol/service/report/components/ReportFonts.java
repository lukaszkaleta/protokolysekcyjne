package com.ra.dissection.protocol.service.report.components;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.BaseFont;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lukaszkaleta
 * @since 10.06.13 20:55
 */
@Component
public class ReportFonts {

    private String windowsFont = "c:/windows/fonts/times.ttf";
    private String linuxFont = "/opt/DissectionProtocol/times.ttf";

    private final BaseFont baseFont;

    private final Map<Integer, Font> whiteBoldFont = new HashMap<Integer, Font>();
    private final Map<Integer, Font> blueBoldItalicFont = new HashMap<Integer, Font>();
    private final Map<Integer, Font> normalFont = new HashMap<Integer, Font>();
    private final Map<Integer, Font> boldFont = new HashMap<Integer, Font>();
    private final Map<Integer, Font> italicFont = new HashMap<Integer, Font>();
    private final Map<Integer, Font> italicBoldFont = new HashMap<Integer, Font>();

    public ReportFonts() {
        String fontLocation;
        String osName = System.getProperty("os.name");
        if (osName.equals("Linux")) {
            fontLocation = linuxFont;
        } else {
            fontLocation = windowsFont;
        }

        try {
            baseFont = BaseFont.createFont(fontLocation, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        } catch (DocumentException e) {
            throw new IllegalStateException(e.getMessage(), e);
        } catch (IOException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    public Font getWhiteBoldFont(final int size) {
        Font font = getFont(whiteBoldFont, size, new FontConstructor() {
            @Override
            public Font create() {
                Font f = new Font(baseFont, size, Font.BOLD);
                f.setColor(BaseColor.WHITE);
                return f;
            }
        });
        return font;
    }

    public Font getBlueBoldItalic(final int size) {
        Font font = getFont(blueBoldItalicFont, size, new FontConstructor() {
            @Override
            public Font create() {
                Font f = new Font(baseFont, size, Font.BOLDITALIC);
                f.setColor(BaseColor.BLUE);
                return f;
            }
        });
        return font;
    }

    public Font getNormal(final int size) {
        Font font = getFont(normalFont, size, new FontConstructor() {
            @Override
            public Font create() {
                return new Font(baseFont, size);
            }
        });
        return font;
    }

    public Font getBold(final int size) {
        Font font = getFont(boldFont, size, new FontConstructor() {
            @Override
            public Font create() {
                return new Font(baseFont, size, Font.BOLD);
            }
        });
        return font;
    }

    public Font getItalic(final int size) {
        Font font = getFont(italicFont, size, new FontConstructor() {
            @Override
            public Font create() {
                return new Font(baseFont, size, Font.ITALIC);
            }
        });
        return font;
    }

    public Font getBoldItalic(final int size) {
        Font font = getFont(italicBoldFont, size, new FontConstructor() {
            @Override
            public Font create() {
                return new Font(baseFont, size, Font.BOLDITALIC);
            }
        });
        return font;
    }

    private Font getFont(Map<Integer, Font> fonts, int size, FontConstructor fontConstructor) {
        Font font = fonts.get(size);
        if (font == null) {
            font = fontConstructor.create();
            fonts.put(size, font);
        }
        return font;
    }

    private interface FontConstructor {
        public Font create();
    }
}
