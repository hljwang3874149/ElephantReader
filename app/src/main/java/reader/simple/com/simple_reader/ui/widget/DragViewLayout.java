package reader.simple.com.simple_reader.ui.widget;

import android.content.Context;
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
 * 项目名称：SimpleReader
 * 创建人：wangxiaolong
 * 创建时间：16/4/6 下午10:03
 * 修改时间：16/4/6 下午10:03
 * 修改备注：
 * Version：
 * ==================================================
 */
public class DragViewLayout extends FrameLayout {
    private ViewDragHelper mViewDrageHelper;
    private View mFirstView;
    private int mViewTop , mViewLeft;
    private DrageViewClampVerticalListener mListener;
    private boolean mVerticalUp = true, mVerticalDown = true, mHorizonLeft, mHorizonRight;

    public void setHorizonLeft(boolean horizonLeft, boolean verticalDown, boolean verticalUp, boolean nHorizonRight) {
        mHorizonLeft = horizonLeft;
        mVerticalDown = verticalDown;
        mVerticalUp = verticalUp;
        this.mHorizonRight = nHorizonRight;
    }

    public void setListener(DrageViewClampVerticalListener mListener) {
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
        //初始化 ViewDragHelper
        mViewDrageHelper = ViewDragHelper.create(this, mCallback);

    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        //当view inflate 完毕 获取 要监听的view
//        mFirstView = findViewById(R.id.notify_image);
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        //mViewDrageHelper拦截手势事件 必须的
        return mViewDrageHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // mViewDrageHelper处理手势事件 必须
        mViewDrageHelper.processTouchEvent(event);
        return true;
    }

    @Override
    public void computeScroll() {
        //需重写此方法 ， ViewDrageHelper 内部使用ScrollerCompat Scoller的v4兼容类
        if (mViewDrageHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        mFirstView = getChildAt(0);
        mViewTop = mFirstView.getTop();
        mViewLeft = mFirstView.getLeft();
    }


    private ViewDragHelper.Callback mCallback = new ViewDragHelper.Callback() {

        //确认 当前view 是否需要处理手势
        @Override
        public boolean tryCaptureView(View child, int pointerId) {

            return child == mFirstView;
        }

        //处理横向手势
        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            if (mHorizonLeft || mHorizonRight) {
                if (null != mListener) {
                    mListener.onHorizonMoving();
                }
                if (mHorizonRight && !mHorizonLeft) {
                    if (left < 0) {
                        return child.getLeft();
                    }
                }
                return left;
            } else {
                return mFirstView.getLeft();
            }

        }

        //处理纵向手势
        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            if (mVerticalUp || mVerticalDown) {
                if (null != mListener) {
                    mListener.onVerticalMoving();
                }
                return top;
            } else {
                return child.getTop();
            }

        }
        //抬起手势处理

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            DebugUtil.e("onViewReleased");
            if (mListener == null) {
                return;
            }
            DebugUtil.e(" onViewReleased move = " + releasedChild.getTop() + " | up =" + mViewTop + " viewtop = " + mFirstView.getTop());
            if(mVerticalDown || mVerticalUp) {
                if (Math.abs(releasedChild.getTop() - mViewTop) > ViewConfiguration.get(getContext()).getScaledTouchSlop() * 3) {
                    mListener.onVerticalMoveDirection(releasedChild.getTop() < mViewTop);
                } else {
                    mViewDrageHelper.smoothSlideViewTo(mFirstView, mFirstView.getLeft(), mViewTop);
                    ViewCompat.postInvalidateOnAnimation(DragViewLayout.this);
                    mListener.onVerticalBack();
                }
            }else{
                mViewDrageHelper.smoothSlideViewTo(mFirstView, mFirstView.getLeft(), mViewTop);
                ViewCompat.postInvalidateOnAnimation(DragViewLayout.this);
                mListener.onVerticalBack();
            }
        }

    };

    public interface DrageViewClampVerticalListener {

        void onVerticalMoving();

        void onVerticalMoveDirection(boolean isUp);

        void onVerticalBack();

        void onHorizonMoving();

    }


}
