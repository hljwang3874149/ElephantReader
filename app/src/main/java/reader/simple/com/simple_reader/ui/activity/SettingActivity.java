package reader.simple.com.simple_reader.ui.activity;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;

import butterknife.InjectView;
import reader.simple.com.simple_reader.R;
import reader.simple.com.simple_reader.ui.activity.base.BaseActivity;

public class SettingActivity extends BaseActivity {


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
        return R.layout.activity_setting;
    }

    @Override
    protected void initViewsAndEvents() {
    }

}
