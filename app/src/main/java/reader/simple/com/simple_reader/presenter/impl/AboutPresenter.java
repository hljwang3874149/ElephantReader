package reader.simple.com.simple_reader.presenter.impl;

import android.content.Context;

import reader.simple.com.simple_reader.common.Utils;
import reader.simple.com.simple_reader.interactor.AboutInteractor;
import reader.simple.com.simple_reader.presenter.Presenter;
import reader.simple.com.simple_reader.viewInterface.AboutView;

/**
 * ==================================================
 * 项目名称：Simple_Reader
 * 创建人：wangxiaolong
 * 创建时间：16/3/29 下午5:42
 * 修改时间：16/3/29 下午5:42
 * 修改备注：
 * Version：
 * ==================================================
 */
public class AboutPresenter implements Presenter {
    private AboutView mViewInterface;
    private Context context;
    private AboutInteractor mHelper;


    public AboutPresenter(Context context, AboutView mViewInterface) {
        this.context = context;
        this.mViewInterface = mViewInterface;
    }

    @Override
    public void initialized() {
        mHelper = new AboutInteractor();
        mViewInterface.setCameraDistance(mHelper.getCaneraDistance(context));

        mViewInterface.startAnimatorset();
        mViewInterface.setVersion(Utils.getVersion(context));
    }

    @Override
    public void onDestroy() {
        mHelper = null;
        context = null;
        mViewInterface = null;

    }

}
