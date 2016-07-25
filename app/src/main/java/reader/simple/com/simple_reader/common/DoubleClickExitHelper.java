package reader.simple.com.simple_reader.common;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.view.KeyEvent;
import android.widget.Toast;

import com.umeng.analytics.MobclickAgent;

import reader.simple.com.simple_reader.R;


/***
 * 双击退出
 */
public class DoubleClickExitHelper {

    private final Activity mActivity;

    private boolean isOnKeyBacking;
    private Handler mHandler;
    private Toast mBackToast;

    public DoubleClickExitHelper(Activity activity) {
        mActivity = activity;
        mHandler = new Handler(Looper.getMainLooper());
    }

    /**
     * Activity onKeyDown事件
     */
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode != KeyEvent.KEYCODE_BACK) {
            return false;
        }
        if (isOnKeyBacking) {
            mHandler.removeCallbacks(onBackTimeRunnable);
            if (mBackToast != null) {
                mBackToast.cancel();
            }
            // 退出
            return true;
        } else {
            isOnKeyBacking = true;
            if (mBackToast == null) {
                mBackToast = Toast.makeText(mActivity, R.string.exit_double, Toast.LENGTH_LONG);
            }
            mBackToast.show();
            mHandler.postDelayed(onBackTimeRunnable, 2000);
            return true;
        }
    }

    /**
     * onbackpress
     */
    public void onBackPress() {
        if (isOnKeyBacking) {
            mHandler.removeCallbacks(onBackTimeRunnable);
            if (mBackToast != null) {
                mBackToast.cancel();
            }
            exitApp();
        } else {
            isOnKeyBacking = true;
                /*if(mBackToast == null) {
					mBackToast = Toast.makeText(mActivity, R.string.exit_double_click, Toast.LENGTH_LONG);
				}
				mBackToast.show();*/
            showCustomToast();
            mHandler.postDelayed(onBackTimeRunnable, 2000);
        }

    }

    public void exitApp() {
        MobclickAgent.onKillProcess(mActivity);
        mActivity.finish();
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }

    private Runnable onBackTimeRunnable = new Runnable() {

        @Override
        public void run() {
            isOnKeyBacking = false;
            if (mBackToast != null) {
                mBackToast.cancel();
            }
        }
    };


    public void showCustomToast() {
        Toast.makeText(mActivity , R.string.exit_double , Toast.LENGTH_LONG).show();
    }
}
