package kr.hs.dgsw.flow.model.response;

import com.google.gson.annotations.SerializedName;

import kr.hs.dgsw.flow.model.ResponseFormat;
import kr.hs.dgsw.flow.model.User;

public class UserResponseFormat extends ResponseFormat {
    @SerializedName("data")
    private UserResponseData data;

    public UserResponseData getData() {
        return data;
    }

    public void setData(UserResponseData data) {
        this.data = data;
    }
}

class UserResponseData {
    @SerializedName("token")
    private String token;

    @SerializedName("user")
    private User user;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}