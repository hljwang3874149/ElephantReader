package reader.simple.com.simple_reader.test;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.widget.ImageView;

import butterknife.InjectView;
import reader.simple.com.simple_reader.R;
import reader.simple.com.simple_reader.ui.activity.base.BaseActivity;
import rx.Single;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class DropImageActiviy extends BaseActivity {
    private int imageSize;
    private int radius = 10;

    @InjectView(R.id.drop_image)
    ImageView dropImage;

    @Override
    protected boolean pendingTransition() {
        return false;
    }

    @Override
    protected TransitionMode getTransitionMode() {
        return null;
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_drop_image_activiy;
    }

    @Override
    protected void initViewsAndEvents() {
        imageSize = getResources().getDimensionPixelSize(R.dimen.drop_image_size);
        Single.create((Single.OnSubscribe<Integer>) singleSubscriber -> singleSubscriber.onSuccess(
                R.drawable.an
        ))
//        Single.just(R.drawable.an)
                .subscribeOn(Schedulers.io())
                .map(integer -> createDropImage())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bitmap -> {
                    dropImage.setImageBitmap(bitmap);
                }, throwable -> {

                });


    }

    private Bitmap createDropImage() {
        Bitmap result = Bitmap.createBitmap(imageSize, imageSize, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(result);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.an);

        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(0, 0, imageSize, imageSize);

        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);

        paint.setXfermode(null);
        canvas.drawRoundRect(rectF, radius, radius, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

        canvas.drawBitmap(bitmap, null, rectF, paint);
        return result;
    }

}
