package reader.simple.com.simple_reader.presenter.impl;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v4.view.ViewPropertyAnimatorListenerAdapter;
import android.support.v4.view.ViewPropertyAnimatorUpdateListener;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import reader.simple.com.simple_reader.common.ArticleUtil;
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
    private Context mContext;
    private WebTextView mView;
    private String artcleID;

    public WebTextPresenter(Context mContext, WebTextView mView, String artcleID) {
        this.mContext = mContext;
        this.mView = mView;
        this.artcleID = artcleID;
    }

    @Override
    public void initialized() {
        mView.showLoadingView();
        RetrofitNetWork.getInstance().getArticleDescInfo(artcleID)
                .subscribe(articleDescInfo -> {
                    mView.getArticleInfo(ArticleUtil.formatBody(articleDescInfo.articleBody
                            .articleInfo));
                }, throwable -> {
                    mView.showThrowMessage(throwable.getMessage());

                }, () -> {
                    mView.hideLoadingView();
                });


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

    public void hideAnimator(FloatingActionButton fab , ViewPropertyAnimatorListenerAdapter adapter) {
        ViewCompat.animate(fab).scaleX(0f).scaleY(0f).setDuration(200)
                .alpha(0f).setInterpolator(new DecelerateInterpolator(1.2f))
                .setListener(adapter)
                .start();

    }
}
