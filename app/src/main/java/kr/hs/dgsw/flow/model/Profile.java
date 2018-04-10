package kr.hs.dgsw.flow.model;

/**
 * My Profile DATA Model
 */

public class Profile {

    private  String token;
    private String email;
    private String name;
    private String gender;
    private String mobile;
    private int auth;
    private int grade;
    private int my_class;
    private int my_class_number;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

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

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public int getMy_class() {
        return my_class;
    }

    public void setMy_class(int my_class) {
        this.my_class = my_class;
    }

    public int getMy_class_number() {
        return my_class_number;
    }

    public void setMy_class_number(int my_class_number) {
        this.my_class_number = my_class_number;
    }
}
