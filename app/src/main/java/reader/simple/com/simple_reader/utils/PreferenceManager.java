package reader.simple.com.simple_reader.utils;

import android.content.Context;
import android.content.SharedPreferences;

import reader.simple.com.simple_reader.common.Constants;

/**
 * ==================================================
 * 项目名称：Simple_Reader
 * 创建人：wangxiaolong
 * 创建时间：16/7/20 下午4:55
 * 备注：
 * Version：
 * ==================================================
 */
public class PreferenceManager {
    private SharedPreferences mPreferences;
    private static PreferenceManager instance = null;
    private int currentVersionCode = -1;

    private static final String KEY_RIDE_RESULT = "RIDE_RESULT";
    private static final String KEY_SPLASH_IMG = "IMG_SPLASH";
    private static final String KEY_SPLASH_CODE = "CODE_SPLASH";

    public static void initPreferences(Context context) {
        instance = new PreferenceManager(context);
    }

    private PreferenceManager(Context context) {
        mPreferences = context.getSharedPreferences(Constants.DEFAULT_PREFER, Context.MODE_PRIVATE);
    }

    public static PreferenceManager getInstance() {
        return instance;
    }

    public int getReceiveRideResult() {
        return mPreferences.getInt(KEY_RIDE_RESULT, 0);
    }

    public void setReceiveRideResult(int value) {
        mPreferences.edit().putInt(KEY_RIDE_RESULT, value).apply();
    }

    public void setVersionCode(int code) {
        if (currentVersionCode != code) {
            currentVersionCode = code;
            setReceiveRideResult(0);
        }
    }

    public String getSplashImgPath() {
        return mPreferences.getString(KEY_SPLASH_IMG, "");
    }


    public void putSplashImgPath(String mPath) {
        mPreferences.edit().putString(KEY_SPLASH_IMG, mPath).apply();
    }


    public int getSplashImgCode() {
        return mPreferences.getInt(KEY_SPLASH_CODE, 0);
    }

    public void putSplashImgCode(int code) {
        mPreferences.edit().putInt(KEY_SPLASH_CODE, code).apply();
    }
}
