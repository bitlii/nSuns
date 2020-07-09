package com.bitco.nsuns;

import com.bitco.nsuns.items.Templates;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TemplatesTest {

    @Test
    public void checkTemplate4day() {
        assertEquals(Templates.create4day(10, 10, 10, 10).size(), 4);
    }
}
