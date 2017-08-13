package com.lh.okhttp.callback;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Response;

/**
 * Created by lh on 2017/8/13.
 */

public abstract class GenericsCallback<T> extends BaseCallback<T> {

    private Gson mGson;

    @Override
    public T parseNetworkResponse(Response response) throws IOException {

        mGson = new Gson();
        Object object = mGson.fromJson(response.body().string(), mType);
        return (T) object;
    }
}
