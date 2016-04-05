package reader.simple.com.simple_reader.viewInterface;


/**
 * ==================================================
 * 项目名称：Simple_Reader
 * 创建人：wangxiaolong
 * 创建时间：16/4/5 下午6:42
 * 修改时间：16/4/5 下午6:42
 * 修改备注：
 * Version：
 * ==================================================
 */
public interface WebTextView {
    void getArticleInfo(String info);

    void showThrowMessage(String msg);

    void hideLoadingView();

    void showLoadingView();

}
