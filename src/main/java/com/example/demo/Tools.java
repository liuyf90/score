package com.example.demo;

import java.math.BigDecimal;
import java.text.DecimalFormat;
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

        days = (int) ((toDate.getTime() - fromDate.getTime())
                / (24 * 60 * 60 * 1000));

        return days;
    }

    public static double hoursDiff(Date fromDate, Date toDate)
            throws ParseException {


//        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
 //       DecimalFormat df=new DecimalFormat("0.00");//设置保留位数
//        BigDecimal b1 = new BigDecimal(Double.toString(toDate.getTime() - fromDate.getTime()));
//        BigDecimal b2 = new BigDecimal(Double.toString((60 * 60 * 1000)));
////        String hours =  df.format ((toDate.getTime() - fromDate.getTime())
////                / (24 * 60 * 60 * 1000));
//       Double hours = b1.divide(b2, 3, BigDecimal.ROUND_HALF_UP).doubleValue();
        Double hours=Arith.div((double)toDate.getTime() - fromDate.getTime(), (60 * 60 * 1000));

        return hours;
    }
}
