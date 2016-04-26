package reader.simple.com.simple_reader.viewInterface;

import reader.simple.com.simple_reader.domain.PageInfo;

/**
 * ==================================================
 * 项目名称：Simple_Reader
 * 创建人：wangxiaolong
 * 创建时间：16/4/2 下午4:47
 * 修改时间：16/4/2 下午4:47
 * 修改备注：
 * Version：
 * ==================================================
 */
public interface MainView {

    void doOnThrow(Throwable throwable);

    void hideRefresh();

    void clearListData();

    void setLoadMore(PageInfo pageInfo);

    void initCacheData(PageInfo pageInfo);
    void showRefreshLoading();


}
