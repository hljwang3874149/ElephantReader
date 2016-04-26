package reader.simple.com.simple_reader.presenter.impl;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;

import reader.simple.com.simple_reader.common.DebugUtil;
import reader.simple.com.simple_reader.common.DeviceUtil;
import reader.simple.com.simple_reader.interactor.SplashInteractor;
import reader.simple.com.simple_reader.presenter.Presenter;
import reader.simple.com.simple_reader.viewInterface.SplashView;

/**
 * ==================================================
 * 项目名称：Simple_Reader
 * 创建人：wangxiaolong
 * 创建时间：16/3/17 下午5:56
 * 修改时间：16/3/17 下午5:56
 * 修改备注：
 * Version：
 * ==================================================
 */
public class SplashPresenter implements Presenter {
    private Context ctx;
    private SplashView splashView;
    private Handler mainHandler = new Handler(Looper.getMainLooper());
    private SplashInteractor interactor;
    private Bitmap bitmap;

    public SplashPresenter(Context ctx, SplashView splashView) {
        this.ctx = ctx;
        this.splashView = splashView;
        interactor = new SplashInteractor();
    }

    @Override
    public void initialized() {
        DebugUtil.e("initialized");
        bitmap = interactor.getSplashBitmap(ctx);
        if (null != bitmap) {
            splashView.getBgView().setImageBitmap(bitmap);
            setAnimation(splashView.getBgView());

        } else {
            mainHandler.postDelayed(() -> {
                        splashView.navigateHome();
                    }
                    , 1000);
        }
    }

    @Override
    public void onDestroy() {
        ctx = null;
        splashView = null;
    }

    private void setAnimation(ImageView bgView) {
        ObjectAnimator animatorRight = ObjectAnimator.ofFloat(bgView, "translationX", 0, DeviceUtil
                .getScreenWidth(ctx) - DeviceUtil.dip2px(ctx, 1000));
        animatorRight.setDuration(2000)
                .setRepeatCount(1);
        animatorRight.setRepeatMode(ObjectAnimator.REVERSE);
        animatorRight.start();
        animatorRight.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                splashView.navigateHome();
                assert bitmap != null;
                bitmap.recycle();
            }
        });
    }
}
