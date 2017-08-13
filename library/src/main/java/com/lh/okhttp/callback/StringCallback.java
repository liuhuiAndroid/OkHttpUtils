package com.lh.okhttp.callback;

import java.io.IOException;

import okhttp3.Response;

/**
 * Created by lh on 2017/8/13.
 */

public abstract class StringCallback extends BaseCallback<String> {

    @Override
    public String parseNetworkResponse(Response response) throws IOException {
        return response.body().string();
    }

}
