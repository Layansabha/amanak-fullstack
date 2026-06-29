package com.example.amanakk;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface ApiClientInterface {
    @Multipart
    @POST("/api/files/upload")
    Call<ResponseBody> uploadFile(@Part MultipartBody.Part file);

    @POST("/api/v1/cases")
    Call<Case> submitCase(@Body Case caseInstance);

    @GET("/api/v1/cases/{id}")
    Call<Case> getCaseById(@Path("id") Long id);
}
