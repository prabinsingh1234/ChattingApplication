package com.quickblox.sample.videochat.java.api;




import com.quickblox.sample.videochat.java.data.CallDuration;
import com.quickblox.sample.videochat.java.data.Feedback;
import com.quickblox.sample.videochat.java.data.InsertRecharge;
import com.quickblox.sample.videochat.java.data.Otp;
import com.quickblox.sample.videochat.java.data.RechargeHistory;
import com.quickblox.sample.videochat.java.data.RechargePayments;
import com.quickblox.sample.videochat.java.data.Registeration;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface APIService {

    @GET("/active_interpreters/")
    Call<Otp> getInterpreters();

    @POST("/getOTP/")
    Call<Otp> getOtp(@Body RequestBody requestBody);

    @POST("/checkOTP/")
    Call<Otp> checkOTP(@Body RequestBody requestBody);

    @POST("/startCall/")
    Call<Otp> startCall(@Body RequestBody requestBody);



    @Multipart
    @POST("/registratio/")
    Call<Registeration> registrationDetails(@Part("phone") RequestBody phone,
                                            @Part("name") RequestBody name,
                                            @Part("email ") RequestBody email,
                                            @Part("dob") RequestBody dob,
                                            @Part("gender") RequestBody gender,
                                            @Part("sign_language") RequestBody sign_language,
                                            @Part("type") RequestBody type,
                                            @Part("city") RequestBody city,
                                            @Part MultipartBody.Part filePart
    );

    @Multipart
    @POST("uploadAttachment")
    Call<Otp> uploadAttachment(@Part MultipartBody.Part filePart);


    @POST("/getuserdetails")
    Call<Otp>getUserDetails(@Body RequestBody requestBody);

    @PUT("/updateuserdetails/{id}/")
    @Multipart
    Call<Otp>updateUserProfile(@Path("id") String id,
                               @Part("phone") RequestBody phone,
                               @Part("name") RequestBody name,
                               @Part("email ") RequestBody email,
                               @Part("dob") RequestBody dob,
                               @Part("gender") RequestBody gender,
                               @Part("sign_language") RequestBody sign_language,
                               @Part("type") RequestBody type,
                               @Part("city") RequestBody city,
                               @Part MultipartBody.Part filePart);

    @GET("getplans/")
    Call<List<RechargePayments>>getRechargePlans();

    @POST("/insertrecharge/")
    Call<InsertRecharge>insertRecharge(@Body RequestBody requestBody);

    @POST("/callduration/")
    Call<CallDuration>callDuration(@Body RequestBody requestBody);

    @POST("/feedbacklists/")
    Call<Feedback>callRating(@Body RequestBody requestBody);

    @POST("/rechargehistory/")
    Call<RechargeHistory>callRechargeHistory(@Body RequestBody requestBody);
}
