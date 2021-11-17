package com.umpay.online.tools.util;

import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author tianxiaoyang
 * @Date: 2020-03-16 11:05
 * @Description:
 */
public class DateUtil {

    // ==格式到年==
    /**
     * 日期格式，年份，例如：2004，2008
     */
    public static final String DATE_FORMAT_YYYY = "yyyy";


    // ==格式到年月 ==
    /**
     * 日期格式，年份和月份，例如：200707，200808
     */
    public static final String DATE_FORMAT_YYYYMM = "yyyyMM";

    /**
     * 日期格式，年份和月份，例如：200707，2008-08
     */
    public static final String DATE_FORMAT_YYYY_MM = "yyyy-MM";


    // ==格式到年月日==
    /**
     * 日期格式，年月日，例如：050630，080808
     */
    public static final String DATE_FORMAT_YYMMDD = "yyMMdd";

    /**
     * 日期格式，年月日，用横杠分开，例如：06-12-25，08-08-08
     */
    public static final String DATE_FORMAT_YY_MM_DD = "yy-MM-dd";

    /**
     * 日期格式，年月日，例如：20050630，20080808
     */
    public static final String DATE_FORMAT_YYYYMMDD = "yyyyMMdd";

    /**
     * 日期格式，年月日，用横杠分开，例如：2006-12-25，2008-08-08
     */
    public static final String DATE_FORMAT_YYYY_MM_DD = "yyyy-MM-dd";

    /**
     * 日期格式，年月日，例如：2016.10.05
     */
    public static final String DATE_FORMAT_POINTYYYYMMDD = "yyyy.MM.dd";

    /**
     * 日期格式，年月日，例如：2016年10月05日
     */
    public static final String DATE_TIME_FORMAT_YYYY年MM月DD日 = "yyyy年MM月dd日";


    // ==格式到年月日 时分 ==

    /**
     * 日期格式，年月日时分，例如：200506301210，200808081210
     */
    public static final String DATE_FORMAT_YYYYMMDDHHmm = "yyyyMMddHHmm";

    /**
     * 日期格式，年月日时分，例如：20001230 12:00，20080808 20:08
     */
    public static final String DATE_TIME_FORMAT_YYYYMMDD_HH_MI = "yyyyMMdd HH:mm";

    /**
     * 日期格式，年月日时分，例如：2000-12-30 12:00，2008-08-08 20:08
     */
    public static final String DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI = "yyyy-MM-dd HH:mm";


    // ==格式到年月日 时分秒==
    /**
     * 日期格式，年月日时分秒，例如：20001230120000，20080808200808
     */
    public static final String DATE_TIME_FORMAT_YYYYMMDDHHMISS = "yyyyMMddHHmmss";

    /**
     * 日期格式，年月日时分秒，年月日用横杠分开，时分秒用冒号分开
     * 例如：2005-05-10 23：20：00，2008-08-08 20:08:08
     */
    public static final String DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI_SS = "yyyy-MM-dd HH:mm:ss";


    // ==格式到年月日 时分秒 毫秒==
    /**
     * 日期格式，年月日时分秒毫秒，例如：20001230120000123，20080808200808456
     */
    public static final String DATE_TIME_FORMAT_YYYYMMDDHHMISSSSS = "yyyyMMddHHmmssSSS";


    // ==特殊格式==
    /**
     * 日期格式，月日时分，例如：10-05 12:00
     */
    public static final String DATE_FORMAT_MMDDHHMI = "MM-dd HH:mm";


    /**
     * 时间格式，时分秒，例如：120011
     */
    public static final String TIME_FORMAT_HHMMSS = "HHmmss";

    /**
     * 时间格式，时分秒，例如：12:00:11
     */
    public static final String TIME_FORMAT_HHMMSS_COLON = "HH:mm:ss";

    /**
     * 时间格式，时分秒，例如：12_00_11
     */
    public static final String TIME_FORMAT_HH_MM_SS = "HH_mm_ss";

    /**
     * 时间格式，时分秒，例如：120011123
     */
    public static final String TIME_FORMAT_HHMMSSSSS = "HHmmssSSS";

    /**
     * 时间格式，时分秒，例如：12.00.11.123
     */
    public static final String TIME_FORMAT_HH_MM_SSS_SS = "HH:mm:ss.SSS";

    /**
     * 时间格式，时分秒，例如：200326121212
     */
    public static String DATE_FORMAT_YYMMDDHHMMSS = "yyMMddHHmmss";


    /**
     * 格式化日期
     * @param date 日期
     * @param pattern 格式
     * @return
     */
    public static String dateFormat(LocalDateTime date, String pattern) {
        date = checkDate(date);
        pattern = checkDatePattern(pattern);
        return date.format(DateTimeFormatter.ofPattern(pattern));
    }

    public static String checkDatePattern(String pattern) {
        if (StringUtils.isEmpty(pattern)) {
            pattern = DATE_FORMAT_YYYYMMDD;
        }
        return pattern;
    }

    public static LocalDateTime checkDate(LocalDateTime date) {
        if (date == null) {
            date = LocalDateTime.now();
        }
        return date;
    }

    public static String checkTimePattern(String pattern) {
        if (StringUtils.isEmpty(pattern)) {
            pattern = TIME_FORMAT_HHMMSS;
        }
        return pattern;
    }

    public static String dateFormatYYYYMMDD(LocalDateTime date) {
        return dateFormat(date, DATE_FORMAT_YYYYMMDD);
    }

    public static String dateAfterFormatYYYYMMDD(LocalDateTime date){
        return date.plusDays(1).format(DateTimeFormatter.ofPattern(DATE_FORMAT_YYYYMMDD));
    }

    public static String dateFormatYYMMDDHHMMSS(LocalDateTime date) {
        return dateFormat(date, DATE_FORMAT_YYMMDDHHMMSS);
    }

    public static String dateFormatYYYYMMDD() {
        return dateFormatYYYYMMDD(LocalDateTime.now());
    }

    public static String dateFormatYYMMDDHHMMSS() {
        return dateFormatYYMMDDHHMMSS(LocalDateTime.now());
    }
    public static String timeFormatHHMMSS(LocalDateTime date) {
        return timeFormat(date, TIME_FORMAT_HHMMSS);
    }

    public static String timeFormatHHMMSS() {
        return timeFormatHHMMSS(LocalDateTime.now());
    }

    /**
     * 格式化时间
     * @param time 时间
     * @param pattern 格式
     * @return
     */
    public static String timeFormat(LocalDateTime time, String pattern) {
        time = checkDate(time);
        pattern = checkTimePattern(pattern);
        return time.format(DateTimeFormatter.ofPattern(pattern));
    }

    public static String getTimeString() {
        return LocalTime.now().format(DateTimeFormatter.ofPattern(TIME_FORMAT_HHMMSS));
    }

    public static String getDateString() {
        return LocalDate.now().format(DateTimeFormatter.ofPattern(DATE_FORMAT_YYYYMMDD));
    }

    public static String getDateTimeString() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT_YYYYMMDDHHMISS));
    }

    public static String getDateFormatYY_MM_DDHHMMSS() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI_SS));
    }
    public static String getDateFormatYY_MM_DDHHMMSS(Date time) {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI_SS));
    }


    /**
     * @author : gaozhiguo
     * @date : 2020-04-15 10:39
     * @version : V1.0
     * @description : 匹配日期格式
     **/
    public static boolean checkValidityDate(String validityDate) {
        String eL = "[0-9]{4}[0-9]{2}[0-9]{2}-[0-9]{4}[0-9]{2}[0-9]{2}";
        Pattern p = Pattern.compile(eL);
        Matcher m = p.matcher(validityDate);
        return m.matches();
    }
}
