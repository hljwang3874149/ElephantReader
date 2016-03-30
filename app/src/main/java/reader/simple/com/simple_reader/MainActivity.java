package reader.simple.com.simple_reader;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.animation.FastOutLinearInInterpolator;
import android.support.v7.widget.Toolbar;
import android.widget.Button;

import butterknife.InjectView;

public class MainActivity extends BaseActivity {

    @InjectView(R.id.fab)
    FloatingActionButton fab;
    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.button)
    Button button;

    @Override
    protected boolean pendingTransition() {
        return false;
    }

    @Override
    protected TransitionMode getTransitionMode() {
        return TransitionMode.FADE;
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViewsAndEvents() {

        fab.setOnClickListener(view -> {
            button.animate()
                    .alpha(0f)
                    .scaleX(0f)
                    .scaleY(0f)
                    .setDuration(500)
                    .setInterpolator(new FastOutLinearInInterpolator())
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            button.animate()
                                    .alpha(1f)
                                    .scaleY(1f)
                                    .scaleX(1f)
                                    .setDuration(500)
                                    .start();
                        }
                    }).start();

                }
        );
    }

}
