package com.example.amanakk.network;

import com.example.amanakk.Case;
import com.example.amanakk.Message; // Importing the correct Message class
import com.example.amanakk.Post;
import com.example.amanakk.PostAR;
import com.example.amanakk.SuccessStory;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    @POST("v1/users/signup")
    Call<SignUpResponse> registerUser(@Body UserAccountsRequest userRequest);

    @POST("v1/users/verifyId")
    Call<SignUpResponse> verifyIdNumber(@Body IdNumbersRequest idRequest);

    @POST("v1/users/login")
    Call<LoginResponse> loginUser(@Body LoginRequest loginRequest);

    @POST("v1/users/checkUser")
    Call<UserCheckResponse> checkUser(@Body UserCheckRequest request);

    @GET("v1/users/profile")
    Call<LoginResponse> getUserProfile(@Query("identifier") String identifier);

    @POST("v1/otp/verify")
    Call<OtpResponse> verifyOtp(@Body OtpRequest otpRequest);

    @POST("v1/otp/resend")
    Call<OtpResponse> resendOtp(@Query("phoneNumber") String phoneNumber);

    @GET("v1/posts")
    Call<List<Post>> getPosts(@Query("language") String language, @Query("limit") int limit);

    @GET("v1/postsAR")
    Call<List<PostAR>> getPostsAR(@Query("language") String language);

    @POST("v1/cases")
    Call<Case> submitCase(@Body Case caseInstance);

    @GET("v1/cases/{id}")
    Call<Case> getCaseById(@Path("id") Long id);

    @Multipart
    @POST("v1/files/upload")
    Call<ResponseBody> uploadFile(@Part MultipartBody.Part file);

    @GET("v1/success-stories")
    Call<List<SuccessStory>> getSuccessStories(@Query("language") String language);
    @GET("v1/messages/inbox")
    Call<List<Message>> getUserMessages(@Query("receiverNationalId") String receiverNationalId);

    // Get chat messages between sender and receiver
    @GET("v1/messages/chat")
    Call<List<Message>> getChatMessages(@Query("senderId") String senderId, @Query("receiverId") String receiverId); // Ensure both senderId and receiverId are passed

    // Send a new message
    @POST("v1/messages/send")
    Call<Message> sendMessage(@Body Message message);

    // Delete a message
    @DELETE("v1/messages/{messageId}")
    Call<Void> deleteMessage(@Path("messageId") Long messageId);

    // Get unsolved messages
    @GET("v1/messages/unsolved")
    Call<List<Message>> getUnsolvedMessages();




}
