package reader.simple.com.simple_reader.ui.activity.base;

import android.os.Bundle;
import android.view.View;

import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.Utils;
import me.imid.swipebacklayout.lib.app.SwipeBackActivityBase;
import me.imid.swipebacklayout.lib.app.SwipeBackActivityHelper;

/**
 * ==================================================
 * 项目名称：Simple_Reader
 * 创建人：wangxiaolong
 * 创建时间：16/4/8 下午5:22
 * 修改时间：16/4/8 下午5:22
 * 修改备注：
 * Version：
 * ==================================================
 */
public abstract class BaseSwipeActivity extends BaseActivity implements SwipeBackActivityBase {
    private SwipeBackActivityHelper mHelper;
    private SwipeBackLayout mSwipeBackLayout;

    @Override
    protected void doBeforeSetContentView() {
        mHelper = new SwipeBackActivityHelper(this);
        mHelper.onActivityCreate();
    }

    @Override
    protected void doAfterSetContentView() {
        mSwipeBackLayout = getSwipeBackLayout();
        mSwipeBackLayout.setEnableGesture(getSwipeBackLayoutEnabled());
        if (!getSwipeBackLayoutEnabled()) {
            mSwipeBackLayout.setEdgeTrackingEnabled(getSwipeBackLayoutTracking());
        }
    }

    protected abstract int getSwipeBackLayoutTracking();

    protected abstract boolean getSwipeBackLayoutEnabled();

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mHelper.onPostCreate();
    }

    @Override
    public View findViewById(int id) {
        View v = super.findViewById(id);
        if (v == null && mHelper != null)
            return mHelper.findViewById(id);
        return v;
    }

    @Override
    public SwipeBackLayout getSwipeBackLayout() {
        return mHelper.getSwipeBackLayout();
    }

    @Override
    public void setSwipeBackEnable(boolean enable) {
        getSwipeBackLayout().setEnableGesture(enable);
    }

    @Override
    public void scrollToFinishActivity() {
        Utils.convertActivityToTranslucent(this);
        getSwipeBackLayout().scrollToFinishActivity();
    }
}
