package com.lh.okhttp.builder;

import com.lh.okhttp.request.OkHttpRequest;
import com.lh.okhttp.request.PostFormRequest;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by lh on 2017/8/13.
 */

public class PostFormBuilder extends OkHttpRequestBuilder<PostFormBuilder> {

    private List<FileInput> files = new ArrayList<>();

    public PostFormBuilder addParams(String key, Object val) {
        if (this.params == null) {
            params = new LinkedHashMap<>();
        }
        params.put(key, val);
        return this;
    }

    public PostFormBuilder addFile(String name, String filename, File file) {
        files.add(new FileInput(name, filename, file));
        return this;
    }


    public OkHttpRequest build() {
        return new PostFormRequest(url, params, headers);
    }


    public static class FileInput {
        public String key;
        public String filename;
        public File file;

        public FileInput(String name, String filename, File file) {
            this.key = name;
            this.filename = filename;
            this.file = file;
        }

        @Override
        public String toString() {
            return "FileInput{" +
                    "key='" + key + '\'' +
                    ", filename='" + filename + '\'' +
                    ", file=" + file +
                    '}';
        }
    }

}
