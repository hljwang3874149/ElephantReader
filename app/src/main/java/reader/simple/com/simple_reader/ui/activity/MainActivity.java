package reader.simple.com.simple_reader.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.InjectView;
import reader.simple.com.simple_reader.R;
import reader.simple.com.simple_reader.animator.ItemAnimatorFactory;
import reader.simple.com.simple_reader.common.ACache;
import reader.simple.com.simple_reader.common.Constants;
import reader.simple.com.simple_reader.common.DeviceUtil;
import reader.simple.com.simple_reader.common.DoubleClickExitHelper;
import reader.simple.com.simple_reader.common.Utils;
import reader.simple.com.simple_reader.common.netWork.RetrofitNetWork;
import reader.simple.com.simple_reader.domain.ArticleInfo;
import reader.simple.com.simple_reader.domain.PageInfo;
import reader.simple.com.simple_reader.presenter.Presenter;
import reader.simple.com.simple_reader.presenter.impl.MainPresenter;
import reader.simple.com.simple_reader.ui.activity.base.BaseActivity;
import reader.simple.com.simple_reader.ui.adapter.MainDrawerAdapter;
import reader.simple.com.simple_reader.ui.adapter.MainRecylerViewAdapter;
import reader.simple.com.simple_reader.viewInterface.MainView;
import rx.subscriptions.CompositeSubscription;

public class MainActivity extends BaseActivity implements
        MainView, MainRecylerViewAdapter.AdapterCallback {

    @InjectView(R.id.slide_content)
    ListView slideContentList;
    @InjectView(R.id.main_drawer)
    DrawerLayout mainDrawer;
    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.main_recycler_view)
    RecyclerView mainRecyclerView;
    @InjectView(R.id.main_swipe_freshlayout)
    SwipeRefreshLayout mSwipeFreshlayout;
    private MainRecylerViewAdapter mRecylerAdapter;
    private ACache mACahe;
    private static final String KEY_PAGEINFOS = "articles";
    private LinearLayoutManager mLinearLayoutManager;
    private int currentRow = 0;
    private int lastVisibleItem;
    private boolean isHaveMore = true;
    private DoubleClickExitHelper mDoubleClickExitHelper;
    private CompositeSubscription subscriptions;


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
        return R.layout.activity_main;
    }

    @Override
    protected void initViewsAndEvents() {
        mACahe = ACache.get(this);
        subscriptions = new CompositeSubscription();

        initDrawerLayout();

        Presenter presenter = new MainPresenter(this, this);
        presenter.initialized();
        mDoubleClickExitHelper = new DoubleClickExitHelper(this);

    }

    private void initDrawerLayout() {
        ActionBarDrawerToggle barDrawerToggle = new
                ActionBarDrawerToggle(this, mainDrawer,
                        toolbar, R.string.app_name, R.string.app_name) {
                    @Override
                    public void onDrawerClosed(View drawerView) {
                        super.onDrawerClosed(drawerView);
                        invalidateOptionsMenu();
                    }

                    @Override
                    public void onDrawerOpened(View drawerView) {
                        super.onDrawerOpened(drawerView);
                        invalidateOptionsMenu();
                    }

                    @Override
                    public void onDrawerStateChanged(int newState) {
                        super.onDrawerStateChanged
                                (newState);
                        if (newState == DrawerLayout
                                .STATE_SETTLING) {
                            slideContentList
                                    .startLayoutAnimation();
                        }
                    }
                };
        mainDrawer.setDrawerListener(barDrawerToggle);
        barDrawerToggle.syncState();


        List<String> data = new ArrayList<>();
        Collections.addAll(data, getResources().getStringArray(R.array.silde_menu));
        MainDrawerAdapter mainDrawerAdapter = new
                MainDrawerAdapter(this, data);

        slideContentList.setAdapter(mainDrawerAdapter);
        slideContentList.setOnItemClickListener((adapterView, view, i, l) -> {
            switch (i) {
                case 0:
                    startActivity(new Intent(this, SettingActivity.class));
                    break;
                case 1:
                    startActivity(new Intent(this, AboutActivity.class));
                    break;
                case 2:
                    startActivityWithIntent(new Intent(this, ShowNotifyActivity.class));
                case 3:
                    startActivityWithIntent(new Intent(this, ReflectionActivity.class));
            }
//            setDayAndNightTheme();

            mainDrawer.closeDrawer(slideContentList);
        });
    }


    private void setDayAndNightTheme() {
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

    @Override
    public void initSwipeFreshView() {
        mSwipeFreshlayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary), Color.RED, Color.BLUE, Color.GRAY);
        mSwipeFreshlayout.setOnRefreshListener(() -> {
            currentRow = 0;
            getArticleInfos(currentRow);
        });

    }

    @Override
    public void initRecyclerView() {
        mLinearLayoutManager = new LinearLayoutManager(this);
        mainRecyclerView.setLayoutManager(mLinearLayoutManager);
        mainRecyclerView.setItemAnimator(ItemAnimatorFactory.slideIn());
        mRecylerAdapter = new MainRecylerViewAdapter(this, this);
        mainRecyclerView.setAdapter(mRecylerAdapter);
        PageInfo pageInfo = (PageInfo) mACahe.getAsObject(KEY_PAGEINFOS);
        if (null != pageInfo) {
            mRecylerAdapter.setItems(pageInfo.body.articleInfoList);
        } else {
            mSwipeFreshlayout.setProgressViewOffset(false, toolbar.getHeight(), DeviceUtil.dip2px(this, 20));
            mSwipeFreshlayout.setRefreshing(true);
            getArticleInfos(currentRow);
        }
        mainRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = mLinearLayoutManager.findLastVisibleItemPosition();
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE && mRecylerAdapter.getItemCount() > 0 && lastVisibleItem + 1 ==
                        mRecylerAdapter.getItemCount()) {
                    if (isHaveMore) {
                        ArticleInfo info;
                        mSwipeFreshlayout.setRefreshing(true);
                        if (currentRow == 0) {
                            getArticleInfos(currentRow);
                        } else {
                            info = mRecylerAdapter.getLastItem();
                            loadMoreArticles(info.createTime, info.updateTime);
                        }

                    } else {
                        showToastMessage(Constants.NONELOAD);
                    }

                }
            }
        });


    }

    protected void getArticleInfos(int pageNum) {
        subscriptions.add(
                RetrofitNetWork.getInstance().getPageInfos(20, pageNum)
                        .subscribe(pageInfo -> {
                                    if (pageNum == 0) {
                                        mRecylerAdapter.clear();
                                        //仅存储最新20条，有效时间2小时
                                        mACahe.put(KEY_PAGEINFOS, pageInfo, (int) (DateUtils.HOUR_IN_MILLIS * 2 / 1000));
                                    }
                                    setLoadMore(pageInfo);
                                    mRecylerAdapter.setItems(pageInfo.body.articleInfoList);
                                }
                                , this::doOnThrow
                                , this::hideRefresh)
        );

    }

    private void doOnThrow(Throwable throwable) {
        RetrofitNetWork.catchThrowable(this, throwable);
        mRecylerAdapter.setHintMessage(Constants.NET_ERROR);
        hideRefresh();
    }

    private void hideRefresh() {
        if (mSwipeFreshlayout != null && mSwipeFreshlayout.isRefreshing()) {
            mSwipeFreshlayout.setRefreshing(false);
        }
    }

    private void setLoadMore(PageInfo pageInfo) {
        if (pageInfo.body.articleInfoList.size() == 20) {
            ++currentRow;
            isHaveMore = true;
        } else {
            mRecylerAdapter.setHintMessage(Constants.NONELOAD);
            isHaveMore = false;
        }
    }

    protected void loadMoreArticles(String createTime, String updateTime) {
        if (Utils.isNetworkConnected(this)) {
            subscriptions.add(
                    RetrofitNetWork.getInstance().loadMoreArticle(20, createTime, updateTime)
                            .subscribe(pageInfo -> {
                                        setLoadMore(pageInfo);
                                        mRecylerAdapter.setItems(pageInfo.body.articleInfoList);
                                    },
                                    this::doOnThrow
                                    ,
                                    this::hideRefresh
                            )
            );
        }
    }

    @Override
    public void enterDetail(String path, View imageView, String arctleId) {
        ActivityOptionsCompat mOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(this, imageView, arctleId);
        Intent startIntent = new Intent(this, WebTextActivity.class);
        startIntent.putExtra(Constants.KEY_ARCITLE, arctleId);
        startIntent.putExtra(Constants.KEY_IMAG_PATH, path);
        ActivityCompat.startActivity(this, startIntent, mOptions.toBundle());
    }

    @Override
    public void onBackPressed() {
        mDoubleClickExitHelper.onBackPress();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        subscriptions.unsubscribe();
    }
}
