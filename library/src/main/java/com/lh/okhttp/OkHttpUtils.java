package com.lh.okhttp;

import com.lh.okhttp.builder.GetBuilder;
import com.lh.okhttp.builder.PostFormBuilder;
import com.lh.okhttp.builder.PostJsonBuilder;

/**
 * Created by lh on 2017/8/12.
 * 可以参照okhttp request类写Builder
 */

public class OkHttpUtils {

    public static GetBuilder get() {
        return new GetBuilder();
    }

    public static PostFormBuilder post() {
        return new PostFormBuilder();
    }

    public static PostJsonBuilder postJson() {
        return new PostJsonBuilder();
    }


}
