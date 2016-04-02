package reader.simple.com.simple_reader.animator;

import android.support.v7.widget.RecyclerView;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

/**
 * ==================================================
 * 项目名称：Simple_Reader
 * 创建人：wangxiaolong
 * 创建时间：16/4/2 下午4:55
 * 修改时间：16/4/2 下午4:55
 * 修改备注：
 * Version：
 * ==================================================
 */
public class ItemAnimatorFactory {
    public static RecyclerView.ItemAnimator slideIn(){
        SlideInUpDelayAnimator animator =  new SlideInUpDelayAnimator(new DecelerateInterpolator(1.2f));
        animator.setAddDuration(600);
        return  animator;
    }
}
