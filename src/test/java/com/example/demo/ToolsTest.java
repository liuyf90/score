package com.example.demo;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created by liuyf90 on 2018/6/27.
 */
public class ToolsTest {
    @Test
    public void dateDiff() throws Exception {
        Date date2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2018-06-26 18:00:00");
        Date date1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2018-06-28 17:00:00");
        int i=Tools.dateDiff(date2,date1);
        System.out.print(i);

    }

}