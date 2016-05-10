package reader.simple.com.simple_reader.ui.activity;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.graphics.Color;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import butterknife.InjectView;
import reader.simple.com.simple_reader.R;
import reader.simple.com.simple_reader.presenter.impl.AboutPresenter;
import reader.simple.com.simple_reader.ui.activity.base.BaseActivity;
import reader.simple.com.simple_reader.viewInterface.AboutView;

public class AboutActivity extends BaseActivity implements AboutView {


    @InjectView(R.id.about_welcome)
    TextView aboutIn;
    @InjectView(R.id.about_version)
    TextView aboutVersion;
    @InjectView(R.id.text_switcher)
    TextSwitcher mTextSwitcher;

    private AnimatorSet mRightOut;
    private AnimatorSet mLeftIn;
    private AboutPresenter mPresenter;
    private int index;

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

        mTextSwitcher.setFactory(new ViewSwitcher.ViewFactory(){

            @Override
            public View makeView() {
                TextView mTextView = new TextView(AboutActivity.this);
                mTextView.setTextColor(Color.DKGRAY);
                mTextView.setTextSize(16);
                return mTextView;
            }
        });
        mTextSwitcher.setInAnimation(AnimationUtils.loadAnimation(this , R.anim.bottom_in));
        mTextSwitcher.setOutAnimation(AnimationUtils.loadAnimation(this , R.anim.top_out));
        mTextSwitcher.setOnClickListener(v -> {
            index++;
            mTextSwitcher.setText("测试 textSwitcher" + index);
        });
        mTextSwitcher.setText("测试 textSwitcher");

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
