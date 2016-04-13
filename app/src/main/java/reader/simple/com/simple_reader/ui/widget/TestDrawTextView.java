package reader.simple.com.simple_reader.ui.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.TextView;

import static android.graphics.Shader.TileMode.CLAMP;

/**
 * ==================================================
 * 项目名称：Simple_Reader
 * 创建人：wangxiaolong
 * 创建时间：16/4/13 下午4:09
 * 修改时间：16/4/13 下午4:09
 * 修改备注：
 * Version：
 * ==================================================
 */
public class TestDrawTextView extends TextView {
    private Paint mPaint;
    private Paint mPaintInside;
    private int mViewWidth;
    private LinearGradient mLinearGradient;
    private Matrix mMatrix;
    private int mTranslate;

    private RectF mRectF ;

    public TestDrawTextView(Context context) {
        this(context, null);
    }

    public TestDrawTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TestDrawTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
//        TypedArray ta = context.obtainStyledAttributes(attrs,R.styleable.ActionBar);
        initView();
    }

    private void initView() {
        mPaint = new Paint();
        mPaint.setColor(getResources().getColor(android.R.color.holo_blue_light));
        mPaint.setStyle(Paint.Style.FILL);

        mPaintInside = new Paint();
        mPaintInside.setColor(Color.YELLOW);
        mPaintInside.setStyle(Paint.Style.FILL);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (0 == mViewWidth) {
            mViewWidth = getMeasuredWidth();
            if (mViewWidth > 0) {
                mLinearGradient = new LinearGradient(0, 0, mViewWidth, 0,
                        new int[]{Color.WHITE , Color.BLUE}, null, CLAMP);
                //重点code 获取 textview 原生piant 并设置 shader
                mPaint = getPaint();
                mPaint.setShader(mLinearGradient);
                mMatrix = new Matrix();

            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas){

//        preSuperDraw(canvas);

        super.onDraw(canvas);
        if (mMatrix != null) {
            mTranslate += mViewWidth / 5;
            if(mTranslate >  2*mViewWidth){
                mTranslate = -mViewWidth;
            }
            mMatrix.setTranslate(mTranslate,0);
            mLinearGradient.setLocalMatrix(mMatrix);
            postInvalidateDelayed(300);
        }



//        canvas.restore();
    }

    private void preSuperDraw(Canvas canvas) {
        canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), mPaint);

        canvas.drawRect(10, 10, getMeasuredWidth() - 10, getMeasuredHeight() - 10, mPaintInside);
        canvas.save();
        canvas.translate(10, 0);
    }
}
