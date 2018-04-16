package kr.hs.dgsw.flow.model;

public class User {
    private String email;
    private String name;
    private String gender;
    private String mobile;
    private String token;
    private int auth;
    private myClass myClass;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public int getAuth() {
        return auth;
    }

    public void setAuth(int auth) {
        this.auth = auth;
    }

    public kr.hs.dgsw.flow.model.myClass getMyClass() {
        return myClass;
    }

    public void setMyClass(kr.hs.dgsw.flow.model.myClass myClass) {
        this.myClass = myClass;
    }
}
