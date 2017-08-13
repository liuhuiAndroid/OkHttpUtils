package com.lh.okhttp;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.lh.okhttp.callback.BaseCallback;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by lh on 2017/8/12.
 */

public class OkHttpManager {

    private volatile static OkHttpManager instance;
    private OkHttpClient mOkHttpClient;
    private Handler mHandler;

    public OkHttpManager() {
        initOkHttp();
        mHandler = new Handler();
        Log.i("TAG", "test init handler thread : " + Thread.currentThread().getName());
    }

    public Handler getHandler() {
        if (mHandler == null) {
            mHandler = new Handler(Looper.getMainLooper());
        }
        return mHandler;
    }

    private void initOkHttp() {
        mOkHttpClient = new OkHttpClient().newBuilder()
                .readTimeout(30 * 1000, TimeUnit.SECONDS)
                .connectTimeout(30 * 1000, TimeUnit.SECONDS)
                .build();
    }

    /**
     * Returns singleton class instance
     */
    public static OkHttpManager getInstance() {
        if (instance == null) {
            synchronized (OkHttpManager.class) {
                if (instance == null) {
                    instance = new OkHttpManager();
                }
            }
        }
        return instance;
    }

    public void request(Request request, final BaseCallback callback) {
        if (callback == null) {
            throw new NullPointerException("callback == null");
        }
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                sendOnFailureMessage(callback, call, e);
            }

            @Override
            public void onResponse(Call call, Response response) {
                if (response.isSuccessful()) {
                    String result = null;
                    try {
                        Object object = callback.parseNetworkResponse(response);
                        sendOnSuccessMessage(callback,object);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    // response是流？用完需要关掉
                    if (response.body() != null) {
                        response.body().close();
                    }
                } else {
                    sendOnErrorMessage(callback, response.code());
                }
            }
        });
    }

    /**
     * 异步转同步???
     */
    private void sendOnFailureMessage(final BaseCallback callback, final Call call, final IOException e) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                callback.onFailure(call, e);
            }
        });
    }

    private void sendOnErrorMessage(final BaseCallback callback, final int code) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                callback.onError(code);
            }
        });
    }

    private void sendOnSuccessMessage(final BaseCallback callback, final Object object) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                callback.onSuccess(object);
            }
        });
    }
}
