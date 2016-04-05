package reader.simple.com.simple_reader.animator;

import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.support.v7.widget.RecyclerView;
import android.view.animation.Interpolator;

/**
 * ==================================================
 * 项目名称：Simple_Reader
 * 创建人：wangxiaolong
 * 创建时间：16/4/2 下午4:40
 * 修改时间：16/4/2 下午4:40
 * 修改备注：
 * Version：
 * ==================================================
 */
public class SlideInUpDelayAnimator extends MyBaseItemAnimtor {
    private Interpolator mInterpolator;

    public SlideInUpDelayAnimator(Interpolator mInterpolator) {
        this.mInterpolator = mInterpolator;
    }

    @Override
    protected void onAnimateRemoveImpl(RecyclerView.ViewHolder holder) {
        ViewCompat.animate(holder.itemView)
                .setDuration(getRemoveDuration())
                .alpha(0)
                .scaleX(0f)
                .scaleY(0f)
                .setDuration(getRemoveDuration())
                .setStartDelay(getRemoveDelay(holder))
                .setListener(getDefaultVpaListener(holder)).start();
    }

    @Override
    protected void preAnimateRemove(RecyclerView.ViewHolder holder) {
        super.preAnimateRemove(holder);
    }

    @Override
    protected void preAnimateAdd(RecyclerView.ViewHolder holder) {
        ViewCompat.setTranslationY(holder.itemView, holder.itemView.getHeight());
        ViewCompat.setAlpha(holder.itemView, 0);
        ViewCompat.setScaleX(holder.itemView, 0.3F);
        ViewCompat.setScaleY(holder.itemView, 0.3f);
    }

    @Override
    protected ViewPropertyAnimatorCompat onAnimateAdd(RecyclerView.ViewHolder holder) {
        return ViewCompat.animate(holder.itemView)
                .translationY(0)
                .alpha(1)
                .scaleX(1.0f)
                .scaleY(1.0f)
                .setInterpolator(mInterpolator)
                .setDuration(getAddDuration())
                .setStartDelay(getAddDelay(holder));

    }
}
