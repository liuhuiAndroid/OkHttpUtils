package com.lh.okhttp.builder;

import java.util.Map;

/**
 * Created by lh on 2017/8/13.
 */

public abstract class OkHttpRequestBuilder<T extends OkHttpRequestBuilder> {

    protected String url;
    protected Map<String, Object> params;

    public T url(String url) {
        this.url = url;
        return (T) this;
    }

}
