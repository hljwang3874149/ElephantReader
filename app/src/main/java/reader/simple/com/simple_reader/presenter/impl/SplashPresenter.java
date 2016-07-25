package reader.simple.com.simple_reader.presenter.impl;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.orhanobut.logger.Logger;
<<<<<<< HEAD
import com.umeng.analytics.AnalyticsConfig;
import com.umeng.analytics.MobclickAgent;
=======
>>>>>>> 25c33cdc705fb6ff5f267893f19e06e6a3563eb8

import reader.simple.com.simple_reader.common.DebugUtil;
import reader.simple.com.simple_reader.common.DeviceUtil;
import reader.simple.com.simple_reader.common.netWork.RetrofitNetWork;
import reader.simple.com.simple_reader.interactor.SplashInteractor;
import reader.simple.com.simple_reader.presenter.Presenter;
import reader.simple.com.simple_reader.utils.PreferenceManager;
import reader.simple.com.simple_reader.viewInterface.SplashView;
import rx.subscriptions.CompositeSubscription;

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
    private CompositeSubscription mSubscription;

    public SplashPresenter(Context ctx, SplashView splashView) {
        this.ctx = ctx;
        this.splashView = splashView;
        interactor = new SplashInteractor();
    }

    @Override
    public void initialized() {
        DebugUtil.e("initialized");
        mSubscription = new CompositeSubscription();
        String mPath = PreferenceManager.getInstance().getSplashImgPath();
        if (TextUtils.isEmpty(mPath)) {
            useDefaultImg();
        } else {
            Glide.with(ctx).load(mPath).crossFade().into(splashView.getBgView());
            setAnimation(splashView.getBgView());
        }

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            AnalyticsConfig.sEncrypt = true;
        } else
            MobclickAgent.enableEncrypt(true);

        requestNewSplashImg();

    }

    private void requestNewSplashImg() {
        if (DeviceUtil.isWifiNet(ctx)) {
            mSubscription.add(RetrofitNetWork.getInstance().loadSplashImg()
                    .filter(splashInfo -> splashInfo.versionCode > PreferenceManager.getInstance().getSplashImgCode())
                    .subscribe(splashInfo -> {
                        Logger.e(splashInfo.toString());
                        PreferenceManager.getInstance().putSplashImgCode(splashInfo.versionCode);
                        PreferenceManager.getInstance().putSplashImgPath(splashInfo.splashPath);
                        Glide.with(ctx.getApplicationContext())
                                .load(splashInfo.splashPath)
                                .downloadOnly(DeviceUtil.getScreenHeight(ctx)
                                        , DeviceUtil.dip2px(ctx, 1100));

                    }, throwable -> {
                        Logger.e(throwable.getMessage());
                    }));
        }

    }

    public void useDefaultImg() {
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
        mSubscription.unsubscribe();
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
                if (null != bitmap && !bitmap.isRecycled()) bitmap.recycle();
            }
        });
    }
}
