package com.coisini.curtain.util;

import com.coisini.curtain.core.common.PageCounter;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

/**
 * @Description 公用工具类
 * @author coisini
 * @Version 1.0
 */
public class CommonUtil {

    /**
     * page参数转换
     * @param start
     * @param count
     */
    public static PageCounter convertToPageParameter(Integer start, Integer count) {

        int pageNum = start / count;

        return PageCounter.builder()
                          .page(pageNum)
                          .count(count)
                          .build();
    }

    /**
     * 时间相加
     * @param calendar
     * @param seconds 秒
     * @return
     */
    public static Calendar addSomeSeconds(Calendar calendar, int seconds) {
        calendar.add(Calendar.SECOND, seconds);
        return calendar;
    }

    /**
     * 是否在时间内
     * @param date
     * @param start
     * @param end
     * @return
     */
    public static Boolean isInTimeLine(Date date, Date start, Date end) {
        Long time = date.getTime();
        Long startTime = start.getTime();
        Long endTime = end.getTime();
        if (time > startTime && time < endTime) {
            return true;
        }
        return false;
    }

    /**
     * 判断是否过期
     * @param startTime 开始时间
     * @param period 时效
     * @return
     */
    public static Boolean isOutOfDate(Date startTime, Long period) {
        Long now = Calendar.getInstance().getTimeInMillis();
        Long startTimeStamp = startTime.getTime();
        Long periodMillSecond = period * 1000;
        return now > (startTimeStamp + periodMillSecond);
    }

    /**
     * 判断是否过期
     * @param expiredTime 过期时间
     * @return
     */
    public static Boolean isOutOfDate(Date expiredTime) {
        return Calendar.getInstance().getTimeInMillis() > expiredTime.getTime();
    }

    /**
     * 价格转换
     * 元 -》 分
     * @param p
     * @return
     */
    public static String yuanToCentsPlainString(BigDecimal p){
        p = p.multiply(new BigDecimal("100"));
        return CommonUtil.toPlain(p);
    }

    /**
     * 转换文本串
     * @param p
     * @return
     */
    public static String toPlain(BigDecimal p){
        return p.stripTrailingZeros().toPlainString();
    }

    /**
     * 生成10位的时间戳
     * @return
     */
    public static String timestamp10(){
        Long timestamp13 = Calendar.getInstance().getTimeInMillis();
        String timestamp13Str = timestamp13.toString();
        return timestamp13Str.substring(0, timestamp13Str.length() - 3);
    }

}
