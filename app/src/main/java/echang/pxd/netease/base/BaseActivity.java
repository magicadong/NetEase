package echang.pxd.netease.base;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import echang.pxd.netease.activity.MainActivity;

import static android.widget.Toast.LENGTH_SHORT;

public abstract class BaseActivity extends AppCompatActivity {
    private Handler mHandler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (shouldFullScreen()){
            setFullScreen();
        }

        if (getLayoutId() != 0){
            setContentView(getLayoutId());
        }

        initView();
        initData();
        setListener();
    }

    protected abstract void setListener();

    protected abstract void initData();

    protected abstract void initView();

    public abstract int getLayoutId();

    public abstract boolean shouldFullScreen();

    public void setFullScreen(){
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    public <T extends View>T obtainView(int resId){
        return (T)findViewById(resId);
    }

    public void showToast(final String msg){
        if (TextUtils.isEmpty(msg)){
            return;
        }

        mHandler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(BaseActivity.this,msg, LENGTH_SHORT).show();
            }
        });

    }

    /**
     * 页面跳转
     * context 跳转过来的界面 A->B   BaseActivity就是A
     * bundle 需要传递的参数
     * shouldFinish 是否需要关闭之前的界面
     */

    public static void switchActivity(BaseActivity context, Bundle bundle,Boolean shouldFinish){
        Intent intent = new Intent(context, MainActivity.class);
        if (bundle != null){
            intent.putExtra("bundle",bundle);
        }
        context.startActivity(intent);

        if (shouldFinish == true){
            context.finish();
        }
    }
}
