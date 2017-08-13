package com.lh.okhttp.builder;

import com.lh.okhttp.request.OkHttpRequest;
import com.lh.okhttp.request.PostJsonRequest;

import java.util.LinkedHashMap;

/**
 * Created by lh on 2017/8/13.
 */

public class PostJsonBuilder extends OkHttpRequestBuilder<PostJsonBuilder> {

    public PostJsonBuilder addParams(String key, Object val) {
        if (this.params == null) {
            params = new LinkedHashMap<>();
        }
        params.put(key, val);
        return this;
    }

    public OkHttpRequest build() {
        return new PostJsonRequest(url, params);
    }


}
