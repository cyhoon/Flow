package kr.hs.dgsw.flow.interfaces;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
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
}
