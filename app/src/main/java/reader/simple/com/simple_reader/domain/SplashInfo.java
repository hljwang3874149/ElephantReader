package reader.simple.com.simple_reader.domain;

import com.google.gson.annotations.SerializedName;

/**
 * ==================================================
 * 项目名称：Simple_Reader
 * 创建人：wangxiaolong
 * 创建时间：16/7/20 下午5:00
 * 备注：
 * Version：
 * ==================================================
 */
public class SplashInfo {
    @SerializedName("versionCode")
    public int versionCode;
    @SerializedName("splashPath")
    public String splashPath;

    @Override
    public String toString() {
        return "SplashInfo{" +
                "splashPath='" + splashPath + '\'' +
                ", versionCode=" + versionCode +
                '}';
    }
}
