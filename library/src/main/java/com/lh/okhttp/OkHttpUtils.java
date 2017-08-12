package com.lh.okhttp;

import android.net.Uri;

import com.lh.okhttp.bean.RequestParam;
import com.lh.okhttp.callback.BaseCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by lh on 2017/8/12.
 */

public class OkHttpUtils {

    private Builder mBuilder;

    public OkHttpUtils(Builder builder) {
        this.mBuilder = builder;
    }

    public void enqueue(BaseCallback callback) {
        OkHttpManager.getInstance().request(this,callback);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Request buildRequest() {
        Request.Builder builder = new Request.Builder();
        if (mBuilder.method.equals("GET")) {
            builder.get();

            builder.url(buildGetRequestParam());
        } else if (mBuilder.method.equals("POST")) {
            try {
                builder.post(buildRequestBody());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            builder.url(mBuilder.url);
        }
        return builder.build();
    }

    private RequestBody buildRequestBody() throws JSONException {
        if (mBuilder.isJsonParam) {
            JSONObject jsonObject = new JSONObject();
            for (RequestParam p : mBuilder.mParams) {
                jsonObject.put(p.getKey(), p.getObj());
            }
            String json = jsonObject.toString();
            return RequestBody.create(MediaType.parse("application/json"), json);
        } else {
            FormBody.Builder builder = new FormBody.Builder();
            for (RequestParam p : mBuilder.mParams) {
                builder.add(p.getKey(), p.getObj() == null ? "" : p.getObj().toString());
            }
            return builder.build();
        }
    }

    /**
     * GET方法，url可能传参，需要拼接
     * @return
     */
    private String buildGetRequestParam() {
        if (mBuilder.mParams.size() <= 0) {
            return this.mBuilder.url;
        }
        Uri.Builder builder = Uri.parse(mBuilder.url).buildUpon();
        for (RequestParam p : mBuilder.mParams) {
            builder.appendQueryParameter(p.getKey(),
                    p.getObj() == null ? "" : p.getObj().toString());
        }
        String url = builder.build().toString();
        return url;
    }

    // 直接参照okhttp request类
    public static class Builder {
        String url;
        String method;
        boolean isJsonParam; // 默认不写是false
        List<RequestParam> mParams;

        public Builder() {
            this.method = "GET";
        }

        public OkHttpUtils build() {
            return new OkHttpUtils(this);
        }

        public Builder url(String url) {
            if (url == null)
                throw new NullPointerException("url == null");
            this.url = url;
            return this;
        }

        public Builder get() {
            method = "GET";
            return this;
        }

        /**
         * Form 表单
         *
         * @return
         */
        public Builder post() {
            method = "POST";
            return this;
        }

        /**
         * JSON 参数
         *
         * @return
         */
        public Builder json() {
            isJsonParam = true;
            return post();
        }

        public Builder addParam(String key, Object value) {
            if (mParams == null) {
                //PASS: android中ArrayList效率不高???
                mParams = new ArrayList<>();
            }
            mParams.add(new RequestParam(key, value));
            return this;
        }

    }

}
