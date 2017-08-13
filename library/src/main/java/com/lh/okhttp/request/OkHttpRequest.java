package com.lh.okhttp.request;

import com.lh.okhttp.OkHttpManager;
import com.lh.okhttp.callback.BaseCallback;

import java.util.Map;

import okhttp3.Headers;
import okhttp3.Request;

/**
 * Created by lh on 2017/8/13.
 */

public abstract class OkHttpRequest {

    protected String url;
    private Map<String, String> headers;
    protected Request request;

    public void setRequest(Request request) {
        this.request = request;
    }

    protected OkHttpRequest(String url, Map<String, Object> params, Map<String, String> headers) {
        this.url = url;
        this.headers = headers;
        initBuilder();
    }

    private void initBuilder() {
        builder.url(url);
        appendHeaders();
    }

    protected Request.Builder builder = new Request.Builder();

    protected abstract Request buildRequest();

    public Request generateRequest() {
        Request request = buildRequest();
        return request;
    }

    public void enqueue(BaseCallback callback) {
        OkHttpManager.getInstance().request(generateRequest(), callback);
    }

    protected void appendHeaders() {
        Headers.Builder headerBuilder = new Headers.Builder();
        if (headers == null || headers.isEmpty())
            return;

        for (String key : headers.keySet()) {
            headerBuilder.add(key, headers.get(key));
        }
        builder.headers(headerBuilder.build());
    }

}
