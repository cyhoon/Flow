package kr.hs.dgsw.flow.model.request;

import com.google.gson.annotations.SerializedName;

public class UserSignIn {
    @SerializedName("email")
    private String email;

    @SerializedName("pw")
    private String pw;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }
}
