package reader.simple.com.simple_reader.common;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.location.LocationManager;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.PowerManager;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import java.lang.reflect.Field;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DeviceUtil {
    private static DeviceUtil instance;
    private Context context;
    private static String appVersion;
    private static String appSimpleVersion;

    private static Integer channel;

    public DeviceUtil(Context context) {
        this.context = context;
        instance = this;
    }

    public static DeviceUtil getInstance() {
        return instance;
    }

    /**
     * 获取屏幕分辨率 width_height
     *
     * @return
     */
    public String getScreen() {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics screenSize = new DisplayMetrics();
        display.getMetrics(screenSize);
        return screenSize.widthPixels + "*" + screenSize.heightPixels;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static int getScreenWidth(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics screenSize = new DisplayMetrics();
        display.getMetrics(screenSize);
        return screenSize.widthPixels;
    }

    public static int getScreenHeight(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics screenSize = new DisplayMetrics();
        display.getMetrics(screenSize);
        return screenSize.heightPixels;
    }

    public static int getDensityDpi(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics screenSize = new DisplayMetrics();
        display.getMetrics(screenSize);
        return screenSize.densityDpi;
    }

    public static boolean isHasSD() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }


    public int getWindowHeight(Context context) {
        return getScreenHeight(context) - getStatusBarHeight(context);
    }

    // 获取手机状态栏高度
    public static int getStatusBarHeight(Context context) {
        Class<?> c;
        Object obj;
        Field field;
        int x, statusBarHeight = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            statusBarHeight = context.getResources().getDimensionPixelSize(x);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return statusBarHeight;
    }

    /**
     * 检测网络状态是否连接
     *
     * @param context
     * @return
     */
    public static boolean isNetworkAavilable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager == null) {
            return false;
        }

        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    public static String getImei(Context context) {
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            String imei = telephonyManager.getDeviceId();
            if (imei.isEmpty())
                imei = null;
            return imei;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取手机的IMSI
     *
     * @param context
     * @return imsi
     * @author tonydeng
     */
    public static String getImsi(Context context) {
        TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String imsi = manager.getSubscriberId();
        if (TextUtils.isEmpty(imsi))
            imsi = null;
        return imsi;
    }

    /**
     * 获取设置的渠道号
     *
     * @param context
     * @return channel
     * @author tonydeng
     */
    public static Integer getChannel(Context context) {
        if (channel == null) {
            try {
                PackageManager pm = context.getPackageManager();
                ApplicationInfo ai = pm.getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
                Bundle bundle = ai.metaData;
                return bundle.getInt("com.leguangchang.channel");
            } catch (NameNotFoundException e) {
                channel = 1;
            } catch (Exception e) {
                channel = 1;
            }
        }
        return channel;
    }

    public static String getAppVersion(Context context) {
        if (null == appVersion || appVersion.trim().length() == 0) {
            PackageManager packageManager = context.getPackageManager();
            try {
                PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
                Integer cahnnel = getChannel(context);
                appVersion = getAppSiteId() + "." + packageInfo.versionName + "." + cahnnel;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return appVersion;
    }
    public static int getAppVersionCode(Context mContext){
        PackageManager mPackageManager =  mContext.getPackageManager();
        try {
            PackageInfo mInfo = mPackageManager.getPackageInfo(mContext.getPackageName(),0);
           return  mInfo.versionCode;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
            return Integer.MAX_VALUE;
        }
    }

    /**
     * 获取带平台信息的url
     *
     * @param context
     * @param url
     * @return
     */
    public static String getPlatformUrl(Context context, String url) {
        if (!url.contains("?")) {
            url += "?";
        }

        if (!url.contains("_c=")) {
            url += "&_c=" + Uri.encode(getAppVersion(context));
        }
        return url;
    }

    public static String getAppSiteId() {
        return "1";
    }

    public static String getAppSimpleVersion(Context context) {
        if (null == appSimpleVersion || appSimpleVersion.trim().length() == 0) {
            PackageManager packageManager = context.getPackageManager();
            try {
                PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
                appSimpleVersion = packageInfo.versionName;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return appSimpleVersion;
    }


    /**
     * 获得Android_id
     *
     * @param context
     * @return android_id
     * @author tonydeng
     */
    public static String getAndroidId(Context context) {
        return Secure.getString(context.getContentResolver(), Secure.ANDROID_ID);
    }

    /**
     * 修改图片
     *
     * @param bitmap
     * @param context
     * @return
     */
    @SuppressWarnings("deprecation")
    public static Drawable resizeImage(Bitmap bitmap, Context context) {
        Bitmap BitmapOrg = bitmap;
        int width = BitmapOrg.getWidth();
        int height = BitmapOrg.getHeight();
        int newWidth = getScreenWidth(context) - width;
        int newHeight = getScreenHeight(context) * 4 / 5 - height;

        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;

        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        // if you want to rotate the Bitmap
        matrix.postRotate(180);
        Bitmap resizedBitmap = Bitmap.createBitmap(BitmapOrg, 0, 0, width, height, matrix, true);
        return new BitmapDrawable(resizedBitmap);
    }

    /**
     * Get android versionName in AndroidManifest.xml
     *
     * @param context
     * @return
     */
    public static String getAppVersionName(Context context) {
        String appVersion;
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            appVersion = info.versionName;
        } catch (NameNotFoundException e) {
            appVersion = null;
        }
        return appVersion;
    }

    public static int getAppPlatform(Context context) {
        String versionName = getAppVersionName(context);
        int platform = -1;
        if (versionName != null) {
            int end = versionName.indexOf('.');
            if (end > -1) {
                platform = Integer.parseInt(versionName.substring(0, end));
            }
        }
        return platform;
    }

    // API都有只不过是将其进行综合并作为自己工程中的工具函数使用，很方便的。
    /**
     * 获取当前网络状态的类型 *
     *
     * @param mContext
     * @return 返回网络类型
     */
    public static final int NETWORK_TYPE_NONE = -0x1; // 断网情况
    public static final int NETWORK_TYPE_WIFI = 0x1; // WIFI模式
    public static final int NETWOKR_TYPE_MOBILE = 0x2; // GPRS模式

    public static int getCurrentNetType(Context mContext) {
        ConnectivityManager connManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI); // WIFI
        NetworkInfo gprs = connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE); // GPRS

        if (wifi != null && wifi.getState() == State.CONNECTED) {
            return NETWORK_TYPE_WIFI;
        } else if (gprs != null && gprs.getState() == State.CONNECTED) {
            return NETWOKR_TYPE_MOBILE;
        }
        return NETWORK_TYPE_NONE;
    }

    /**
     * 判断Android客户端网络是否连接
     * 只能判断是否有可用的连接，而不能判断是否能连网
     *
     * @param context
     * @return true/false
     */
    public static boolean checkNet(Context context) {
        try {
            ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivity != null) {
                NetworkInfo info = connectivity.getActiveNetworkInfo();
                if (info != null && info.isConnected()) {
                    if (info.getState() == State.CONNECTED) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    /**
     * 判断GPS是否打开
     *
     * @param context
     * @return
     */
    public static boolean isGpsEnabled(Context context) {
        LocationManager lm = ((LocationManager) context.getSystemService(Context.LOCATION_SERVICE));
        List<String> accessibleProviders = lm.getProviders(true);
        return accessibleProviders != null && accessibleProviders.size() > 0;
    }

    /**
     * 判断WIFI是否打开
     *
     * @param context
     * @return
     */
    public static boolean isWifiEnabled(Context context) {
        ConnectivityManager mgrConn = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        TelephonyManager mgrTel = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return ((mgrConn.getActiveNetworkInfo() != null && mgrConn
                .getActiveNetworkInfo().getState() == State.CONNECTED) || mgrTel
                .getNetworkType() == TelephonyManager.NETWORK_TYPE_UMTS);
    }

    /**
     * 判断是否是3G网络
     *
     * @param context
     * @return
     */
    public static boolean is3gNet(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkINfo = cm.getActiveNetworkInfo();
        return networkINfo != null && networkINfo.getType() == ConnectivityManager.TYPE_MOBILE;
    }

    /**
     * 判断是否是WIFI网络
     *
     * @param context
     * @return
     */
    public static boolean isWifiNet(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkINfo = cm.getActiveNetworkInfo();
        return networkINfo != null
                && networkINfo.getType() == ConnectivityManager.TYPE_WIFI;
    }

    /**
     * WIFI状态下获取IP地址
     *
     * @param mContext
     * @return
     */
    public static String getWIFILocalIpAdress(Context mContext) {
        //获取wifi服务
        WifiManager wifiManager = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);
        //判断wifi是否开启
        if (!wifiManager.isWifiEnabled()) {
            wifiManager.setWifiEnabled(true);
        }
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        int ipAddress = wifiInfo.getIpAddress();
        return formatIpAddress(ipAddress);
    }

    private static String formatIpAddress(int ipAdress) {
        return (ipAdress & 0xFF) + "." +
                ((ipAdress >> 8) & 0xFF) + "." +
                ((ipAdress >> 16) & 0xFF) + "." +
                (ipAdress >> 24 & 0xFF);
    }

    /**
     * 使用GPRS时，获取本机IP地址
     *
     * @return
     */
    public static String getGPRSLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        return inetAddress.getHostAddress();
                    }
                }
            }
        } catch (SocketException ex) {
//            DebugUtils.d(ex.getMessage());
        }
        return "";
    }


    /**
     * 获取网关IP地址
     *
     * @return
     */
    public static String getHostIp() {
        try {
            Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
            while (null != en && en.hasMoreElements()) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> ipAddr = intf.getInetAddresses(); ipAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = ipAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        return inetAddress.getHostAddress();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getTrackingDistinctId(Context context) {
        // String source = getAndroidId(context) + ":" + getImei(context) + ":" + DeviceUtil.getImsi(context) + ":" + ReaderApplication
        // .getToken();

        String source = getAndroidId(context) + ":" + getImei(context) + ":" + DeviceUtil.getImsi(context);
        return MD5Util.MD5Encode(source, "utf-8");
    }

//    public static boolean isMobile(String phonenum) {
//        if (phonenum.length() < 3){
//            return false;
//        }
//        if(TextUtils.isDigitsOnly(phonenum)) {
//            String prefix = phonenum.substring(0,2);
//
//        }
////        String frontTwoNums = phonenum.substring(0, 2);
////        String regExp = "^[1]([3][0-9]{1}|[5][0-9]{1}|88|89|70|85|82|86)[0-9]{8}$";
////         String regExp = ".*?(\\d).*?\\1{8,11}$";
////        String regExp = "^[1](3|4|5|7|8|9)$";
////        Pattern p = Pattern.compile(regExp);
////        Matcher m = p.matcher(phonenum);
////
////        return   m.find();
//        return false;
//    }

    public static boolean isPasswordRule(String password) {
        String regExp = "^[0-9A-Za-z]{6,16}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(password);
        return m.find();
    }

    public static boolean isHasSpecialWord(String password) {
//        String regExp = "[\\u4e00-\\u9fa5\\\\w\\\\d]*";
        String regExp = "^[\\u4e00-\\u9fa5_a-zA-Z0-9]+$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(password);
        return m.find();
    }

    public static boolean isRecurWord(String password) {
        String regExp = "^[1](([0|1|2|3|4|5|6|7|8|9|0]{9}[0-9]{1})|[0-9]{1}[0|1|2|3|4|5|6|7|8|9|0]{9})$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(password);
        return m.find();
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     *
     * @param pxValue
     *         （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue
     *         （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }


    public static int getVoiceCurrentPercent(Context context) {
        AudioManager mAudioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        float max = (float) mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        float current = (float) mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        float percent = current / max * 100;

        return (int) percent;
    }

    public static void setVolumeByPercent(Context context, int value) {
        AudioManager mAudioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        int max = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int current = Math.round((float) max * (float) value / 100);

        mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, current, 0);
    }

    /**
     * save clientid
     *
     * @param context
     *         context
     */
    public static void setClientId(Context context) {
    }

    public static boolean isRunningApp(Context context, String packageName) {
        boolean isAppRunning = false;
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(100);
        for (ActivityManager.RunningTaskInfo info : list) {
            if (info.topActivity.getPackageName().equals(packageName) && info.baseActivity.getPackageName().equals(packageName)) {
                isAppRunning = true;
                // find it, break
                break;
            }
        }
        return isAppRunning;
    }

    /**
     * 检测手机是否有内存卡
     *
     * @return boolean
     */
    public static boolean isExternalMediaMounted() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * 判断服务是否启动, 注意只要名称相同, 会检测任何服务.
     *
     * @param context
     *         上下文
     * @param serviceClass
     *         服务类
     * @return 是否启动服务
     */
    public static boolean isServiceRunning(Context context, Class<?> serviceClass) {
        if (context == null) {
            return false;
        }

        Context appContext = context.getApplicationContext();
        ActivityManager manager = (ActivityManager) appContext.getSystemService(Context.ACTIVITY_SERVICE);

        if (manager != null) {
            List<ActivityManager.RunningServiceInfo> infos = manager.getRunningServices(Integer.MAX_VALUE);
            if (infos != null && !infos.isEmpty()) {
                for (ActivityManager.RunningServiceInfo service : infos) {
                    // 添加Uid验证, 防止服务重名, 当前服务无法启动
                    if (getUid(context) == service.uid) {
                        if (serviceClass.getName().equals(service.service.getClassName())) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * 获取应用的Uid, 用于验证服务是否启动
     *
     * @param context
     *         上下文
     * @return uid
     */
    public static int getUid(Context context) {
        if (context == null) {
            return -1;
        }

        int pid = android.os.Process.myPid();
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);

        if (manager != null) {
            List<ActivityManager.RunningAppProcessInfo> infos = manager.getRunningAppProcesses();
            if (infos != null && !infos.isEmpty()) {
                for (ActivityManager.RunningAppProcessInfo processInfo : infos) {
                    if (processInfo.pid == pid) {
                        return processInfo.uid;
                    }
                }
            }
        }
        return -1;
    }

    // 获取通知的ID, 防止重复, 可以用于通知的ID
    public static class NotificationID {
        // 随机生成一个数
        private final static AtomicInteger c = new AtomicInteger(0);

        // 获取一个不重复的数, 从0开始
        public static int getID() {
            return c.incrementAndGet();
        }
    }

    /**
     * 检测应用是否运行
     *
     * @param packageName
     *         包名
     * @param context
     *         上下文
     * @return 是否存在
     */
    public static boolean isAppAlive(String packageName, Context context) {
        if (context == null || TextUtils.isEmpty(packageName)) {
            return false;
        }

        ActivityManager activityManager = (ActivityManager)
                context.getSystemService(Context.ACTIVITY_SERVICE);

        if (activityManager != null) {
            List<ActivityManager.RunningAppProcessInfo> procInfos = activityManager.getRunningAppProcesses();
            if (procInfos != null && !procInfos.isEmpty()) {
                for (int i = 0; i < procInfos.size(); i++) {
                    if (procInfos.get(i).processName.equals(packageName)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    /**
     * 获取进程名称
     *
     * @param context
     *         上下文
     * @return 进程名称
     */
    public static String getProcessName(Context context) {
        int pid = android.os.Process.myPid();
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> infos = manager.getRunningAppProcesses();
        if (infos != null) {
            for (ActivityManager.RunningAppProcessInfo processInfo : infos) {
                if (processInfo.pid == pid) {
                    return processInfo.processName;
                }
            }
        }
        return null;
    }

    /**
     * 检测计步传感器是否可以使用
     *
     * @param context
     *         上下文
     * @return 是否可用计步传感器
     */
    public static boolean hasStepSensor(Context context) {
        if (context == null) {
            return false;
        }

        Context appContext = context.getApplicationContext();
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return false;
        } else {
            boolean hasSensor = false;
            Sensor sensor = null;
            try {
                hasSensor = appContext.getPackageManager().hasSystemFeature("android.hardware.sensor.stepcounter");
                SensorManager sm = (SensorManager) appContext.getSystemService(Context.SENSOR_SERVICE);
                sensor = sm.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return hasSensor && sensor != null;
        }
    }

    // 获取通知的ID, 防止重复, 可以用于通知的ID
    public static int getNotificationID() {
        // 随机生成一个数
        AtomicInteger c = new AtomicInteger(0);

        return c.incrementAndGet();
    }

    /**
     * 检测屏幕是否开启
     *
     * @param context
     *         上下文
     * @return 是否屏幕开启
     */
    public static boolean isScreenOn(Context context) {
        Context appContext = context.getApplicationContext();
        PowerManager pm = (PowerManager) appContext.getSystemService(Context.POWER_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH) {
            return pm.isInteractive();
        } else {
            // noinspection all
            return pm.isScreenOn();
        }
    }
}
