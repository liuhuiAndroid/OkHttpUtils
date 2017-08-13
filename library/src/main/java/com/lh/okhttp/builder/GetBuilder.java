package com.lh.okhttp.builder;

import android.net.Uri;

import com.lh.okhttp.request.GetRequest;
import com.lh.okhttp.request.OkHttpRequest;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by lh on 2017/8/13.
 */

public class GetBuilder extends OkHttpRequestBuilder<GetBuilder> {

    public GetBuilder addParams(String key, Object val) {
        if (this.params == null) {
            params = new LinkedHashMap<>();
        }
        params.put(key, val);
        return this;
    }

    protected String appendParams(String url, Map<String, Object> params) {
        if (url == null || params == null || params.isEmpty()) {
            return url;
        }
        Uri.Builder builder = Uri.parse(url).buildUpon();
        Set<String> keys = params.keySet();
        Iterator<String> iterator = keys.iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            builder.appendQueryParameter(key, params.get(key).toString());
        }
        return builder.build().toString();
    }

    public OkHttpRequest build() {
        if (params != null) {
            url = appendParams(url, params);
        }
        GetRequest getRequest = new GetRequest(url,params,headers);
        getRequest.setRequest(getRequest.buildRequest());
        return getRequest;
    }

}
