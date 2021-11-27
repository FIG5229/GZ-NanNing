/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.common.utils;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 时间计算工具类
 *
 * @author ThinkGem
 * @version 2013-11-03
 */
public class TimeUtils {

    public static String toTimeString(long time) {
        TimeUtils t = new TimeUtils(time);
        int day = t.get(TimeUtils.DAY);
        int hour = t.get(TimeUtils.HOUR);
        int minute = t.get(TimeUtils.MINUTE);
        int second = t.get(TimeUtils.SECOND);
        StringBuilder sb = new StringBuilder();
        if (day > 0) {
            sb.append(day).append("天");
        }
        if (hour > 0) {
            sb.append(hour).append("时");
        }
        if (minute > 0) {
            sb.append(minute).append("分");
        }
        if (second > 0) {
            sb.append(second).append("秒");
        }
        return sb.toString();
    }

    /**
     * 时间字段常量，表示“秒”
     */
    public final static int SECOND = 0;

    /**
     * 时间字段常量，表示“分”
     */
    public final static int MINUTE = 1;

    /**
     * 时间字段常量，表示“时”
     */
    public final static int HOUR = 2;

    /**
     * 时间字段常量，表示“天”
     */
    public final static int DAY = 3;

    /**
     * 各常量允许的最大值
     */
    private final int[] maxFields = {59, 59, 23, Integer.MAX_VALUE - 1};

    /**
     * 各常量允许的最小值
     */
    private final int[] minFields = {0, 0, 0, Integer.MIN_VALUE};

    /**
     * 默认的字符串格式时间分隔符
     */
    private String timeSeparator = ":";

    /**
     * 时间数据容器
     */
    private int[] fields = new int[4];

    /**
     * 无参构造，将各字段置为 0
     */
    public TimeUtils() {
        this(0, 0, 0, 0);
    }

    /**
     * 使用时、分构造一个时间
     *
     * @param hour   小时
     * @param minute 分钟
     */
    public TimeUtils(int hour, int minute) {
        this(0, hour, minute, 0);
    }

    /**
     * 使用时、分、秒构造一个时间
     *
     * @param hour   小时
     * @param minute 分钟
     * @param second 秒
     */
    public TimeUtils(int hour, int minute, int second) {
        this(0, hour, minute, second);
    }

    /**
     * 使用一个字符串构造时间<br/>
     * Time time = new Time("14:22:23");
     *
     * @param time 字符串格式的时间，默认采用“:”作为分隔符
     */
    public TimeUtils(String time) {
        this(time, null);
//    	System.out.println(time);
    }

    /**
     * 使用时间毫秒构建时间
     *
     * @param time
     */
    public TimeUtils(long time) {
        this(new Date(time));
    }

    /**
     * 使用日期对象构造时间
     *
     * @param date
     */
    public TimeUtils(Date date) {
        this(DateFormatUtils.formatUTC(date, "HH:mm:ss"));
    }

    /**
     * 使用天、时、分、秒构造时间，进行全字符的构造
     *
     * @param day    天
     * @param hour   时
     * @param minute 分
     * @param second 秒
     */
    public TimeUtils(int day, int hour, int minute, int second) {
        initialize(day, hour, minute, second);
    }

    /**
     * 使用一个字符串构造时间，指定分隔符<br/>
     * Time time = new Time("14-22-23", "-");
     *
     * @param time 字符串格式的时间
     */
    public TimeUtils(String time, String timeSeparator) {
        if (timeSeparator != null) {
            setTimeSeparator(timeSeparator);
        }
        parseTime(time);
    }

    /**
     * 设置时间字段的值
     *
     * @param field 时间字段常量
     * @param value 时间字段的值
     */
    public void set(int field, int value) {
        if (value < minFields[field]) {
            throw new IllegalArgumentException(value + ", time value must be positive.");
        }
        fields[field] = value % (maxFields[field] + 1);
        // 进行进位计算
        int carry = value / (maxFields[field] + 1);
        if (carry > 0) {
            int upFieldValue = get(field + 1);
            set(field + 1, upFieldValue + carry);
        }
    }

    /**
     * 获得时间字段的值
     *
     * @param field 时间字段常量
     * @return 该时间字段的值
     */
    public int get(int field) {
        if (field < 0 || field > fields.length - 1) {
            throw new IllegalArgumentException(field + ", field value is error.");
        }
        return fields[field];
    }

    /**
     * 将时间进行“加”运算，即加上一个时间
     *
     * @param time 需要加的时间
     * @return 运算后的时间
     */
    public TimeUtils addTime(TimeUtils time) {
        TimeUtils result = new TimeUtils();
        int up = 0;     // 进位标志
        for (int i = 0; i < fields.length; i++) {
            int sum = fields[i] + time.fields[i] + up;
            up = sum / (maxFields[i] + 1);
            result.fields[i] = sum % (maxFields[i] + 1);
        }
        return result;
    }

    /**
     * 将时间进行“减”运算，即减去一个时间
     *
     * @param time 需要减的时间
     * @return 运算后的时间
     */
    public TimeUtils subtractTime(TimeUtils time) {
        TimeUtils result = new TimeUtils();
        int down = 0;       // 退位标志
        for (int i = 0, k = fields.length - 1; i < k; i++) {
            int difference = fields[i] + down;
            if (difference >= time.fields[i]) {
                difference -= time.fields[i];
                down = 0;
            } else {
                difference += maxFields[i] + 1 - time.fields[i];
                down = -1;
            }
            result.fields[i] = difference;
        }
        result.fields[DAY] = fields[DAY] - time.fields[DAY] + down;
        return result;
    }

    /**
     * 获得时间字段的分隔符
     *
     * @return
     */
    public String getTimeSeparator() {
        return timeSeparator;
    }

    /**
     * 设置时间字段的分隔符（用于字符串格式的时间）
     *
     * @param timeSeparator 分隔符字符串
     */
    public void setTimeSeparator(String timeSeparator) {
        this.timeSeparator = timeSeparator;
    }

    private void initialize(int day, int hour, int minute, int second) {
        set(DAY, day);
        set(HOUR, hour);
        set(MINUTE, minute);
        set(SECOND, second);
    }

    private void parseTime(String time) {
        if (time == null) {
            initialize(0, 0, 0, 0);
            return;
        }
        String t = time;
        int field = DAY;
        set(field--, 0);
        int p = -1;
        while ((p = t.indexOf(timeSeparator)) > -1) {
            parseTimeField(time, t.substring(0, p), field--);
            t = t.substring(p + timeSeparator.length());
        }
        parseTimeField(time, t, field--);
    }

    private void parseTimeField(String time, String t, int field) {
        if (field < SECOND || t.length() < 1) {
            parseTimeException(time);
        }
        char[] chs = t.toCharArray();
        int n = 0;
        for (int i = 0; i < chs.length; i++) {
            if (chs[i] <= ' ') {
                continue;
            }
            if (chs[i] >= '0' && chs[i] <= '9') {
                n = n * 10 + chs[i] - '0';
                continue;
            }
            parseTimeException(time);
        }
        set(field, n);
    }

    private void parseTimeException(String time) {
        throw new IllegalArgumentException(time + ", time format error, HH"
                + this.timeSeparator + "mm" + this.timeSeparator + "ss");
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(16);
        sb.append(fields[DAY]).append(',').append(' ');
        buildString(sb, HOUR).append(timeSeparator);
        buildString(sb, MINUTE).append(timeSeparator);
        buildString(sb, SECOND);
        return sb.toString();
    }

    private StringBuilder buildString(StringBuilder sb, int field) {
        if (fields[field] < 10) {
            sb.append('0');
        }
        return sb.append(fields[field]);
    }

    public int hashCode() {
        final int PRIME = 31;
        int result = 1;
        result = PRIME * result + Arrays.hashCode(fields);
        return result;
    }

    /**
     * 获取当月所有天
     *
     * @return
     */
    public static List<String> getDayListOfMonth() {
        List<String> list = new ArrayList<String>();
        Calendar aCalendar = Calendar.getInstance(Locale.CHINA);
        int year = aCalendar.get(Calendar.YEAR);//年份
        int month = aCalendar.get(Calendar.MONTH) + 1;//月份
        int day = aCalendar.getActualMaximum(Calendar.DATE);
        for (int i = 1; i <= day; i++) {
            String aDate = year + "-" + month + "-" + i;
            list.add(aDate);
        }
        return list;
    }

    /**
     * 获取当月所有天和星期
     *
     * @return
     */
    public static List<Map<String, String>> getDayListOfOneMonth(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        List<Map<String, String>> resultList = new ArrayList<>();
        String[] weeks = {"日", "一", "二", "三", "四", "五", "六"};
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(sdf.parse(date));
        } catch (Exception e) {
            e.printStackTrace();
        }
        int days = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        int weekday = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        // 1是星期日
        String cWeekDay = weeks[weekday];
        Map<String, String> fristDate = new HashMap<>();
        fristDate.put("code", weekday + "");
        fristDate.put("mcode", "1");
        fristDate.put("weekday", cWeekDay);
        fristDate.put("date", sdf.format(calendar.getTime()));
        resultList.add(fristDate);
        for (int i = 1; i < days; i++) {
            calendar.add(Calendar.DATE, 1);
            int weekday2 = calendar.get(Calendar.DAY_OF_WEEK) - 1;
            // 1是星期日
            String cWeekDay2 = weeks[weekday2];
            Map<String, String> map = new HashMap<>();
            map.put("code", weekday2 + "");
            map.put("mcode", String.valueOf(i+1));
            map.put("weekday", cWeekDay2);
            map.put("date", sdf.format(calendar.getTime()));
            resultList.add(map);
        }

        return resultList;
    }

    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final TimeUtils other = (TimeUtils) obj;
        return Arrays.equals(fields, other.fields);
    }

    /**
     * 根据生日计算当前周岁数
     */
    public static int getCurrentAge(Date birthday) {
        if (birthday==null){
            return 0;
        }
        // 当前时间
        Calendar curr = Calendar.getInstance();
        // 生日
        Calendar born = Calendar.getInstance();
        born.setTime(birthday);
        // 年龄 = 当前年 - 出生年
        int age = curr.get(Calendar.YEAR) - born.get(Calendar.YEAR);
        if (age <= 0) {
            return 0;
        }
        // 如果当前月份小于出生月份: age-1
        // 如果当前月份等于出生月份, 且当前日小于出生日: age-1
        int currMonth = curr.get(Calendar.MONTH);
        int currDay = curr.get(Calendar.DAY_OF_MONTH);
        int bornMonth = born.get(Calendar.MONTH);
        int bornDay = born.get(Calendar.DAY_OF_MONTH);
        if ((currMonth < bornMonth) || (currMonth == bornMonth && currDay <= bornDay)) {
            age--;
        }
        return age < 0 ? 0 : age;
    }


    /**
     * 根据年龄计算出生日期
     * 通过当前时间减去年龄计算出生日期
     * @param age
     * @return
     */
    public static Date getBornBirthday(Integer age) {
        if (age ==null|| age <=0 || age >200 ){
            return new Date();
        }
        // 当前时间
        Calendar curr = Calendar.getInstance();
        // 生日
        Calendar born = Calendar.getInstance();
        curr.setTime(new Date());
        int year = curr.get(Calendar.YEAR);
        year = year - age;
        int month = born.get(Calendar.MONTH)+1;
        int day = born.get(Calendar.DAY_OF_MONTH);
        String result = year+"-"+month+"-"+day;
        return DateUtils.parseDate(result);
    }


    public static int getCurrentTime(Date startDate,Date endDate) {
        if (startDate==null){
            return 0;
        }
        // 截止时间
        Calendar curr = Calendar.getInstance();
        if (endDate !=null){
            curr.setTime(endDate);
        }
        // 开始时间
        Calendar born = Calendar.getInstance();
        born.setTime(startDate);
        // 时间差 = 截止年 - 开始年
        int age = curr.get(Calendar.YEAR) - born.get(Calendar.YEAR);
        if (age <= 0) {
            return 0;
        }
        // 如果截止月份小于开始月份: age-1
        // 如果截止月份等于开始月份, 且截止日小于开始日: age-1
        int currMonth = curr.get(Calendar.MONTH);
        int currDay = curr.get(Calendar.DAY_OF_MONTH);
        int bornMonth = born.get(Calendar.MONTH);
        int bornDay = born.get(Calendar.DAY_OF_MONTH);
        if ((currMonth < bornMonth) || (currMonth == bornMonth && currDay <= bornDay)) {
            age--;
        }
        return age < 0 ? 0 : age;
    }

    /**
     * 根据生日计算当前周岁数  差三个月
     */
    public static int getDiffThreeMonthCurrentAge(Date birthday) {
        if (birthday==null){
            return 0;
        }
        // 当前时间
        Calendar curr = Calendar.getInstance();
        // 生日
        Calendar born = Calendar.getInstance();
        born.setTime(birthday);
        born.add(Calendar.MONTH,-3);//提前三月
        // 年龄 = 当前年 - 出生年
        int age = curr.get(Calendar.YEAR) - born.get(Calendar.YEAR);
        if (age <= 0) {
            return 0;
        }
        // 如果当前月份小于出生月份: age-1
        // 如果当前月份等于出生月份, 且当前日小于出生日: age-1
        int currMonth = curr.get(Calendar.MONTH);
        int currDay = curr.get(Calendar.DAY_OF_MONTH);
        int bornMonth = born.get(Calendar.MONTH);
        int bornDay = born.get(Calendar.DAY_OF_MONTH);

        if ((currMonth < bornMonth) || (currMonth == bornMonth && currDay <= bornDay)) {
            age--;
        }
        return age < 0 ? 0 : age;
    }

    /*将秒和毫秒设置为0*/
    public static Date setSecondMILLISECOND(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        // 秒
        calendar.set(Calendar.SECOND, 0);
        // 毫秒
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }
}