package com.android.okhttp;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.okhttp.Bean.Now;
import com.lh.okhttp.OkHttpUtils;
import com.lh.okhttp.callback.FileCallback;
import com.lh.okhttp.callback.GenericsCallback;
import com.lh.okhttp.callback.StringCallback;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.File;

import io.reactivex.functions.Consumer;

/**
 * API接口来自和风天气，感谢：https://www.kancloud.cn/hefengyun/weather/222344
 * API说明文档：https://www.heweather.com/documents/api/v5/scenic
 */
public class MainActivity extends AppCompatActivity {

    private static final String API_KEY = "5d5fc651748d45898d50d4f204e7defb";
    private static final String BASE_URL = "https://free-api.heweather.com/v5/";
    private static final String CITY = "CN101020100";//上海

    // 实况天气
    private static final String NOW = "now";
    // 未来每小时预报
    private static final String HOURLY = "hourly";

    TextView textResult;
    private RxPermissions mRxPermissions;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textResult = (TextView) findViewById(R.id.textResult);

        mRxPermissions = new RxPermissions(this);
    }

    public void testGet(View view) {
        OkHttpUtils.get()
                .url(BASE_URL.concat(NOW))
                .addHeader("app_type","android")
                .addParams("city", CITY)
                .addParams("key", API_KEY)
                .build()
                .enqueue(new GenericsCallback<Now>() {
                    @Override
                    public void onSuccess(Now now) {
                        textResult.setText("now = "+now.getHeWeather5().get(0).getBasic().getCnty());
                    }

                    @Override
                    public void onError(int code) {

                    }
                });
    }

    public void testGetFile(View view) {
        // 申请权限
        mRxPermissions
                .request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(@NonNull Boolean granted) throws Exception {
                        if (granted) { // Always true pre-M
                            Log.i("TAG","Environment.getDownloadCacheDirectory() = "+MainActivity.this.getExternalCacheDir().getAbsolutePath());
                            OkHttpUtils.get()
                                    .url("https://ucan.25pp.com/Wandoujia_web_seo_baidu_homepage.apk")
                                    .build()
                                    .enqueue(new FileCallback(MainActivity.this.getExternalCacheDir().getAbsolutePath(),
                                            "Wandoujia_web_seo_baidu_homepage.apk") {

                                        @Override
                                        public void onSuccess(File file) {
                                            textResult.setText("下载成功... ");
                                        }

                                        @Override
                                        public void onError(int code) {

                                        }

                                        @Override
                                        public void inProgress(float progress) {
                                            super.inProgress(progress);
                                            textResult.setText("progress:" + progress);
                                        }
                                    });
                        } else {
                            Toast.makeText(MainActivity.this, "没有SdCard权限!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    public void testPost(View view) {
        OkHttpUtils.post()
                .url(BASE_URL.concat(HOURLY))
                .addParams("city", CITY)
                .addParams("key", API_KEY)
                .build()
                .enqueue(new StringCallback(){
                    @Override
                    public void onSuccess(String s) {
                        textResult.setText(s);
                    }

                    @Override
                    public void onError(int code) {

                    }
                });
    }

}
