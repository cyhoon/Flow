package kr.hs.dgsw.flow.model.response;

import kr.hs.dgsw.flow.model.ResponseFormat;
import kr.hs.dgsw.flow.model.User;

public class UserResponseFormat extends ResponseFormat {
    private String token;
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
