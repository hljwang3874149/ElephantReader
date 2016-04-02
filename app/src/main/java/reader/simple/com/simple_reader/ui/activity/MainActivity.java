package reader.simple.com.simple_reader.ui.activity;

import android.os.Bundle;
import android.os.Handler;
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

import butterknife.ButterKnife;
import butterknife.InjectView;
import reader.simple.com.simple_reader.R;
import reader.simple.com.simple_reader.animator.ItemAnimatorFactory;
import reader.simple.com.simple_reader.presenter.Presenter;
import reader.simple.com.simple_reader.presenter.impl.MainPresenter;
import reader.simple.com.simple_reader.ui.activity.adapter.MainDrawerAdapter;
import reader.simple.com.simple_reader.ui.activity.adapter.MainRecylerViewAdapter;
import reader.simple.com.simple_reader.ui.activity.base.BaseActivity;
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
            //todo  refresh things

        });

    }

    @Override
    public void initRecyclerView() {
        new Handler().postDelayed(() -> {
            List<String> data = new ArrayList<>();
            Collections.addAll(data, getResources().getStringArray(R.array.main_testdata));

            mainRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            mainRecyclerView.setItemAnimator(ItemAnimatorFactory.slideIn());

            mRecylerAdapter = new MainRecylerViewAdapter(this);
            mainRecyclerView.setAdapter(mRecylerAdapter);
            mRecylerAdapter.setItems(data);

        }, DateUtils.SECOND_IN_MILLIS);
    }
}
