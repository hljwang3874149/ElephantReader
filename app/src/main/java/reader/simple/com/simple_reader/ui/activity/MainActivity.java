package reader.simple.com.simple_reader.ui.activity;

import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
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
import reader.simple.com.simple_reader.common.Utils;
import reader.simple.com.simple_reader.common.netWork.RetrofitNetWork;
import reader.simple.com.simple_reader.domain.PageInfo;
import reader.simple.com.simple_reader.presenter.Presenter;
import reader.simple.com.simple_reader.presenter.impl.MainPresenter;
import reader.simple.com.simple_reader.ui.activity.base.BaseActivity;
import reader.simple.com.simple_reader.ui.adapter.MainDrawerAdapter;
import reader.simple.com.simple_reader.ui.adapter.MainRecylerViewAdapter;
import reader.simple.com.simple_reader.viewInterface.MainView;

public class MainActivity extends BaseActivity implements MainView {

    @InjectView(R.id.slide_content)
    ListView slideContentList;
    @InjectView(R.id.main_drawer)
    DrawerLayout mainDrawer;
    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.main_recycler_view)
    RecyclerView mainRecyclerView;
    @InjectView(R.id.main_swipe_freshlayout)
    SwipeRefreshLayout mainSwipeFreshlayout;
    private Presenter mPresenter;
    private MainRecylerViewAdapter mRecylerAdapter;
    private ACache mACahe;
    private static final String KEY_PAGEINFOS = "articles";

    @Override
    protected boolean pendingTransition() {
        return false;
    }

    @Override
    protected TransitionMode getTransitionMode() {
        return TransitionMode.FADE;
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViewsAndEvents() {
        mACahe = ACache.get(this);
        ActionBarDrawerToggle barDrawerToggle = new ActionBarDrawerToggle(this, mainDrawer,
                toolbar, R.string.app_name,
                R.string.app_name) {
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
                super.onDrawerStateChanged(newState);
                if (newState == DrawerLayout.STATE_SETTLING) {
                    slideContentList.startLayoutAnimation();
                }
            }
        };
        mainDrawer.setDrawerListener(barDrawerToggle);
        barDrawerToggle.syncState();

        String[] TEST = {"设置", "关于", "夜间模式", "白天模式"};
        List data = new ArrayList<>();
        Collections.addAll(data, TEST);
        MainDrawerAdapter mainDrawerAdapter = new MainDrawerAdapter(this, data);

        slideContentList.setAdapter(mainDrawerAdapter);
        slideContentList.setOnItemClickListener((adapterView, view, i, l) -> {
            showToastMessage((String) adapterView.getAdapter().getItem(i));
            mainDrawer.closeDrawer(slideContentList);
        });

        mPresenter = new MainPresenter(this, this);
        mPresenter.initialized();


    }

    @Override
    public void initSwipeFreshView() {
        mainSwipeFreshlayout.setOnRefreshListener(() -> {
            getArticleInfos(0);
        });

    }

    @Override
    public void initRecyclerView() {

        List<String> data = new ArrayList<>();
//        Collections.addAll(data, getResources().getStringArray(R.array.main_testdata));

        mainRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mainRecyclerView.setItemAnimator(ItemAnimatorFactory.slideIn());
//        mainRecyclerView.addItemDecoration(new GridDividerDecoration(this));
        mRecylerAdapter = new MainRecylerViewAdapter(this);
        mainRecyclerView.setAdapter(mRecylerAdapter);
        PageInfo pageInfo = (PageInfo) mACahe.getAsObject(KEY_PAGEINFOS);
        if (null != pageInfo) {
            mRecylerAdapter.setItems(pageInfo.body.articleInfoList);
        } else {
            getArticleInfos(0);
        }

    }

    protected void getArticleInfos(int pageNum) {
        if(Utils.isNetworkConnected(this)){
            RetrofitNetWork.getInstance().getPageInfos(20, pageNum)
                    .subscribe(pageInfo -> {
                                if (pageNum == 0) {
                                    mRecylerAdapter.clear();
                                    //仅存储最新20条，有效时间2小时
                                    mACahe.put(KEY_PAGEINFOS, pageInfo, (int) (DateUtils
                                            .HOUR_IN_MILLIS *
                                            2));
                                }
                                mRecylerAdapter.setItems(pageInfo.body.articleInfoList);
                            }
                            , throwable -> {
                                showSnackMessage(mainRecyclerView, throwable.getMessage());
                            }
                            , () -> {
                                if (mainSwipeFreshlayout != null && mainSwipeFreshlayout.isRefreshing
                                        ()) {
                                    mainSwipeFreshlayout.setRefreshing(false);
                                }
                            });
        }

    }
}
