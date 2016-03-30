package reader.simple.com.simple_reader.ui.activity;

import android.content.Intent;
import android.os.Build;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;


import butterknife.InjectView;
import reader.simple.com.simple_reader.R;
import reader.simple.com.simple_reader.common.DebugUtil;
import reader.simple.com.simple_reader.presenter.Presenter;
import reader.simple.com.simple_reader.presenter.impl.SplashPresenter;
import reader.simple.com.simple_reader.ui.activity.base.BaseActivity;
import reader.simple.com.simple_reader.viewInterface.SplashView;


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class SplashActivty extends BaseActivity implements SplashView {

    @InjectView(R.id.splash_image)
    ImageView splashImage;

    @Override
    protected boolean pendingTransition() {
        return false;
    }

    @Override
    protected TransitionMode getTransitionMode() {
        return TransitionMode.NONE;
    }

    @Override
    protected int getContentViewLayoutID() {
        DebugUtil.e("getContentViewLayoutID");
        return R.layout.activity_splash_activty;
    }

    @Override
    protected void initViewsAndEvents() {
        DebugUtil.e("initViewsAndEvents");
        Presenter splashPresenter = new SplashPresenter(this, this);
        splashPresenter.initialized();
    }

    @Override
    protected void doBeforeSetContentView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

    }

    @Override
    public void navigateHome() {
        startActivityWithIntent(new Intent(this, MainActivity.class));
        finish();

    }

    @Override
    public ImageView getBgView() {
        return splashImage;
    }
}