package com.eric.me.roughrssreader;

import org.junit.Test;

public class MyUnitTestTest {
    @Test
    public void readFileExist() {
        assertEquals(IOHelper.getFile("test.txt"), "");
    }
}
