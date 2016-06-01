package reader.simple.com.simple_reader.ui.adapter;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import reader.simple.com.simple_reader.R;
import reader.simple.com.simple_reader.common.Constants;
import reader.simple.com.simple_reader.common.Time;
import reader.simple.com.simple_reader.domain.ArticleInfo;

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
public class MainRecylerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int KEY_ITEM = 1;
    private static final int KEY_FOOTER = 2;
    private String hintMessage = Constants.LOADING;

    private List<ArticleInfo> testDate = new ArrayList<>();
    private Context mContext;
    private AdapterCallback mAdapterCallback;


    public MainRecylerViewAdapter(Context mContext, AdapterCallback mAdapterCallback) {
        this.mContext = mContext;
        this.mAdapterCallback = mAdapterCallback;
    }

    public void setItems(List<ArticleInfo> info) {
        int pos = getItemCount();
        testDate.addAll(info);
        notifyItemRangeInserted(pos, info.size());

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
        notifyItemRangeRemoved(0, size + 1);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (KEY_ITEM == viewType) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.activity_recyclerview_item, parent, false);
            return new ViewHolder(view);
        } else {
            View view = View.inflate(mContext, R.layout.bikeput_station_list_footview, null);
            return new FooterViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            onBindViewHolder((ViewHolder) holder, position);
        } else {
            if (getItemCount() > 1) {
                ((FooterViewHolder) holder).footview.setVisibility(View.VISIBLE);
                ((FooterViewHolder) holder).footview.setText(hintMessage);
            } else
                ((FooterViewHolder) holder).footview.setVisibility(View.GONE);

        }

    }


    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.articleTitle.setText(testDate.get(position).title);
        Glide.with(mContext)
                .load(testDate.get(position).headpic)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .thumbnail(0.2f)
                .into(holder.articleAvatar);
        holder.articleAuthor.setText(testDate.get(position).author);
        holder.acticleBrief.setText(testDate.get(position).brief);
        holder.articleCreateDate.setText(Time.getYMD(new Date(Long.valueOf(testDate.get(position)
                .createTime))));
        holder.articleReadNum.setText(String.format(mContext.getString(R.string.arcticle_read),
                testDate.get(position).readNum));
        ViewCompat.setTransitionName(holder.mRootView, testDate.get(position).id);
        holder.mRootView.setOnClickListener(v -> {
            mAdapterCallback.enterDetail(testDate.get(position).headpic, holder.articleAvatar, testDate.get(position).id);
        });

    }


    @Override
    public int getItemCount() {
        return testDate.size() + 1;
    }

    public ArticleInfo getLastItem() {
        return testDate.get(testDate.size() - 1);
    }

    @Override
    public int getItemViewType(int position) {
        if (position + 1 == getItemCount())
            return KEY_FOOTER;
        else {
            return KEY_ITEM;
        }
    }


    public void setHintMessage(String hintMessage) {
        this.hintMessage = hintMessage;
        notifyItemChanged(getItemCount());
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

    public interface AdapterCallback {
        void enterDetail(String path, View imageView, String arctleId);
    }

    static class FooterViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.footview)
        TextView footview;

        FooterViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }
    }
}
