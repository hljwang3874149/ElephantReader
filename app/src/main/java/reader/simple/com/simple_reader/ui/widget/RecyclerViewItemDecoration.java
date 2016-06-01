package reader.simple.com.simple_reader.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * ==================================================
 * 项目名称：Simple_Reader
 * 创建人：wangxiaolong
 * 创建时间：16/6/1 下午2:59
 * 备注：
 * Version：
 * ==================================================
 */
public class RecyclerViewItemDecoration extends RecyclerView.ItemDecoration {
    private static final int[] ATTRS = {android.R.attr.listDivider};

    private Drawable mDivider;
    int childCount;

    public RecyclerViewItemDecoration(Context mctx) {
        TypedArray a = mctx.obtainStyledAttributes(ATTRS);
        mDivider = a.getDrawable(0);
        a.recycle();
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        if (parent.getChildCount() == 0) return;
        final int left = parent.getPaddingLeft();
        final int right = parent.getWidth() - parent.getPaddingRight();

        childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            if (child.getHeight() == 0) break;

            final RecyclerView.LayoutParams mLayoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
            final int top = child.getBottom() + mLayoutParams.bottomMargin;
            final int bottom = top + mDivider.getIntrinsicHeight();
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);

        }
//
//        final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
//        int top = child.getBottom() + params.bottomMargin + mInsets;
//        int bottom = top + mDivider.getIntrinsicHeight();
//
//        final int parentBottom = parent.getHeight() - parent.getPaddingBottom();
//        while (bottom < parentBottom) {
//            mDivider.setBounds(left, top, right, bottom);
//            mDivider.draw(c);
//            top += mInsets + params.topMargin + child.getHeight() + params.bottomMargin + mInsets;
//            bottom = top + mDivider.getIntrinsicHeight();
//        }

    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.set(0, 0, 0, mDivider.getIntrinsicHeight());
    }
}
