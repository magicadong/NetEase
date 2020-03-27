package echang.pxd.netease.base;

import android.content.Context;

/**
 * @Description
 * @Author 彭孝东
 * @QQ 932056657
 */
public interface BaseView {
    /**
     * 网络加载或耗时加载时界面显示
     */
    void showLoading();

    /**
     * 网络加载或耗时加载完成时界面显示
     */
    void dismissLoading();

    /**
     * 消息提示，如Toast，Dialog等
     */
    void showMessage(String msg);

    /**
     * 获取Context
     */
    Context getContext();
}
