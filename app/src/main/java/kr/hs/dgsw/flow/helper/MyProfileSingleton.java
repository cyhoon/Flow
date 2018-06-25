package kr.hs.dgsw.flow.helper;

/**
 * Created by ihell on 2018-05-01.
 */

public class MyProfileSingleton {
    private static MyProfileSingleton myProfileInstance = null;

    private String token;
    private String email;
    private String name;

    private MyProfileSingleton() {}

    public static MyProfileSingleton getInstance(String token, String email, String name) {
        if (myProfileInstance == null) {
            myProfileInstance = new MyProfileSingleton();
            myProfileInstance.token = token;
            myProfileInstance.email = email;
            myProfileInstance.name = name;
        }

        return myProfileInstance;
    }
}