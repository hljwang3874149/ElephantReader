package reader.simple.com.simple_reader;

import com.github.moduth.blockcanary.BlockCanaryContext;

/**
 * ==================================================
 * 项目名称：Simple_Reader
 * 创建人：wangxiaolong
 * 创建时间：16/4/1 下午12:24
 * 修改时间：16/4/1 下午12:24
 * 修改备注：
 * Version：
 * ==================================================
 */
public class AppBlockCanaryContext extends BlockCanaryContext {
    @Override
    public int getConfigBlockThreshold() {
        return 500;
    }

    // if set true, notification will be shown, else only write log file
    @Override
    public boolean isNeedDisplay() {
        return BuildConfig.DEBUG;
    }

    // path to save log file
    @Override
    public String getLogPath() {
        return "/blockcanary/performance";
    }
}
