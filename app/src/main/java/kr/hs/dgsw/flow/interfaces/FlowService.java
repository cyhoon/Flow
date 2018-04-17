package kr.hs.dgsw.flow.interfaces;

import org.json.JSONObject;

import kr.hs.dgsw.flow.model.ResponseFormat;
import kr.hs.dgsw.flow.model.request.UserSignIn;
import kr.hs.dgsw.flow.model.request.UserSignUp;
import kr.hs.dgsw.flow.model.response.UserResponseFormat;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by ihell on 2018-04-10.
 */

public interface FlowService {
    @Headers({"Accept: application/json"})
    @GET("/test")
    Call<JSONObject> getTest();

    @GET("users/{user}/repos")
    Call<JSONObject> getUserRepositories(@Path("user") String userName);

    @POST("/auth/signup")
    Call<ResponseFormat> signUp(@Body UserSignUp userSignUp);

    @POST("/auth/signin")
    Call<ResponseFormat> signIn(@Body UserSignIn userSignIn);
}
