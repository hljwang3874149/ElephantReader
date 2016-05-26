package reader.simple.com.simple_reader.common;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Time {

    /**
     * yyyy-MM-dd HH:mm:ss
     *
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String getNowYMDHMSTime() {


        SimpleDateFormat mDateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss");
        String date = mDateFormat.format(new Date());
        return date;
    }

    /**
     * MM-dd HH:mm:ss
     *
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String getNowMDHMSTime() {

        SimpleDateFormat mDateFormat = new SimpleDateFormat(
                "MM-dd HH:mm:ss");
        String date = mDateFormat.format(new Date());
        return date;
    }

    /**
     * MM-dd
     *
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String getNowYMD() {

        SimpleDateFormat mDateFormat = new SimpleDateFormat(
                "yyyy-MM-dd");
        String date = mDateFormat.format(new Date());
        return date;
    }

    /**
     * yyyy-MM-dd
     *
     * @param date
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String getYMD(Date date) {

        SimpleDateFormat mDateFormat = new SimpleDateFormat(
                "yyyy-MM-dd");
        String dateS = mDateFormat.format(date);
        return dateS;
    }

    /**
     * 设置午夜定时器, 午夜12点发送广播, MIDNIGHT_ALARM_FILTER.
     * 实际测试可能会有一分钟左右的偏差.
     *
     * @param context
     *         上下文
     */
    public static void setMidnightAlarm(Context context) {
        Context appContext = context.getApplicationContext();
        Intent intent = new Intent();
//        MIDNIGHT_ALARM_FILTER

        PendingIntent pi = PendingIntent.getBroadcast(appContext, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager am = (AlarmManager) appContext.getSystemService(Context.ALARM_SERVICE);

        // 午夜12点的标准计时, 来源于SO, 实际测试可能会有一分钟左右的偏差.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.AM_PM, Calendar.AM);
        calendar.add(Calendar.DAY_OF_MONTH, 1);

        // 显示剩余时间
        long now = Calendar.getInstance().getTimeInMillis();
//        showLogs("剩余时间(秒): " + ((calendar.getTimeInMillis() - now) / 1000));

        // 设置之前先取消前一个PendingIntent
        am.cancel(pi);

        // 设置每一天的计时器
        am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pi);
    }
}
