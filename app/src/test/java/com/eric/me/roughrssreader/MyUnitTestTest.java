package com.eric.me.roughrssreader;

import static org.junit.Assert.*;
import org.junit.Test;

public class MyUnitTestTest {
    @Test
    public void readFileExist() {
        assertEquals(DataHelper.getFile("test.txt"), "");
    }
}
