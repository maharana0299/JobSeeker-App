package com.innerworkindia.jobseeker.API;

import com.innerworkindia.jobseeker.Model.BodyApplyIntern;
import com.innerworkindia.jobseeker.Model.BodyApplyJob;
import com.innerworkindia.jobseeker.Model.BodyGetJobs;
import com.innerworkindia.jobseeker.Model.BodyGetUserApplications;
import com.innerworkindia.jobseeker.Model.BodyGetUserProfile;
import com.innerworkindia.jobseeker.Model.BodyLoginUser;
import com.innerworkindia.jobseeker.Model.BodySendPassword;
import com.innerworkindia.jobseeker.Model.BodySignUpUser;
import com.innerworkindia.jobseeker.Model.ResponseApplyIntern;
import com.innerworkindia.jobseeker.Model.ResponseApplyJob;
import com.innerworkindia.jobseeker.Model.ResponseGetJobs;
import com.innerworkindia.jobseeker.Model.ResponseGetUserApplications;
import com.innerworkindia.jobseeker.Model.ResponseGetUserProfile;
import com.innerworkindia.jobseeker.Model.ResponseLoginUser;
import com.innerworkindia.jobseeker.Model.ResponseSendPassword;
import com.innerworkindia.jobseeker.Model.ResponseSignUpUser;
import com.innerworkindia.jobseeker.Model.ResponseUpdateUserProfile;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Url;

public interface LoginApi {
    @POST("sign_up_user.php")
    Call<ResponseSignUpUser> signUpUser(@Body BodySignUpUser bodySignUpUser);

    @POST("log_in_user.php")
    Call<ResponseLoginUser> logInUser(@Body BodyLoginUser bodyLoginUser);

    @POST("send_password")
    Call<ResponseSendPassword> sendPassword(@Body BodySendPassword bodySendPassword);

    @POST("user_profile")
    Call<ResponseGetUserProfile> getUserProfile(@Body BodyGetUserProfile bodyGetUserProfile);

    @POST("user_get_jobs")
    Call<ResponseGetJobs> getJobs(@Body BodyGetJobs bodyGetJobs);

    @POST("user_get_applications")
    Call<ResponseGetUserApplications> getUserApplications(@Body BodyGetUserApplications bodyGetUserApplications);

    @POST("user_apply_job")
    Call<ResponseApplyJob> applyJob(@Body BodyApplyJob bodyApplyJob);

    @POST("user_apply_intern")
    Call<ResponseApplyIntern> applyIntern(@Body BodyApplyIntern bodyApplyIntern);

    @Multipart
    @POST("user_update_profile.php")
    Call<ResponseUpdateUserProfile> updateUserProfile(
            @Part("descriptions") RequestBody descriptions,
            @Part MultipartBody.Part filePhoto,
            @Part MultipartBody.Part fileResume
    );

    @GET
    Call<ResponseBody> downloadFileWithDynamicUrlSync(@Url String fileUrl);
}
