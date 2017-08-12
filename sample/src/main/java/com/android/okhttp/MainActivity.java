package com.android.okhttp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.lh.okhttp.OkHttpUtils;
import com.lh.okhttp.callback.BaseCallback;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textResult = (TextView) findViewById(R.id.textResult);
    }

    public void testGet(View view){
        OkHttpUtils.newBuilder()
                .url(BASE_URL.concat(NOW))
                .get()
                .addParam("city",CITY)
                .addParam("key",API_KEY)
                .build()
                .enqueue(new BaseCallback<String>() {
                    @Override
                    public void onSuccess(String s) {
                        textResult.setText(s);
                    }
                });
    }

    public void testPost(View view){
        OkHttpUtils.newBuilder()
                .url(BASE_URL.concat(HOURLY))
                .post()
                .addParam("city",CITY)
                .addParam("key",API_KEY)
                .build()
                .enqueue(new BaseCallback<String>() {
                    @Override
                    public void onSuccess(String s) {
                        textResult.setText(s);
                    }
                });
    }

}
