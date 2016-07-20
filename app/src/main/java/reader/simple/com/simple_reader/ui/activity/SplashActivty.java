package reader.simple.com.simple_reader.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.text.format.DateUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.BounceInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.InjectView;
import reader.simple.com.simple_reader.R;
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
    @InjectView(R.id.fullscreen_content)
    TextView fullscreenContent;


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
        return R.layout.activity_splash_activty;
    }

    @Override
    protected void initViewsAndEvents() {
        Presenter splashPresenter = new SplashPresenter(this, this);
        splashPresenter.initialized();

        ViewCompat.animate(fullscreenContent)
                .scaleX(1.0f)
                .scaleY(1.0f)
                .translationY(-100)
                .alpha(1f)
                .setInterpolator(new BounceInterpolator())
                .setStartDelay(DateUtils.SECOND_IN_MILLIS)
                .setDuration(DateUtils.SECOND_IN_MILLIS * 2)
                .start();
    }

    @Override
    protected void doBeforeSetContentView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            Window window = getWindow();
//            window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
//                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            View mDecorView = getWindow().getDecorView();
//            mDecorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            window.setNavigationBarColor(Color.TRANSPARENT);

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
