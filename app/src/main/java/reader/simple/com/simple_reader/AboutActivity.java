package reader.simple.com.simple_reader;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.graphics.Color;
import android.support.v4.view.ViewCompat;
import android.text.format.DateUtils;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.TextView;

import butterknife.InjectView;
import reader.simple.com.simple_reader.presenter.impl.AboutPresenter;
import reader.simple.com.simple_reader.viewInterface.AboutView;

public class AboutActivity extends BaseActivity implements AboutView {


    @InjectView(R.id.about_welcome)
    TextView aboutIn;
    @InjectView(R.id.about_version)
    TextView aboutVersion;

    private AnimatorSet mRightOut;
    private AnimatorSet mLeftIn;
    private AboutPresenter mPresenter;

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
        return R.layout.activity_about;
    }

    @Override
    protected void initViewsAndEvents() {
        mLeftIn = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator
                .anim_rotation_y_in);
        mRightOut = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator
                .anim_rotation_y_out);
        mPresenter = new AboutPresenter(this, this);
        mPresenter.initialized();
        mRightOut.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                //// TODO:  nothing

            }
        });

    }

    @Override
    public void startAnimatorset() {
        mLeftIn.setTarget(aboutVersion);
        mRightOut.setTarget(aboutIn);
        mRightOut.start();
        mLeftIn.start();
    }


    @Override
    public void setVersion(String version) {
        aboutVersion.setTextColor(Color.WHITE);
        aboutVersion.setText(String.format(getString(R.string.current_version), version));
    }

    @Override
    public void setCameraDistance(float value) {
        aboutIn.setCameraDistance(value);
        aboutVersion.setCameraDistance(value);
    }

}
