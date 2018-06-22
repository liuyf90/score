package com.example.demo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by liuyf90 on 2018/6/21.
 */
public class Tools {
    /**
     * 计算两个日期间的天数
     *
     * @param fromDate
     *            起始日期
     * @param toDate
     *            结束日期
     * @return
     * @throws ParseException
     */
    public static int dateDiff(Date fromDate, Date toDate)
            throws ParseException {
        int days = 0;

//        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");

        days = (int) Math.abs((toDate.getTime() - fromDate.getTime())
                / (24 * 60 * 60 * 1000)) + 1;

        return days;
    }
}
