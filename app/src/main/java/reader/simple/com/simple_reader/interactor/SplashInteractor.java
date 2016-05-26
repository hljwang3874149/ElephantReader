package reader.simple.com.simple_reader.interactor;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;

/**
 * ==================================================
 * 项目名称：Simple_Reader
 * 创建人：wangxiaolong
 * 创建时间：16/3/18 上午11:31
 * 修改时间：16/3/18 上午11:31
 * 修改备注： 交互处理器
 * Version：
 * ==================================================
 */
public class SplashInteractor {

    public Bitmap getSplashBitmap(Context context) {
//        Glide.with(context).load(Uri.parse("file:///android_asset/splash/splash_background.jpg")).asBitmap()
        Bitmap mBitmap;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        try {
            mBitmap = BitmapFactory.decodeStream(context.getResources().getAssets().open
                    ("splash/splash_background.jpg"), null, options);
        } catch (IOException e) {
            mBitmap = null;
            e.printStackTrace();
        }
        return mBitmap;
    }

}
