package com.example.room;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HttpUtil {

    public static Response sendHttpRequest(String path) throws IOException {
        OkHttpClient okHttpClient = new OkHttpClient();
        return okHttpClient.newCall(new Request.Builder().url(path).build()).execute();
    }

    public static List<PersonInfo> getPersonInfo(String path) throws IOException {
        Response response = sendHttpRequest(path);
        String values = response.body().string();

        Gson gson = new Gson();
        Type type = new TypeToken<PersonInfoRes<List<PersonInfo>>>() {
        }.getType();

        PersonInfoRes<List<PersonInfo>> personInfoRes = gson.fromJson(values, type);
        return personInfoRes.getData();
    }
}
