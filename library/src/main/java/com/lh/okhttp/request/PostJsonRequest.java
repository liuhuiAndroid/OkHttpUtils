package com.lh.okhttp.request;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by lh on 2017/8/13.
 */

public class PostJsonRequest extends OkHttpRequest {

    private Map<String, Object> mParams;

    public PostJsonRequest(String url, Map<String, Object> params, Map<String, String> headers) {
        super(url, params, headers);
        mParams = params;
    }

    @Override
    protected Request buildRequest() {
        JSONObject jsonObject = new JSONObject();
        for (String key : mParams.keySet()) {
            try {
                jsonObject.put(key, mParams.get(key).toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        String json = jsonObject.toString();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), json);
        return builder.post(requestBody).build();
    }

}
