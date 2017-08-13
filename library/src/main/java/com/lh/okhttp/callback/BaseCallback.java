package com.lh.okhttp.callback;

import com.google.gson.internal.$Gson$Types;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by lh on 2017/8/12.
 */

public abstract class BaseCallback<T> {

    public Type mType;

    static Type getSuperclassTypeParameter(Class<?> subclass){
        Type superclass = subclass.getGenericSuperclass();
        if(superclass instanceof Class){
            return null;
        }
        ParameterizedType parameterizedType = (ParameterizedType) superclass;
        return $Gson$Types.canonicalize(parameterizedType.getActualTypeArguments()[0]);
    }

    public BaseCallback(){
        mType = getSuperclassTypeParameter(this.getClass());
    }

    public abstract void onSuccess(T t);

    public abstract void onError(int code);

    public void onFailure(Call call, IOException e){}

    public void inProgress(float progress) {}

    public abstract T  parseNetworkResponse(Response response) throws IOException;
}
