package com.lh.okhttp.builder;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by lh on 2017/8/13.
 */

public abstract class OkHttpRequestBuilder<T extends OkHttpRequestBuilder> {

    protected String url;
    protected Map<String, Object> params;
    protected Map<String, String> headers;

    public T url(String url) {
        this.url = url;
        return (T) this;
    }

    public T addHeader(String key, String val) {
        if (this.headers == null) {
            headers = new LinkedHashMap<>();
        }
        headers.put(key, val);
        return (T) this;
    }

}
