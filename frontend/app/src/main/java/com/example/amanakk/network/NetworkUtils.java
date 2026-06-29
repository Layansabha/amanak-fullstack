package com.example.amanakk.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkUtils {

    private static final String BASE_URL = "http://10.0.2.2:8081/api/";
    private static Retrofit retrofit = null;

    public static PostApi getApi() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(PostApi.class);
    }
}
