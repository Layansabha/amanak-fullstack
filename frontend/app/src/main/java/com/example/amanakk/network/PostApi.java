package com.example.amanakk.network;

import com.example.amanakk.Post;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PostApi {

    @GET("v1/blogs/latest")
    Call<List<Post>> getLatestPosts(@Query("lang") String language, @Query("limit") int limit);
}
