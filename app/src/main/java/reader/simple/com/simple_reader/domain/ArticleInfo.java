package reader.simple.com.simple_reader.domain;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * ==================================================
 * 项目名称：Simple_Reader
 * 创建人：wangxiaolong
 * 创建时间：16/4/5 下午4:08
 * 修改时间：16/4/5 下午4:08
 * 修改备注：
 * Version：
 * ==================================================
 */
public class ArticleInfo implements Serializable {
    @SerializedName("id")
    public String id;
    @SerializedName("title")
    public String title;
    @SerializedName("headpic")
    public String headpic;
    @SerializedName("raw_headpic")
    public String rawHeadpic;
    @SerializedName("author")
    public String author;
    @SerializedName("brief")
    public String brief;
    @SerializedName("read_num")
    public String readNum;
    @SerializedName("wechat_url")
    public String wechatUrl;
    @SerializedName("url")
    public String url;
    @SerializedName("create_time")
    public String createTime;
    @SerializedName("update_time")
    public String updateTime;
    @SerializedName("content")
    public String content;

}
