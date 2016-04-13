package reader.simple.com.simple_reader.common.netWork;

import reader.simple.com.simple_reader.domain.ArticleDescInfo;
import reader.simple.com.simple_reader.domain.PageInfo;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * ==================================================
 * 项目名称：Simple_Reader
 * 创建人：wangxiaolong
 * 创建时间：16/4/5 下午4:17
 * 修改时间：16/4/5 下午4:17
 * 修改备注：
 * Version：
 * ==================================================
 */
public interface ApiService {
    @GET("art/list")
    Observable<PageInfo> getPageInfos(@Query("pageSize") int pageSize, @Query("page") int pageNum);

    @GET("art/info")
    Observable<ArticleDescInfo> getArticleDescInfo(@Query("id") String id);

    @GET("art/list")
    Observable<PageInfo> loadMoreArticle(@Query("pageSize") int pageSize, @Query("create_time") String createTime, @Query("update_time") String
            updateTime);
}
