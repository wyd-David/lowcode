package com.yabushan.common.utils;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.lang.management.ManagementFactory;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 时间工具类
 *
 * @author ruoyi
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils {
    public static String YYYY = "yyyy";

    public static String YYYY_MM = "yyyy-MM";

    public static String YYYY_MM_DD = "yyyy-MM-dd";

    public static String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

    public static String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    private static String[] parsePatterns = {
            "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM",
            "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM",
            "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM"};

    /**
     * 获取当前Date型日期
     *
     * @return Date() 当前日期
     */
    public static Date getNowDate() {
        return new Date();
    }

    /**
     * 获取当前日期, 默认格式为yyyy-MM-dd
     *
     * @return String
     */
    public static String getDate() {
        return dateTimeNow(YYYY_MM_DD);
    }

    public static final String getTime() {
        return dateTimeNow(YYYY_MM_DD_HH_MM_SS);
    }

    public static final String dateTimeNow() {
        return dateTimeNow(YYYYMMDDHHMMSS);
    }

    public static final String dateTimeNow(final String format) {
        return parseDateToStr(format, new Date());
    }

    public static final String dateTime(final Date date) {
        return parseDateToStr(YYYY_MM_DD, date);
    }

    public static final String parseDateToStr(final String format, final Date date) {
        return new SimpleDateFormat(format).format(date);
    }

    public static final Date dateTime(final String format, final String ts) {
        try {
            return new SimpleDateFormat(format).parse(ts);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 日期路径 即年/月/日 如2018/08/08
     */
    public static final String datePath() {
        Date now = new Date();
        return DateFormatUtils.format(now, "yyyy/MM/dd");
    }

    /**
     * 日期路径 即年/月/日 如20180808
     */
    public static final String dateTime() {
        Date now = new Date();
        return DateFormatUtils.format(now, "yyyyMMdd");
    }

    /**
     * 日期型字符串转化为日期 格式
     */
    public static Date parseDate(Object str) {
        if (str == null) {
            return null;
        }
        try {
            return parseDate(str.toString(), parsePatterns);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 获取服务器启动时间
     */
    public static Date getServerStartDate() {
        long time = ManagementFactory.getRuntimeMXBean().getStartTime();
        return new Date(time);
    }

    /**
     * 计算两个时间差
     */
    public static String getDatePoor(Date endDate, Date nowDate) {
        long nd =(long) 1000 * 24 * 60 * 60;
        long nh =(long)1000 * 60 * 60;
        long nm =(long) 1000 * 60;
        // long ns = 1000;
        // 获得两个时间的毫秒时间差异
        long diff = endDate.getTime() - nowDate.getTime();
        // 计算差多少天
        long day = diff / nd;
        // 计算差多少小时
        long hour = diff % nd / nh;
        // 计算差多少分钟
        long min = diff % nd % nh / nm;
        // 计算差多少秒//输出结果
        // long sec = diff % nd % nh % nm / ns;
        return day + "天" + hour + "小时" + min + "分钟";
    }

    /**
     * 给时间加一天  接受时间类型：yyyy-mm-dd的字符串
     *
     * @return
     */
    public static String addOneDay(String time) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
        //将接收的time中的年月日截取成String数组
        String[] timeStr = time.substring(0,9).split("-");
        //确定time中的那一年的那个月份有多少天
        Calendar calendar = Calendar.getInstance();
        int year = Integer.valueOf(timeStr[0]);
        int month = Integer.valueOf(timeStr[1]);
        int day = Integer.valueOf(timeStr[2]);
        calendar.set(year, month, 0);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        //判断time中的日期是否超过了该年该月的最后一天，如果超过就进行以下处理
        if (day >= dayOfMonth) {
            String date = null;
            //判断月份是否是12月，不是就往后加一个月；是的话就把年份加一年
            if (month < 12) {
                month++;
                Date parse = dateFormat.parse(year + "-" + month + "-01");
                date = dateFormat.format(parse);
            } else if (month == 12) {
                year++;
                date = year + "-01-01";
            }
            return date;
        }
        //time中的日期没有超过该年该月的最后一天，则天数往后加一天
        calendar.setTime(dateFormat.parse(time));
        calendar.add(calendar.DATE, -1);
        return dateFormat.format(calendar.getTime());
    }
    /**
     * 计算两个时间差多少天
     */
    public static int getDatePoorDay(Date endDate, Date nowDate) {
        long nd =(long) 1000 * 24 * 60 * 60;
        long nh =(long) 1000 * 60 * 60;
        long nm =(long) 1000 * 60;
        // long ns = 1000;
        // 获得两个时间的毫秒时间差异
        long diff = endDate.getTime() - nowDate.getTime();
        // 计算差多少天
        long day = diff / nd;
        // 计算差多少小时
        long hour = diff % nd / nh;
        // 计算差多少分钟
        long min = diff % nd % nh / nm;
        // 计算差多少秒//输出结果
        // long sec = diff % nd % nh % nm / ns;
        return (int) day;
    }
    // 获取两个时间相差分钟数
    public static int getTime(Date endDate,Date nowDate) {

//        SimpleDateFormat df = new SimpleDateFormat("YYYY/MM/dd HH:mm:ss.SSS");
        long NTime =endDate.getTime();
        //从对象中拿到时间
        long OTime = nowDate.getTime();
        int diff= (int) ((NTime-OTime)/1000);
        return diff;
    }
    /**
     * 计算两个时间差多少秒
     */
    public static String getDatePoorSec(Date endDate, Date nowDate) {
        long nd =(long) 1000 * 24 * 60 * 60;
        long nh =(long) 1000 * 60 * 60;
        long nm =(long) 1000 * 60;
        long ns =(long) 1000;
//        // 获得两个时间的毫秒时间差异
        long diff = endDate.getTime() - nowDate.getTime();
//        // 计算差多少天
//        long day = diff / nd;
//        // 计算差多少小时
//        long hour = diff % nd / nh;
//        // 计算差多少分钟
//        long min = diff % nd % nh / nm;
        // 计算差多少秒//输出结果
        long sec = diff % nd % nh % nm / ns;
        return String.valueOf(sec);
    }

    public static void main(String[] args) throws ParseException {
//        String datePoorSec = String.valueOf(getTime(DateUtils.parseDate("2021-11-25 17:34:32"), DateUtils.parseDate("2021-11-25 17:29:55")));
//        System.out.println(datePoorSec);
//        String str = "6541111";
        System.out.println(addOneDay("2021-12-21"));
    }

    /**
     * 根据多少秒转时分秒
     */

    public static String getSFMByMiao(String str) {
        int seconds = Integer.parseInt(str);
        int temp=0;
        StringBuffer sb=new StringBuffer();
        temp = seconds/3600;
        sb.append((temp<10)?"0"+temp+"时":""+temp+"时");

        temp=seconds%3600/60;
        sb.append((temp<10)?"0"+temp+"分":""+temp+"分");

        temp=seconds%3600%60;
        sb.append((temp<10)?"0"+temp:""+temp+"秒");

        System.out.println(sb.toString());
        return sb.toString();
    }

    /**
     * date2比date1多的天数
     *
     * @param date1
     * @param date2
     * @return
     */
    public static int differentDays(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        int day1 = cal1.get(Calendar.DAY_OF_YEAR);
        int day2 = cal2.get(Calendar.DAY_OF_YEAR);
        int year1 = cal1.get(Calendar.YEAR);
        int year2 = cal2.get(Calendar.YEAR);
        if (year1 != year2)   //同一年
        {
            int timeDistance = 0;
            for (int i = year1; i < year2; i++) {
                if (i % 4 == 0 && i % 100 != 0 || i % 400 == 0)    //闰年
                {
                    timeDistance += 366;
                } else    //不是闰年
                {
                    timeDistance += 365;
                }
            }

            return timeDistance + (day2 - day1);
        } else    //不同年
        {
            System.out.println("判断day2 - day1 : " + (day2 - day1));
            return day2 - day1;
        }
    }

    /**
     * 获取指定日期是星期几<br>
     *
     * @param date
     * @return 指定日期是星期几
     */
    public static String getWeekOfDate(Date date) {
        String[] weekDays = {"7", "1", "2", "3", "4", "5", "6"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return weekDays[w];
    }

    /**
     * 获取指定日期是星期几<br>
     *
     * @param date
     * @return 指定日期是星期几
     */
    public static String getWeekOfDateText(Date date) {
        String[] weekDays = {"日", "一", "二", "三", "四", "五", "六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return weekDays[w];
    }

    /**
     * 健身服务判断当前时间是否在[startTime, endTime]区间，注意时间格式要一致
     *
     * @param nowTime   当前时间
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return
     */
    public static boolean isEffectiveDate(Date nowTime, Date startTime, Date endTime, Date nowEndTime) {
        if (nowEndTime.getTime() == endTime.getTime()) {
            return true;
        }
        //开始时间判断
        Calendar date = Calendar.getInstance();
        date.setTime(nowTime);

        Calendar begin = Calendar.getInstance();
        begin.setTime(startTime);

        Calendar end = Calendar.getInstance();
        end.setTime(endTime);


        //结束时间判断
        Calendar date1 = Calendar.getInstance();
        date1.setTime(nowEndTime);

        Calendar begin1 = Calendar.getInstance();
        begin1.setTime(startTime);

        Calendar end1 = Calendar.getInstance();
        end1.setTime(endTime);

        //开始时间判断,都要判断是否包含
        if ((date.after(begin) && date.before(end)) || (date1.after(begin1) && date1.before(end1))) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断当前时间是否在[startTime, endTime]区间，注意时间格式要一致
     *
     * @param nowTime   当前时间
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return
     */
    public static boolean isGGEffectiveDate(Date nowTime, Date startTime, Date endTime) {
        if (nowTime.getTime() == endTime.getTime()) {
            return true;
        }
        //开始时间判断
        Calendar date = Calendar.getInstance();
        date.setTime(nowTime);

        Calendar begin = Calendar.getInstance();
        begin.setTime(startTime);

        Calendar end = Calendar.getInstance();
        end.setTime(endTime);

        //开始时间判断,都要判断是否包含
        if ((date.after(begin) && date.before(end))) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 实现当前时间往前推N小时
     *
     * @param date 时间
     * @param i    推送的日期格式，比如小时，分钟 天 ，年月
     * @param d    推送的具体数值
     * @return
     */
    public static Date dateRoll(Date date, int i, int d) {
        // 获取Calendar对象并以传进来的时间为准
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        // 将现在的时间滚动固定时长,转换为Date类型赋值
        calendar.add(i, d);
        // 转换为Date类型再赋值
        date = calendar.getTime();
        return date;
    }

    /**
     * 解析字符串为日期类型
     */
    public static Date parseDateByStr(String date, String str) {
        try {
            return new SimpleDateFormat(str).parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 比较两个日期之间的大小
     *
     * @param d1
     * @param d2
     * @return 前者大于后者返回true 反之false
     */
    public static boolean compareDate(Date d1, Date d2) {
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(d1);
        c2.setTime(d2);

        int result = c1.compareTo(c2);
        if (result >= 0)
            return true;
        else
            return false;
    }

    /**
     *  
     *      * 获取当月的 天数 
     *      * 
     */
    public static int getCurrentMonthDay() {

        Calendar a = Calendar.getInstance();
        a.set(Calendar.DATE, 1);
        a.roll(Calendar.DATE, -1);
        int maxDate = a.get(Calendar.DATE);
        return maxDate;
    }


    /**
     *  
     *      * 根据年 月 获取对应的月份 天数 
     *      * 
     */
    public static int getDaysByYearMonth(int year, int month) {

        Calendar a = Calendar.getInstance();
        a.set(Calendar.YEAR, year);
        a.set(Calendar.MONTH, month - 1);
        a.set(Calendar.DATE, 1);
        a.roll(Calendar.DATE, -1);
        int maxDate = a.get(Calendar.DATE);
        return maxDate;
    }


    /**
     * 通过开始日期和结束日期构建一个key为日期，value默认为0的Map(无序)，用于构建图表的数据
     *
     * @param startDate 格式形如：yyyy-MM-dd
     * @param endDate   格式形如：yyyy-MM-dd
     * @param unit      hour(小时)，day(天)，week(周)，month(月)
     * @return
     * @throws ParseException
     */
    public static Map<String, String> buildMap(String startDate, String endDate, String unit) throws ParseException {
        Map<String, String> map = new HashMap<String, String>();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM");
        if (unit.equals("hour")) {
            for (long i = sdf.parse((startDate)).getTime(); i <= sdf.parse(endDate).getTime(); i += 3600 * 1000) {
                map.put(sdf1.format(new Date(i)), "0");
            }
        } else if (unit.equals("day")) {
            for (long i = sdf.parse(startDate).getTime(); i <= sdf.parse(endDate).getTime(); i += 3600 * 1000 * 24) {
                String weekOfDateText = getWeekOfDateText(new Date(i));
                map.put(sdf.format(new Date(i)), weekOfDateText);
            }
        } else if (unit.equals("week")) {
            for (long i = sdf.parse(startDate).getTime(); i <= sdf.parse(endDate).getTime(); i += 3600 * 1000 * 24
                    * 7) {
                map.put(sdf.format(new Date(i)), "0");
            }
        } else if (unit.equals("month")) {
            for (long i = sdf.parse(startDate).getTime(); i <= sdf.parse(endDate).getTime(); i += 3600 * 1000 * 24) {
                map.put(sdf2.format(new Date(i)), "0");
            }
        }
        return map;
    }


    /**
     * 获取指定月份的第一天
     *
     * @return
     */
    public static String getLastMonthFirstDate(String dateStr) {
        Date date = null;
        Calendar calendar = Calendar.getInstance();
        try {
            date = new SimpleDateFormat("yyyy-MM").parse(dateStr);
            calendar.setTime(date); // 设置为当前时间
            calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
    }

    /**
     * 获取指定月份的最后一天
     *
     * @return
     */
    public static String getLastMonthLastDate(String dateStr) {
        Date date = null;
        Calendar calendar = Calendar.getInstance();
        try {
            date = new SimpleDateFormat("yyyy-MM").parse(dateStr);
            calendar.setTime(date); // 设置为当前时间
            calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
    }


    //根据年月构建当月的日期以及日期所属周期
    public static Map<String, String> getDateSortByMonth(String month) {
        Map<String, String> stringMap = new HashMap<>();
        try {
            String lastMonthFirstDate = getLastMonthFirstDate(month);
            String lastMonthLastDate = getLastMonthLastDate(month);
            Map<String, String> map = DateUtils.buildMap(lastMonthFirstDate, lastMonthLastDate, "day");
            //排序日期
            stringMap = MapKeyComparator.sortMapByKey(map);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return stringMap;
    }

    /**
     * 获取年
     */
    public static String getYear() {
        Calendar calendar = Calendar.getInstance();
        String year = String.valueOf(calendar.get(Calendar.YEAR));
        return year;
    }

    /**
     * 获取月
     */
    public static String getMonth() {
        Calendar calendar = Calendar.getInstance();
        String month = String.valueOf(calendar.get(Calendar.MONTH) + 1);
        return month;
    }

    /**
     * 获取年
     */
    public static String getDay() {
        Calendar calendar = Calendar.getInstance();
        String day_of_month = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
        return day_of_month;
    }
    /**
     * 计算两个时间差
     */
    public static BigDecimal getHourPoor(Date endDate, Date nowDate)
    {
        long nd =(long) 1000 * 24 * 60 * 60;
        long nh =(long) 1000 * 60 * 60;
        long nm =(long) 1000 * 60;
        // long ns = 1000;
        // 获得两个时间的毫秒时间差异
        long diff = endDate.getTime() - nowDate.getTime();
        // 计算差多少天
        long day = diff / nd;
        // 计算差多少小时
        long hour = diff % nd / nh;
        // 计算差多少分钟
        long min = diff % nd % nh / nm;
        BigDecimal time = new BigDecimal(hour);
        if(min==30){
            //time =time.add(new BigDecimal(0.5));
            time=time.add(BigDecimal.valueOf(0.5));
        }
        return time ;
    }
    /**
     * 获取指定月份的第一天到时分秒
     *
     * @return
     */
    public static String getLastMonthFirstTime(String dateStr) {
        Date date = null;
        Calendar calendar = Calendar.getInstance();
        try {
            date = new SimpleDateFormat("yyyy-MM").parse(dateStr);
            calendar.setTime(date); // 设置为当前时间
            calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS).format(calendar.getTime());
    }

    /**
     * 获取当前时间的上一个月
     */
    public static String getYearAndMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MONTH,-1);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        return new SimpleDateFormat(YYYY_MM).format(calendar.getTime());
    }

    /**
     * 获取当前时间的上x月的天数
     */
    public static int getDays(int i) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH,-i);
        calendar.set(Calendar.DATE, 1);
        calendar.roll(Calendar.DATE, -1);
        int maxDate = calendar.get(Calendar.DATE);
        return maxDate;
    }
}
