package reader.simple.com.simple_reader.domain;

import com.google.gson.annotations.SerializedName;

/**
 * ==================================================
 * 项目名称：Simple_Reader
 * 创建人：wangxiaolong
 * 创建时间：16/4/5 下午4:14
 * 修改时间：16/4/5 下午4:14
 * 修改备注：
 * Version：
 * ==================================================
 */
public class ArticleDescInfo {
    @SerializedName("body")
    public ArticleBody articleBody;

    public class ArticleBody {
        @SerializedName("article")
        public ArticleInfo articleInfo;
    }
}
