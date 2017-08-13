package com.lh.okhttp.builder;

import com.lh.okhttp.request.OkHttpRequest;
import com.lh.okhttp.request.PostFormRequest;

import java.util.LinkedHashMap;

/**
 * Created by lh on 2017/8/13.
 */

public class PostFormBuilder extends OkHttpRequestBuilder<PostFormBuilder> {

    public PostFormBuilder addParams(String key, Object val) {
        if (this.params == null) {
            params = new LinkedHashMap<>();
        }
        params.put(key, val);
        return this;
    }

    public OkHttpRequest build() {
        return new PostFormRequest(url,params);
    }


}
