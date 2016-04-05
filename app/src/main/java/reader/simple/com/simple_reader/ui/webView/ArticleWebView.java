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
        getSettings().setJavaScriptEnabled(true);
        getSettings().setSupportZoom(true);
        getSettings().setBuiltInZoomControls(false);
        getSettings().setDomStorageEnabled(true);
        getSettings().setLoadsImagesAutomatically(true);
        getSettings().setLoadWithOverviewMode(true);
        getSettings().setUseWideViewPort(true);

    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        freeMemory();
        clearHistory();
        destroy();
    }
}
