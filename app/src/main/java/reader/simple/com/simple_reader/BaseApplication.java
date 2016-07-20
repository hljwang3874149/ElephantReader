package reader.simple.com.simple_reader;

import android.app.Application;
import android.content.Context;

import com.github.moduth.blockcanary.BlockCanary;
import com.squareup.leakcanary.LeakCanary;

import reader.simple.com.simple_reader.utils.PreferenceManager;


public class BaseApplication extends Application {

    public static String cacheDir = "";
    public static Context mAppContext = null;


    @Override
    public void onCreate() {
        super.onCreate();
        mAppContext = getApplicationContext();
        // 初始化 retrofit
//        CrashHandler.init(new CrashHandler(getApplicationContext()));

        /**
         * 如果存在SD卡则将缓存写入SD卡,否则写入手机内存
         */

        if (getApplicationContext().getExternalCacheDir() != null && ExistSDCard()) {
            cacheDir = getApplicationContext().getExternalCacheDir().getAbsolutePath();

        } else {
            cacheDir = getApplicationContext().getCacheDir().toString();
        }
        PreferenceManager.initPreferences(this);
        LeakCanary.install(this);
        BlockCanary.install(this, new AppBlockCanaryContext()).start();
    }


    private boolean ExistSDCard() {
        if (android.os.Environment.getExternalStorageState().equals(android.os.Environment
                .MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }
}
