package reader.simple.com.simple_reader.interactor;


import android.content.Context;

/**
 * ==================================================
 * 项目名称：Simple_Reader
 * 创建人：wangxiaolong
 * 创建时间：16/3/29 下午5:56
 * 修改时间：16/3/29 下午5:56
 * 修改备注：
 * Version：
 * ==================================================
 */
public class AboutInteractor {
    public float getCaneraDistance(Context context) {
        return context.getResources().getDisplayMetrics().density * 1600;


    }

}
