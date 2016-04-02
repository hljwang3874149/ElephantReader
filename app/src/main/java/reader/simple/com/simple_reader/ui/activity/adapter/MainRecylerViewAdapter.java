package reader.simple.com.simple_reader.ui.activity.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
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
 * 创建时间：16/4/2 下午5:00
 * 修改时间：16/4/2 下午5:00
 * 修改备注：
 * Version：
 * ==================================================
 */
public class MainRecylerViewAdapter extends RecyclerView.Adapter<MainRecylerViewAdapter
        .ViewHolder> {


    private List<String> testDate = new ArrayList<>();
    private Context mContext;

    public MainRecylerViewAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setItems(List<String> info) {
        int pos = getItemCount();
        testDate.addAll(info);
        notifyItemRangeInserted(pos, info.size());
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(mContext, R.layout.left_drawer_menu_item, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.drawerMenuItemTitle.setText(testDate.get(position));

    }


    @Override
    public int getItemCount() {
        return testDate.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.drawer_menu_item_icon)
        ImageView drawerMenuItemIcon;
        @InjectView(R.id.drawer_menu_item_title)
        TextView drawerMenuItemTitle;

        ViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }
    }
}
