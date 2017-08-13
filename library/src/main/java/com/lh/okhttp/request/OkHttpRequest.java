package com.lh.okhttp.request;

import com.lh.okhttp.OkHttpManager;
import com.lh.okhttp.callback.BaseCallback;

import org.json.JSONException;

import java.util.Map;

import okhttp3.Request;

/**
 * Created by lh on 2017/8/13.
 */

public abstract class OkHttpRequest {

    protected String url;
    protected Request request;

    public void setRequest(Request request) {
        this.request = request;
    }

    protected OkHttpRequest(String url, Map<String, Object> params) {
        this.url = url;
        initBuilder();
    }

    private void initBuilder() {
        builder.url(url);
    }

    protected Request.Builder builder = new Request.Builder();

    protected abstract Request buildRequest() throws JSONException;

    public Request generateRequest() {
        Request request = buildRequest();
        return request;
    }

    public void enqueue(BaseCallback callback) {
        OkHttpManager.getInstance().request(generateRequest(),callback);
    }


}
