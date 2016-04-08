package reader.simple.com.simple_reader.ui.activity.base;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


import butterknife.ButterKnife;
import reader.simple.com.simple_reader.R;

/**
 * 项目名称：Simple_Reader
 * 类描述：基类
 * 创建人：wangxiaolong
 * 创建时间：16/3/17 上午10:50
 * 修改人：wangxiaolong
 * 修改时间：16/3/17 上午10:50
 * 修改备注：
 */
public abstract class BaseActivity extends AppCompatActivity {
    Toolbar toolbar;

    public enum TransitionMode {
        LEFT, RIGHT, TOP, BOTTOM, SCALE, FADE, NONE
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        if (pendingTransition()) {
            switch (getTransitionMode()) {
                case LEFT:
                    overridePendingTransition(R.anim.left_in, R.anim.left_out);
                    break;
                case RIGHT:
                    overridePendingTransition(R.anim.right_in, R.anim.right_out);
                    break;
                case TOP:
                    overridePendingTransition(R.anim.top_in, R.anim.top_out);
                    break;
                case BOTTOM:
                    overridePendingTransition(R.anim.bottom_in, R.anim.bottom_out);
                    break;
                case SCALE:
                    overridePendingTransition(R.anim.scale_in, R.anim.scale_out);
                    break;
                case FADE:
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    break;
                default:
            }
        }
        super.onCreate(savedInstanceState);
        doBeforeSetContentView();
        setContentView(getContentViewLayoutID());
        doAfterSetContentView();
        initViewsAndEvents();
    }

    protected void doAfterSetContentView() {

    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.inject(this);
        initToolbar();
    }

    protected void initToolbar() {
        View v = findViewById(R.id.toolbar);
        if (null != v) {
            toolbar = (Toolbar) v;
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            setStatusbar();
        }

    }

    protected void setStatusbar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT
                && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            set19and20SdkStautsBar();
        }
    }

    protected void set19and20SdkStautsBar() {
        toolbar.setPadding(0, getStatusBarHeight(), 0, 0);
    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public void showToastMessage(String message) {
        if (!TextUtils.isEmpty(message))
            Toast.makeText(BaseActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    public void showSnackMessage(View view, String message) {
        if (!TextUtils.isEmpty(message))
            Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show();
    }

    public void showLoadingView(View view) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void finish() {
        super.finish();
        if (pendingTransition()) {
            switch (getTransitionMode()) {
                case LEFT:
                    overridePendingTransition(R.anim.left_in, R.anim.left_out);
                    break;
                case RIGHT:
                    overridePendingTransition(R.anim.right_in, R.anim.right_out);
                    break;
                case TOP:
                    overridePendingTransition(R.anim.top_in, R.anim.top_out);
                    break;
                case BOTTOM:
                    overridePendingTransition(R.anim.bottom_in, R.anim.bottom_out);
                    break;
                case SCALE:
                    overridePendingTransition(R.anim.scale_in, R.anim.scale_out);
                    break;
                case FADE:
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    break;
                default:
            }
        }
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
        ButterKnife.reset(this);
    }
    protected  void doOnDestroy(){

    }

    public void startActivityWithIntent(Intent intent) {
        startActivity(intent);
    }

    protected abstract boolean pendingTransition();

    protected abstract TransitionMode getTransitionMode();

    protected abstract int getContentViewLayoutID();

    protected abstract void initViewsAndEvents();

    protected  void doBeforeSetContentView(){

    };

}
