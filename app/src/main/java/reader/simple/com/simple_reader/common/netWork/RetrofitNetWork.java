package reader.simple.com.simple_reader.common.netWork;

import android.content.Context;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import reader.simple.com.simple_reader.R;
import reader.simple.com.simple_reader.common.Utils;
import reader.simple.com.simple_reader.domain.ArticleDescInfo;
import reader.simple.com.simple_reader.domain.PageInfo;
import reader.simple.com.simple_reader.domain.SplashInfo;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * ==================================================
 * 项目名称：Simple_Reader
 * 创建人：wangxiaolong
 * 创建时间：16/4/5 下午4:22
 * 修改时间：16/4/5 下午4:22
 * 修改备注：
 * Version：
 * ==================================================
 */
public class RetrofitNetWork {
    private ApiService mApiService;

    public static final String BaseURL = "http://app.idaxiang.org/api/v1_0/";


    private volatile static RetrofitNetWork ourInstance = null;

    public static RetrofitNetWork getInstance() {
        if (null == ourInstance) {
            synchronized (RetrofitNetWork.class) {
                if (ourInstance == null) {
                    ourInstance = new RetrofitNetWork();
                }
            }
        }
        return ourInstance;
    }

    private RetrofitNetWork() {
        Executor cacheExecutor = Executors.newCachedThreadPool();
        Retrofit retrofit = new Retrofit.Builder()
                .callbackExecutor(cacheExecutor)
                .baseUrl(BaseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        mApiService = retrofit.create(ApiService.class);

    }


    public static void catchThrowable(Context context, Throwable throwable) {
        if (null == throwable) {
            return;
        }
        if (null != throwable.getCause() && throwable.getCause().toString().contains("android.system.GaiException")) {
            Utils.showToast(context, context.getString(R.string.network_error));
        } else {
            Utils.showToast(context, throwable.getMessage());
        }

    }

    public Observable<PageInfo> getPageInfos(int pageSize, int pageNum) {
        return mApiService.getPageInfos(pageSize, pageNum)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<ArticleDescInfo> getArticleDescInfo(String articleId) {
        return mApiService.getArticleDescInfo(articleId)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<PageInfo> loadMoreArticle(int pageSize, String createTime, String updateTime) {
        return mApiService.loadMoreArticle(pageSize, createTime, updateTime)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<SplashInfo> loadSplashImg(){
        return mApiService.loadSplashImg()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


}
