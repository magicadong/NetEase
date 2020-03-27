package echang.pxd.netease.presenter.ipresenter;

import android.graphics.Bitmap;

import echang.pxd.netease.base.BaseView;

/**
 * @Description
 * @Author 彭孝东
 * @QQ 932056657
 */
public abstract class ISplashPresenter {
    private BaseView mBaseView;

    public ISplashPresenter(ISplashView mBaseView) {
        this.mBaseView = mBaseView;
    }

    public abstract void loadAdvertisement();
    public abstract String getUrl();

    public interface ISplashView extends BaseView{
        void onLoadingAdSuccess(Bitmap bitmap);
        void onLoadingAdFailure(String error);
    }
}
