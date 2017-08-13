package com.lh.okhttp.request;

import com.lh.okhttp.builder.PostFormBuilder;

import java.io.UnsupportedEncodingException;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by lh on 2017/8/13.
 */

public class PostFormRequest extends OkHttpRequest {

    private Map<String, Object> mParams;
    private List<PostFormBuilder.FileInput> mFiles;

    public PostFormRequest(String url, Map<String, Object> params, Map<String, String> headers,
                           List<PostFormBuilder.FileInput> files) {
        super(url, params, headers);
        mParams = params;
        mFiles = files;
    }

    @Override
    protected Request buildRequest() {
        if (mFiles == null || mFiles.isEmpty()) {
            FormBody.Builder formBodyBuilder = new FormBody.Builder();
            addParams(formBodyBuilder);
            FormBody formBody = formBodyBuilder.build();
            return builder.post(formBody).build();
        } else {
            MultipartBody.Builder multipartBodyBuilder = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM);
            addParams(multipartBodyBuilder);

            for (int i = 0; i < mFiles.size(); i++) {
                PostFormBuilder.FileInput fileInput = mFiles.get(i);
                RequestBody fileBody = RequestBody.create(MediaType.parse(guessMimeType(fileInput.filename)), fileInput.file);
                multipartBodyBuilder.addFormDataPart(fileInput.key, fileInput.filename, fileBody);
            }
            MultipartBody multipartBody = multipartBodyBuilder.build();
            return builder.post(multipartBody).build();
        }

    }

    private String guessMimeType(String path) {
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        String contentTypeFor = null;
        try {
            contentTypeFor = fileNameMap.getContentTypeFor(URLEncoder.encode(path, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (contentTypeFor == null) {
            contentTypeFor = "application/octet-stream";
        }
        return contentTypeFor;
    }

    private void addParams(FormBody.Builder builder) {
        if (mParams != null) {
            for (String key : mParams.keySet()) {
                builder.add(key, mParams.get(key).toString());
            }
        }
    }

    private void addParams(MultipartBody.Builder builder) {
        if (mParams != null && !mParams.isEmpty()) {
            for (String key : mParams.keySet()) {
                builder.addPart(Headers.of("Content-Disposition", "form-data; name=\"" + key + "\""),
                        RequestBody.create(null, mParams.get(key).toString()));
            }
        }
    }
}
