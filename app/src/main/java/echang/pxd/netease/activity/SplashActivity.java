package echang.pxd.netease.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import echang.pxd.netease.R;
import echang.pxd.netease.base.BaseActivity;
import echang.pxd.netease.presenter.ipresenter.ISplashPresenter;
import echang.pxd.netease.ui.splash.JumpView;
import echang.pxd.netease.presenter.ipresenter.SplashPresenter;

/**
 * @Description
 * @Author 彭孝东
 * @QQ 932056657
 */
public class SplashActivity extends BaseActivity implements ISplashPresenter.ISplashView {
    private ImageView mImageView;
    private SplashPresenter mPresenter;
    private JumpView mJumpView;

    @Override
    protected void initData() {
        
    }

    @Override
    protected void initView() {
        mImageView = obtainView(R.id.iv_splash_ad);
        mPresenter = new SplashPresenter(this);
        mPresenter.loadAdvertisement();

        mJumpView = obtainView(R.id.jv_splash_jump);
    }

    @Override
    protected void setListener() {
        mJumpView.setOnJumpClickListener(new JumpView.OnJumpButtonClickListener() {
            @Override
            public void onJumpClicked() {
                MainActivity.switchActivity(SplashActivity.this,null,true);
            }

            @Override
            public void onTimeOver() {
                MainActivity.switchActivity(SplashActivity.this,null,true);

            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    public boolean shouldFullScreen() {
        return true;
    }

    @Override
    public void onLoadingAdSuccess(Bitmap bitmap) {
        mImageView.setImageBitmap(bitmap);
    }

    @Override
    public void onLoadingAdFailure(String error) {
        showToast(error);
    }

    @Override
    public void showLoading() {
        mImageView.setImageResource(R.drawable.ad);
    }

    @Override
    public void dismissLoading() {

    }

    @Override
    public void showMessage(String msg) {
    }

    @Override
    public Context getContext() {
        return SplashActivity.this;
    }
}


















