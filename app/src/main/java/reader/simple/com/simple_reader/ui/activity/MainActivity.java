package reader.simple.com.simple_reader.ui.activity;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.InjectView;
import reader.simple.com.simple_reader.R;
import reader.simple.com.simple_reader.ui.activity.adapter.MainDrawerAdapter;
import reader.simple.com.simple_reader.ui.activity.base.BaseActivity;

public class MainActivity extends BaseActivity {

    @InjectView(R.id.slide_content)
    ListView slideContentList;
    @InjectView(R.id.main_drawer)
    DrawerLayout mainDrawer;
    @InjectView(R.id.toolbar)
    Toolbar toolbar;

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

    }

}
