package echang.pxd.netease.presenter.ipresenter;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.InputStream;

import echang.pxd.netease.http.AsyncHttp;
import echang.pxd.netease.http.Constants;
import echang.pxd.netease.presenter.ipresenter.ISplashPresenter;
import okhttp3.Response;


/**
 * @Description
 * @Author 彭孝东
 * @QQ 932056657
 */
public class SplashPresenter extends ISplashPresenter {
    private ISplashView mSplashView;

    public SplashPresenter(ISplashView mSplashView) {
        super(mSplashView);
        this.mSplashView = mSplashView;
    }

    @Override
    public void loadAdvertisement() {
        // 显示网络加载界面
        mSplashView.showLoading();

        loadFromUrl();
    }

    @Override
    public String getUrl() {
        return Constants.Url.ads_url;
    }

    public void loadFromUrl() {
        AsyncHttp.getInstance().get(getUrl(), new AsyncHttp.OnHttpFinishListener() {
            @Override
            public void onSuccess(Response response) {
                InputStream is = response.body().byteStream();
                Bitmap bitmap = BitmapFactory.decodeStream(is);
                if (bitmap != null) {
                    mSplashView.onLoadingAdSuccess(bitmap);
                }else{
                    mSplashView.onLoadingAdFailure("解析数据失败");
                }
                System.out.println("下载成功");
            }

            @Override
            public void onFailure(IOException e) {
                mSplashView.onLoadingAdFailure("下载失败");
                e.printStackTrace();
                System.out.println("下载失败");
            }
        });
    }
}
