package reader.simple.com.simple_reader.ui.webView;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;


/**
 * 项目名称：AndroidMaterialDesignToolbar
 * 类描述：
 * 创建人：wangxiaolong
 * 创建时间：15/11/1 下午6:01
 * 修改人：wangxiaolong
 * 修改时间：15/11/1 下午6:01
 * 修改备注：
 */
public class ArticleWebView extends WebView {
    public ArticleWebView(Context context) {
        this(context, null);
    }

    public ArticleWebView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ArticleWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        getSettings().setSupportZoom(false);
        getSettings().setBuiltInZoomControls(false);
        getSettings().setDomStorageEnabled(true);
        getSettings().setLoadsImagesAutomatically(true);
        getSettings().setLoadWithOverviewMode(true);
        getSettings().setUseWideViewPort(true);
//        WebSettings webSetting = getSettings();
//        webSetting.setAllowFileAccess(true);
//        webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
//        webSetting.setSupportZoom(true);
//        webSetting.setBuiltInZoomControls(true);
//        webSetting.setUseWideViewPort(true);
//        webSetting.setSupportMultipleWindows(false);
//        webSetting.setAppCacheEnabled(true);
//        webSetting.setDatabaseEnabled(true);
//        webSetting.setDomStorageEnabled(true);
//        webSetting.setJavaScriptEnabled(true);
//        webSetting.setGeolocationEnabled(true);
//        webSetting.setAppCacheMaxSize(Long.MAX_VALUE);
        // webSetting.setPageCacheCapacity(IX5WebSettings.DEFAULT_CACHE_CAPACITY);
//        webSetting.setPluginState(WebSettings.PluginState.ON_DEMAND);
//        webSetting.setRenderPriority(WebSettings.RenderPriority.HIGH);

    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    @Override
    public void destroy() {
        removeAllViews();
        freeMemory();
        clearHistory();
        destroy();
        super.destroy();
    }
}
