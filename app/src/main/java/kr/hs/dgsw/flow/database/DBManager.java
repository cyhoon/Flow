package kr.hs.dgsw.flow.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import kr.hs.dgsw.flow.model.MyClass;
import kr.hs.dgsw.flow.model.User;
import kr.hs.dgsw.flow.model.response.UserResponseData;
import kr.hs.dgsw.flow.model.response.UserResponseFormat;

public class DBManager extends SQLiteOpenHelper {

    public DBManager(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // table 생성
        /**
         *   "email": "jeff@gmail.com",
             "name": "최영훈",
             "gender": "남성",
             "mobile": "01091947201",
             "auth": 2,
             "my_class": {
                 "grade": 3,
                 "class": 1,
                 "class_number": 19
             }
         * */
        String myProfileTable = "" +
                "CREATE TABLE MY_PROFILE" +
                    "(email TEXT PRIMARY KEY," +
                    "token TEXT, " +
                    "name TEXT, gender TEXT, mobile TEXT," +
                    "auth INTEGER, " + "grade INTEGER, " +
                    "class_room INTEGER," + "class_number INTEGER" +
                ");";
        db.execSQL(myProfileTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void saveMyProfile(UserResponseFormat userResponseFormat) {
        UserResponseData userResponseData = userResponseFormat.getData();
        User user = userResponseData.getUser();
        MyClass myClass = user.getMyClass();

        String email = user.getEmail();
        String token = userResponseData.getToken();
        String name = user.getName();
        String gender = user.getGender();
        String mobile = user.getMobile();
        int auth = user.getAuth();
        int grade = myClass.getGrade();
        int classRoom = myClass.getClassRoom();
        int classNumber = myClass.getClassNumber();

        String myProfile = "INSERT INTO MY_PROFILE VALUES('" +
                email + "', '"+token +"', '" +name+"', '"+gender+"', "+
                mobile+"', '"+auth+", '" + grade + ", '"+classRoom+"', '"+classNumber+
                ")";

        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(myProfile);
        db.close();
    }

    public UserResponseData getMyProfile() {
        SQLiteDatabase db = getWritableDatabase();
        UserResponseData userResponseData = new UserResponseData();
        User user = new User();
        MyClass myClass = new MyClass();

        String selectMyProfile = "SELECT * FROM MY_RPOFILE";
        Cursor cursor = db.rawQuery(selectMyProfile, null);

        cursor.moveToNext();

        user.setEmail(cursor.getString(0));

        userResponseData.setToken(cursor.getString(1));

        user.setName(cursor.getString(2));
        user.setGender(cursor.getString(3));
        user.setMobile(cursor.getString(4));
        user.setAuth(cursor.getInt(5));

        myClass.setGrade(cursor.getInt(6));
        myClass.setClassRoom(cursor.getInt(7));
        myClass.setClassNumber(cursor.getInt(8));

        user.setMyClass(myClass);
        userResponseData.setUser(user);

        return userResponseData;
    }

    public void updateMyToken(String token, String email) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE MY_RPOFILE SET token='"+token+"' WHERE email='"+email+"';");
        db.close();
    }

    public void deleteMyProfile() {
        SQLiteDatabase db = getWritableDatabase();

        db.execSQL("DELETE FROM MY_RPOFILE;");
        db.close();
    }
}
