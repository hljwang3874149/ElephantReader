package reader.simple.com.simple_reader.ui.activity;

import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import butterknife.InjectView;
import me.imid.swipebacklayout.lib.SwipeBackLayout;
import reader.simple.com.simple_reader.R;
import reader.simple.com.simple_reader.ui.activity.base.BaseSwipeActivity;
import reader.simple.com.simple_reader.utils.PreferenceManager;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class LockScreenActivity extends BaseSwipeActivity {

    @InjectView(R.id.fullscreen_content)
    ImageView fullscreenContent;

    @Override
    protected void doBeforeSetContentView() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        super.doBeforeSetContentView();
    }


    @Override
    protected boolean pendingTransition() {
        return false;
    }

    @Override
    protected TransitionMode getTransitionMode() {
        return null;
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_lockscreen;
    }

    @Override
    protected void initViewsAndEvents() {
        initView();
    }

    @Override
    protected int getSwipeBackLayoutTracking() {
        return SwipeBackLayout.EDGE_LEFT ;
    }

    @Override
    protected boolean getSwipeBackLayoutEnabled() {
        return true;
    }

    private void initView() {
        String path = PreferenceManager.getInstance().getSplashImgPath();
        if (!TextUtils.isEmpty(path)) {
            Glide.with(this).load(PreferenceManager.getInstance().getSplashImgPath()).into(fullscreenContent);
        } else {
            Glide.with(this).load("file:///android_asset/splash/splash_background.jpg").into(fullscreenContent);
        }
        show();
    }

    private void show() {
        fullscreenContent.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        fullscreenContent.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        int action = event.getKeyCode();
        switch (action) {
            case KeyEvent.KEYCODE_BACK:
                return true;
            case KeyEvent.KEYCODE_MENU:
                return true;
            default:
                return super.onKeyDown(keyCode, event);
        }
    }
}
