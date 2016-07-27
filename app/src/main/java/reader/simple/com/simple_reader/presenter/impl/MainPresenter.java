package reader.simple.com.simple_reader.presenter.impl;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatDelegate;
import android.text.format.DateUtils;

import reader.simple.com.simple_reader.common.ACache;
import reader.simple.com.simple_reader.common.netWork.RetrofitNetWork;
import reader.simple.com.simple_reader.domain.PageInfo;
import reader.simple.com.simple_reader.presenter.Presenter;
import reader.simple.com.simple_reader.ui.service.LockScreenService;
import reader.simple.com.simple_reader.viewInterface.MainView;
import rx.subscriptions.CompositeSubscription;

/**
 * ==================================================
 * 项目名称：Simple_Reader
 * 创建人：wangxiaolong
 * 创建时间：16/4/2 下午4:46
 * 修改时间：16/4/26 下午6:46
 * 修改备注：
 * Version：
 * ==================================================
 */
public class MainPresenter implements Presenter {
    private final int DEFAULT_ROW = 0;
    private Context mContext;
    private MainView mView;
    private CompositeSubscription subscriptions;
    private static final String KEY_PAGEINFOS = "articles";
    private ACache mACahe;

    public MainPresenter(Context mContext, MainView mView) {
        this.mContext = mContext;
        this.mView = mView;
        subscriptions = new CompositeSubscription();
        mACahe = ACache.get(mContext);
    }

    @Override
    public void initialized() {
        PageInfo pageInfo = (PageInfo) mACahe.getAsObject(KEY_PAGEINFOS);
        if (null != pageInfo) {
            mView.initCacheData(pageInfo);
        } else {
            mView.showRefreshLoading();
            getArticleInfos(DEFAULT_ROW);
        }
        startService();

    }

    private void startService() {
        mContext.startService(new Intent(mContext, LockScreenService.class));
    }

    @Override
    public void onDestroy() {
        subscriptions.unsubscribe();
        mContext.stopService(new Intent(mContext, LockScreenService.class));
    }

    public void getArticleInfos(int pageNum) {
        subscriptions.clear();
        subscriptions.add(
                RetrofitNetWork.getInstance().getPageInfos(20, pageNum)
                        .subscribe(pageInfo -> {
                                    if (pageNum == 0) {
                                        mView.clearListData();
                                        //仅存储最新20条，有效时间2小时
                                        mACahe.put(KEY_PAGEINFOS, pageInfo, (int) (DateUtils.HOUR_IN_MILLIS * 2 / 1000));
                                    }
                                    mView.setLoadMore(pageInfo);
                                }
                                , mThrow -> mView.doOnThrow(mThrow)
                                , () -> mView.hideRefresh())
        );

    }

    public void loadMoreArticles(String createTime, String updateTime) {
        subscriptions.clear();
        subscriptions.add(
                RetrofitNetWork.getInstance().loadMoreArticle(20, createTime, updateTime)
                        .subscribe(pageInfo ->
                                        mView.setLoadMore(pageInfo)
                                , mThrow -> mView.doOnThrow(mThrow)
                                , () -> mView.hideRefresh()
                        )
        );
    }

    public void setDayAndNightTheme() {
        switch (AppCompatDelegate
                .getDefaultNightMode()) {
            case AppCompatDelegate
                    .MODE_NIGHT_NO:
                AppCompatDelegate
                        .setDefaultNightMode
                                (AppCompatDelegate.MODE_NIGHT_YES);
                break;
            case AppCompatDelegate
                    .MODE_NIGHT_YES:
                AppCompatDelegate
                        .setDefaultNightMode
                                (AppCompatDelegate.MODE_NIGHT_NO);
                break;
            default:
                AppCompatDelegate
                        .setDefaultNightMode
                                (AppCompatDelegate.MODE_NIGHT_YES);
        }
    }
}
