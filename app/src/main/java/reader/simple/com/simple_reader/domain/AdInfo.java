package reader.simple.com.simple_reader.domain;

import com.google.gson.annotations.SerializedName;

/**
 * ==================================================
 * 项目名称：Simple_Reader
 * 创建人：wangxiaolong
 * 创建时间：16/4/5 下午4:06
 * 修改时间：16/4/5 下午4:06
 * 修改备注：
 * Version：
 * ==================================================
 */
public class AdInfo {
    @SerializedName("welcome_ad")
    public String welcomeAd;
    @SerializedName("article_ad")
    public String articleAd;
    @SerializedName("css_version_code")
    public String cssVersionCode;

}
