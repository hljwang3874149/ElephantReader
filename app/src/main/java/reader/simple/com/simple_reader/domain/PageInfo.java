package reader.simple.com.simple_reader.domain;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * ==================================================
 * 项目名称：Simple_Reader
 * 创建人：wangxiaolong
 * 创建时间：16/4/5 下午3:45
 * 修改时间：16/4/5 下午3:45
 * 修改备注：
 * Version：
 * ==================================================
 */
public class PageInfo implements Serializable {

    @SerializedName("body")
    public PageBody body;

    public class PageBody implements Serializable {
        //        @SerializedName("ad")
//        public AdInfo adInfo;
        @SerializedName("article")
        public List<ArticleInfo> articleInfoList;

    }


}
