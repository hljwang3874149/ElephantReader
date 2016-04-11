package reader.simple.com.simple_reader.presenter.impl;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v4.view.ViewPropertyAnimatorListenerAdapter;
import android.support.v4.view.ViewPropertyAnimatorUpdateListener;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import reader.simple.com.simple_reader.common.ACache;
import reader.simple.com.simple_reader.common.ArticleUtil;
import reader.simple.com.simple_reader.common.DebugUtil;
import reader.simple.com.simple_reader.common.netWork.RetrofitNetWork;
import reader.simple.com.simple_reader.presenter.Presenter;
import reader.simple.com.simple_reader.viewInterface.WebTextView;

/**
 * ==================================================
 * 项目名称：Simple_Reader
 * 创建人：wangxiaolong
 * 创建时间：16/4/5 下午6:40
 * 修改时间：16/4/5 下午6:40
 * 修改备注：
 * Version：
 * ==================================================
 */
public class WebTextPresenter implements Presenter {
    private WebTextView mView;
    private String artcleID;
    private ACache mAcache;
    private String mPath;

    public WebTextPresenter(Context mContext, WebTextView mView, String artcleID) {
        this.mView = mView;
        this.artcleID = artcleID;
        mAcache = ACache.get(mContext);
    }

    @Override
    public void initialized() {
        mView.showLoadingView();
        if (TextUtils.isEmpty(mAcache.getAsString(artcleID))) {
            RetrofitNetWork.getInstance().getArticleDescInfo(artcleID)
                    .subscribe(articleDescInfo -> {
                        String artcle = ArticleUtil.formatBody(articleDescInfo.articleBody
                                .articleInfo);
                        mView.getArticleInfo(artcle);
                        mPath = articleDescInfo.articleBody.articleInfo.wechatUrl;
                        mAcache.put(artcleID, artcle, (int) (DateUtils.HOUR_IN_MILLIS * 2 / 1000)); //缓存 文章数据2小时
                        mAcache.put("Uri_" + artcleID, mPath, (int) (DateUtils.HOUR_IN_MILLIS * 2 / 1000)); //缓存 文章数据2小时
                    }, throwable -> {
                        mView.showThrowMessage(throwable.getMessage());

                    }, () -> {
                        mView.hideLoadingView();
                    });
        } else {
            mPath = mAcache.getAsString("Uri_" + artcleID);
            mView.getArticleInfo(mAcache.getAsString(artcleID));
            mView.hideLoadingView();

        }
    }

    public void startAnimator(FloatingActionButton fab, ViewPropertyAnimatorListenerAdapter
            adapter) {
        ViewCompat.animate(fab).scaleX(1f).scaleY(1f)
                .setDuration(200)
                .alpha(1f)
                .setInterpolator(new DecelerateInterpolator(1.2f))
                .setListener(adapter)
                .start();

    }

    public void hideAnimator(FloatingActionButton fab, ViewPropertyAnimatorListenerAdapter adapter) {
        ViewCompat.animate(fab).scaleX(0f).scaleY(0f).setDuration(200)
                .alpha(0f).setInterpolator(new DecelerateInterpolator(1.2f))
                .setListener(adapter)
                .start();
    }

    public void clear() {
        artcleID = null;
        mAcache = null;
        mView = null;
    }

    public String getPath() {
        return this.mPath;
    }
}
