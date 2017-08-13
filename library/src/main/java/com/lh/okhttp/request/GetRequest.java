package com.lh.okhttp.request;

import java.util.Map;

import okhttp3.Request;

/**
 * Created by lh on 2017/8/13.
 */

public class GetRequest extends OkHttpRequest{


    public GetRequest(String url, Map<String, Object> params) {
        super(url, params);
    }

    @Override
    public Request buildRequest() {
        return builder.get().build();
    }

}
