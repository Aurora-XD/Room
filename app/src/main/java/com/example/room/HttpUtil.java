package com.example.room;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HttpUtil {

    public static Response sendHttpRequest(String path) throws IOException {
        OkHttpClient okHttpClient = new OkHttpClient();
        return okHttpClient.newCall(new Request.Builder().url(path).build()).execute();
    }
}
