package com.lh.okhttp.request;

import java.util.Map;

import okhttp3.FormBody;
import okhttp3.Request;

/**
 * Created by lh on 2017/8/13.
 */

public class PostFormRequest extends OkHttpRequest {

    private Map<String, Object> mParams;

    public PostFormRequest(String url, Map<String, Object> params) {
        super(url, params);
        mParams = params;
    }

    @Override
    protected Request buildRequest() {
        FormBody.Builder formBodyBuilder = new FormBody.Builder();
        for (String key : mParams.keySet()) {
            formBodyBuilder.add(key, mParams.get(key) == null ? "" : mParams.get(key).toString());
        }
        return builder.post(formBodyBuilder.build()).build();
    }

}
