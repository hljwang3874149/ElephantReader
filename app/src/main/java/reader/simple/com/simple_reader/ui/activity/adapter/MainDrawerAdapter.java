package reader.simple.com.simple_reader.ui.activity.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import reader.simple.com.simple_reader.R;

/**
 * ==================================================
 * 项目名称：Simple_Reader
 * 创建人：wangxiaolong
 * 创建时间：16/3/30 下午3:51
 * 修改时间：16/3/30 下午3:51
 * 修改备注：
 * Version：
 * ==================================================
 */
public class MainDrawerAdapter extends BaseAdapter {

    private List<String> data = new ArrayList<>();
    private Context context;

    public MainDrawerAdapter(Context context, List<String> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.size() == 0 && i < data.size() ? null : data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (null == view) {
            view = View.inflate(context, R.layout.left_drawer_menu_item, null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.drawerMenuItemTitle.setText(data.get(i));

        return view;
    }

    static class ViewHolder {
        @InjectView(R.id.drawer_menu_item_icon)
        ImageView drawerMenuItemIcon;
        @InjectView(R.id.drawer_menu_item_title)
        TextView drawerMenuItemTitle;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
