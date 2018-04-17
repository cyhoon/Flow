package kr.hs.dgsw.flow.model.request;

import com.google.gson.annotations.SerializedName;

public class UserSignUp {
    @SerializedName("email")
    private String email;

    @SerializedName("pw")
    private String pw;

    @SerializedName("name")
    private String name;

    @SerializedName("gender")
    private String gender;

    @SerializedName("mobile")
    private String mobile;

    @SerializedName("class_idx")
    private String classIdx;

    @SerializedName("class_number")
    private String classNumber;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getClassIdx() {
        return classIdx;
    }

    public void setClassIdx(String classIdx) {
        this.classIdx = classIdx;
    }

    public String getClassNumber() {
        return classNumber;
    }

    public void setClassNumber(String classNumber) {
        this.classNumber = classNumber;
    }
}
