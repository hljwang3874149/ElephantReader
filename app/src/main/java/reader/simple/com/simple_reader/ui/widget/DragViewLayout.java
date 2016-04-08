package reader.simple.com.simple_reader.ui.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.FrameLayout;

import reader.simple.com.simple_reader.common.DebugUtil;


/**
 * ==================================================
 * 项目名称：mobike
 * 创建人：wangxiaolong
 * 创建时间：16/4/6 下午10:03
 * 修改时间：16/4/6 下午10:03
 * 修改备注：
 * Version：
 * ==================================================
 */
public class DragViewLayout extends FrameLayout {
    private ViewDragHelper mViewDrageHelper;
    private View mFirstView, mSecondView;
    private float mX, mY;
    private int mViewHight;
    private DrageViewClampVerticalListener mListener;

    public void setmListener(DrageViewClampVerticalListener mListener) {
        this.mListener = mListener;
    }

    public DragViewLayout(Context context) {
        this(context, null);
    }

    public DragViewLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DragViewLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        mViewDrageHelper = ViewDragHelper.create(this, mCallback);

    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mFirstView = getChildAt(1);
        mSecondView = getChildAt(0);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mViewHight = mFirstView.getMeasuredHeight();
        mX = mFirstView.getLeft();
        mY = mFirstView.getTop();
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        Bitmap bitmap = Bitmap.createBitmap(100,100, Bitmap.Config.ARGB_8888);
        Canvas mCanvas = new Canvas(bitmap);
        DebugUtil.e("dispathcDraw");
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measureWidth(widthMeasureSpec), measureHight(heightMeasureSpec));
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private int measureHight(int heightMeasureSpec) {
        int specMode = MeasureSpec.getMode(heightMeasureSpec);
        int specSize = MeasureSpec.getSize(heightMeasureSpec);
        if (specMode == MeasureSpec.EXACTLY) {
            return specSize;
        } else {
            return specSize;
        }

    }

    private int measureWidth(int widthMeasureSpec) {
        int specMode = MeasureSpec.getMode(widthMeasureSpec);
        int specSize = MeasureSpec.getSize(widthMeasureSpec);
        if (specMode == MeasureSpec.EXACTLY) {
            return specSize;
        } else {
            return specSize;
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mViewDrageHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mViewDrageHelper.processTouchEvent(event);
        return true;
    }

    @Override
    public void computeScroll() {

        if (mViewDrageHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }


    private ViewDragHelper.Callback mCallback = new ViewDragHelper.Callback() {

        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return child == mFirstView;
        }

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            return left;
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            return top;
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            super.onViewReleased(releasedChild, xvel, yvel);
            if (mListener == null) {
                return;
            }
            if (Math.abs(releasedChild.getTop() - mFirstView.getTop()) > ViewConfiguration.get(getContext()).getScaledTouchSlop() * 3) {
                mListener.onVerticalMoveSuccess();
            } else {
                mViewDrageHelper.smoothSlideViewTo(mFirstView, mFirstView.getLeft(), mFirstView.getTop());
                ViewCompat.postInvalidateOnAnimation(DragViewLayout.this);
            }
        }

        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            super.onViewPositionChanged(changedView, left, top, dx, dy);
        }
    };

    public interface DrageViewClampVerticalListener {

        void onVerticalMoveSuccess();

        void moveFailture();
    }


}
