package reader.simple.com.simple_reader.ui.activity;

import android.os.Build;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListenerAdapter;
import android.support.v4.widget.NestedScrollView;
import android.text.format.DateUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;

import butterknife.InjectView;
import me.imid.swipebacklayout.lib.SwipeBackLayout;
import reader.simple.com.simple_reader.R;
import reader.simple.com.simple_reader.common.Constants;
import reader.simple.com.simple_reader.common.DeviceUtil;
import reader.simple.com.simple_reader.common.Utils;
import reader.simple.com.simple_reader.presenter.impl.WebTextPresenter;
import reader.simple.com.simple_reader.ui.activity.base.BaseSwipeActivity;
import reader.simple.com.simple_reader.ui.webView.ArticleWebView;
import reader.simple.com.simple_reader.viewInterface.WebTextView;

public class WebTextActivity extends BaseSwipeActivity
        implements WebTextView {
    @InjectView(R.id.webView)
    ArticleWebView webView;
    @InjectView(R.id.scrollView)
    NestedScrollView scrollView;
    @InjectView(R.id.fab)
    FloatingActionButton fab;
    @InjectView(R.id.headerImage)
    ImageView mImageView;
    @InjectView(R.id.html_text_rootview)
    View mRootView;
    @InjectView(R.id.loadView)
    ProgressBar loadView;
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
    protected boolean isHaveFinishAnim() {
        return false;
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_html_text;
    }

    @Override
    protected void initViewsAndEvents() {
        mScreenHight = DeviceUtil.getScreenHeight(this);
        mPresenter = new WebTextPresenter(this, this,
                getIntent().getStringExtra(Constants.KEY_ARCITLE));
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

        webView.setWebViewClient(new WebViewClient());
        webView.setWebChromeClient(new WebChromeClient() {

            public void onProgressChanged(WebView webView, int newProgress) {
                if (100 == newProgress) {
                    new Handler().postDelayed(() -> {
                        webView.setVisibility(View.VISIBLE);
                        hideLoadingView();
                    }, DateUtils.SECOND_IN_MILLIS);

                }
            }
        });
        mImageView.setVisibility(View.VISIBLE);
        ViewCompat.setTransitionName(mImageView, getIntent().getStringExtra(Constants.KEY_ARCITLE));
        initRootView();

        Glide.with(this)
                .load(getIntent().getStringExtra(Constants.KEY_IMAG_PATH))
                .crossFade()
                .into(mImageView);
        fab.setOnClickListener(v -> {
            scrollView.smoothScrollTo(0, 10);
        });

        ViewGroup.LayoutParams mParams = webView.getLayoutParams();
        mParams.width = DeviceUtil.getScreenWidth(this);
        mParams.height = DeviceUtil.getScreenHeight(this);

    }

    private void initRootView() {
        mRootView.setAlpha(1f);
    }

    @Override
    protected boolean isNeedOtherDoSetToolsbar() {
        return true;
    }

    @Override
    public void getArticleInfo(String info) {
        webView.loadDataWithBaseURL("", info, "text/html", "utf-8", null);

    }

    @Override
    public void showThrowMessage(String msg) {

        showToastMessage(msg);
    }

    @Override
    protected void doOnDestroy() {
        if (null != mPresenter) {
            mPresenter.onDestroy();
            mPresenter = null;
        }
        if (webView != null)
            webView.destroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_webtext_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.chrome_open:
                Utils.startChrome(this, mPresenter.getPath());
                return true;
            case R.id.share_article:
                Utils.shareArticleUrl(this, mPresenter.getPath());
                return true;
            case R.id.home:
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH) {
                    finishAfterTransition();
                } else {
                    finish();
                }
                return true;
            default:
                onBackPressed();
                return true;
        }

    }

    @Override
    public void hideLoadingView() {
        loadView.setVisibility(View.GONE);
    }

    @Override
    public void showLoadingView() {
        loadView.setVisibility(View.VISIBLE);
    }

    @Override
    protected int getSwipeBackLayoutTracking() {
        return SwipeBackLayout.EDGE_LEFT;
    }

    @Override
    protected boolean getSwipeBackLayoutEnabled() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH) {
            return false;
        } else {
            return true;
        }
    }

}
