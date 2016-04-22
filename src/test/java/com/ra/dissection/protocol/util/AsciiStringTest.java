package com.ra.dissection.protocol.util;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by lka on 22.04.16.
 */
public class AsciiStringTest {

    @Test
    public void testName() throws Exception {
        String value = "WładysławGałązka";
        assertEquals("WladyslawGalazka", new AsciiString(value).get());
    }
}