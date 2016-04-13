package reader.simple.com.simple_reader.ui.activity;

import reader.simple.com.simple_reader.R;
import reader.simple.com.simple_reader.ui.activity.base.BaseActivity;

public class TestDrawActivity extends BaseActivity {

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
        return R.layout.activity_test_draw;
    }

    @Override
    protected void initViewsAndEvents() {

    }
}
