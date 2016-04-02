package reader.simple.com.simple_reader.presenter.impl;

import android.content.Context;

import reader.simple.com.simple_reader.presenter.Presenter;
import reader.simple.com.simple_reader.viewInterface.MainView;

/**
 * ==================================================
 * 项目名称：Simple_Reader
 * 创建人：wangxiaolong
 * 创建时间：16/4/2 下午4:46
 * 修改时间：16/4/2 下午4:46
 * 修改备注：
 * Version：
 * ==================================================
 */
public class MainPresenter implements Presenter {
    private Context mContext;
    private MainView mView;

    public MainPresenter(Context mContext, MainView mView) {
        this.mContext = mContext;
        this.mView = mView;
    }

    @Override
    public void initialized() {
        mView.initSwipeFreshView();
        mView.initRecyclerView();

    }
}
