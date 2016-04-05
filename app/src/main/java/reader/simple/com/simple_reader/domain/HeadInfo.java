package reader.simple.com.simple_reader.domain;

import com.google.gson.annotations.SerializedName;

/**
 * ==================================================
 * 项目名称：Simple_Reader
 * 创建人：wangxiaolong
 * 创建时间：16/4/5 下午4:12
 * 修改时间：16/4/5 下午4:12
 * 修改备注：
 * Version：
 * ==================================================
 */
public class HeadInfo {
    @SerializedName("code")
    public int code;
    @SerializedName("message")
    public String message;
    @SerializedName("has_more")
    public boolean hasMore;
}
