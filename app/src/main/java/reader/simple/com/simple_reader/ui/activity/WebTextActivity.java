package reader.simple.com.simple_reader.ui.activity;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.view
        .ViewPropertyAnimatorListenerAdapter;
import android.support.v4.widget.NestedScrollView;
import android.view.View;

import butterknife.InjectView;
import reader.simple.com.simple_reader.R;
import reader.simple.com.simple_reader.common.DeviceUtil;
import reader.simple.com.simple_reader.presenter.impl
        .WebTextPresenter;
import reader.simple.com.simple_reader.ui.activity.base
        .BaseActivity;
import reader.simple.com.simple_reader.ui.webView
        .ArticleWebView;
import reader.simple.com.simple_reader.ui.webView
        .HtmlWebClient;
import reader.simple.com.simple_reader.viewInterface
        .WebTextView;

public class WebTextActivity extends BaseActivity
        implements WebTextView {
    @InjectView(R.id.webView)
    ArticleWebView webView;
    @InjectView(R.id.scrollView)
    NestedScrollView scrollView;
    @InjectView(R.id.fab)
    FloatingActionButton fab;
    private WebTextPresenter mPresenter;
    private int mScreenHight;

    private boolean isShowFab;

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
        return R.layout.activity_html_text;
    }

    @Override
    protected void initViewsAndEvents() {
        mScreenHight = DeviceUtil.getScreenHeight(this);
        mPresenter = new WebTextPresenter(this, this,
                getIntent().getStringExtra("id"));
        mPresenter.initialized();
        scrollView.setOnScrollChangeListener(
                (NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
                    if (!isShowFab && scrollY > mScreenHight) {
                        mPresenter.startAnimator(fab, new
                                ViewPropertyAnimatorListenerAdapter() {
                                    @Override
                                    public void onAnimationEnd(View view) {
                                        isShowFab = true;
                                    }

                                });
                    } else if (isShowFab && scrollY < mScreenHight) {
                        mPresenter.hideAnimator(fab, new ViewPropertyAnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd
                                    (View view) {
                                isShowFab = false;
                            }

                        });
                    }

                });

        webView.setWebViewClient(new HtmlWebClient(this));
        fab.setOnClickListener(v -> {
            scrollView.smoothScrollTo(0, 10);
        });

    }


    @Override
    public void getArticleInfo(String info) {
        webView.loadDataWithBaseURL("", info,
                "text/html", "utf-8", null);

    }

    @Override
    public void showThrowMessage(String msg) {
        showToastMessage(msg);
    }

    @Override
    public void hideLoadingView() {

    }

    @Override
    public void showLoadingView() {

    }
}
