package echang.pxd.netease.http;

import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * @Description
 * @Author 彭孝东
 * @QQ 932056657
 */
public class AsyncHttp {
    private static AsyncHttp instance;
    private OkHttpClient client;
    private Handler mHandler;
    private OnHttpFinishListener mListener;

    private static final int REQUEST_SUCCESS = 1;
    private static final int REQUEST_FAILURE = 2;

    private AsyncHttp(){
        client = new OkHttpClient();
        mHandler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message msg) {
                if (msg.what == REQUEST_SUCCESS){
                    mListener.onSuccess((Response) msg.obj);
                }else{
                    mListener.onFailure((IOException) msg.obj);
                }
                return true;
            }
        });
    }

    public static AsyncHttp getInstance(){
        if (instance == null) {
            synchronized (AsyncHttp.class) {
                if (instance == null)
                    instance = new AsyncHttp();
            }
        }
        return instance;
    }

    /**
     * get请求
     * @param url
     * @param listener
     */
    public void get(String url, OnHttpFinishListener listener) {
        get(url,null,listener);
    }

    /**
     * get请求 需要拼接参数map到url后面
     * @param url
     * @param map
     * @param listener
     */
    public void get(String url, HashMap<String, String> map, OnHttpFinishListener listener) {
        mListener = listener;
        url += getParams(map);

        Request request = new Request.Builder()
                .get()
                .url(url)
                .build();

        requestData(request);
    }

    /**
     * 普通的post请求 请求体数据在map中 不能上传文件
     * @param url
     * @param map
     * @param listener
     */
    public void post(String url, HashMap<String,String> map, OnHttpFinishListener listener) {
        mListener = listener;
        FormBody body = postParams(map);
        RequestBody requestBody = body;
        Request request = new Request.Builder()
                .post(body)
                .url(url)
                .build();
        requestData(request);
    }

    /**
     * 统一请求数据
     * @param request
     */
    private void requestData(Request request) {
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                Message message = mHandler.obtainMessage();
                message.what = REQUEST_SUCCESS;
                message.obj = response;
                mHandler.sendMessage(message);
            }

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Message message = mHandler.obtainMessage();
                message.what = REQUEST_FAILURE;
                message.obj = e;
                mHandler.sendMessage(message);
            }
        });
    }

    /**
     * 拼接get请求的参数
     * @param map
     * @return
     */
    private String getParams(HashMap<String,String> map){
        if (map != null) {
            StringBuffer buffer = new StringBuffer();
            buffer.append("?");
            for (HashMap.Entry<String,String> entry: map.entrySet()){
                buffer.append(entry.getKey());
                buffer.append("=");
                buffer.append(entry.getValue());
                buffer.append("&");
            }
            buffer.deleteCharAt(buffer.length()-1);
            return buffer.toString();
        }
        return "";
    }

    /**
     * 将map转化为 FormBody或者RequestBody
     * @param map
     * @return
     */
    private FormBody postParams(HashMap<String,String> map){
        if (map != null){
            FormBody.Builder builder = new FormBody.Builder();
            for (HashMap.Entry<String,String> entry : map.entrySet()){
                builder.add(entry.getKey(),entry.getValue());
            }
            return builder.build();
        }
        return null;
    }

    /**
     * 接口，统一返回数据
     */
    public interface  OnHttpFinishListener{
        void onSuccess(Response response);
        void onFailure(IOException e);
    }

}


















