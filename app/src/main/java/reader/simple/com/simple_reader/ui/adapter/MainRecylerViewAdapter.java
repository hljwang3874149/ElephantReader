package reader.simple.com.simple_reader.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import reader.simple.com.simple_reader.R;
import reader.simple.com.simple_reader.common.Time;
import reader.simple.com.simple_reader.domain.ArticleInfo;
import reader.simple.com.simple_reader.ui.activity.WebTextActivity;

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


    private List<ArticleInfo> testDate = new ArrayList<>();
    private Context mContext;

    public MainRecylerViewAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setItems(List<ArticleInfo> info) {
        int pos = getItemCount();
        testDate.addAll(info);
        notifyItemRangeInserted(pos, info.size());
//        notifyDataSetChanged();
    }

    public void setItem(ArticleInfo info) {
        if (testDate.size() <= 8) {
            testDate.add(1, info);
            notifyItemInserted(1);
        } else {
            testDate.remove(1);
            removeItem(1);
        }
    }

    public void removeItem(int postion) {
        notifyItemRemoved(postion);

    }

    public void clear() {
        if (testDate.size() == 0) {
            return;
        }
        int size = testDate.size();
        testDate.clear();
        notifyItemRangeRemoved(0, size);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(mContext, R.layout.activity_recyclerview_item, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.articleTitle.setText(testDate.get(position).title);
        Glide.with(mContext).load(testDate.get(position).headpic).into(holder.articleAvatar);
        holder.articleAuthor.setText(testDate.get(position).author);
        holder.acticleBrief.setText(testDate.get(position).brief);
        holder.articleCreateDate.setText(Time.getYMD(new Date(Long.valueOf(testDate.get(position)
                .createTime))));
        holder.articleReadNum.setText(String.format(mContext.getString(R.string.arcticle_read),
                testDate.get(position).readNum));
        holder.mRootView.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, WebTextActivity.class);
            intent.putExtra("id", testDate.get(position).id);
            mContext.startActivity(intent);
        });

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
        @InjectView(R.id.article_title)
        TextView articleTitle;
        @InjectView(R.id.article_avatar)
        ImageView articleAvatar;
        @InjectView(R.id.article_author)
        TextView articleAuthor;
        @InjectView(R.id.acticle_brief)
        TextView acticleBrief;
        @InjectView(R.id.article_create_date)
        TextView articleCreateDate;
        @InjectView(R.id.article_readNum)
        TextView articleReadNum;
        @InjectView(R.id.rootview)
        RelativeLayout mRootView;

        ViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }
    }
}
