package kr.hs.dgsw.flow.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import kr.hs.dgsw.flow.model.MyClass;
import kr.hs.dgsw.flow.model.User;
import kr.hs.dgsw.flow.model.response.UserResponseData;
import kr.hs.dgsw.flow.model.response.UserResponseFormat;

public class DatabaseHelper  extends SQLiteOpenHelper{
    private final DBManager dbManager = new DBManager();

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "flow.db";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(dbManager.getMyProfile());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + "MY_PROFILE");
        onCreate(db);
    }

    public void saveMyProfile(UserResponseFormat userResponseFormat) {
        UserResponseData userResponseData = userResponseFormat.getData();
        User user = userResponseData.getUser();
        MyClass myClass = user.getMyClass();

        SQLiteDatabase db = getWritableDatabase();

        String sql = "INSERT INTO MY_PROFILE values(?,?,?,?,?,?,?,?,?);";

        final SQLiteStatement insertStmt = db.compileStatement(sql);
        insertStmt.clearBindings();
        insertStmt.bindString(1, user.getEmail());
        insertStmt.bindString(2, userResponseData.getToken());
        insertStmt.bindString(3, user.getName());
        insertStmt.bindString(4, user.getGender());
        insertStmt.bindString(5, user.getMobile());
        insertStmt.bindLong(6, user.getAuth());
        insertStmt.bindLong(7, myClass.getGrade());
        insertStmt.bindLong(8, myClass.getClassRoom());
        insertStmt.bindLong(9, myClass.getClassNumber());
        insertStmt.executeInsert();

        db.close();
    }

    public UserResponseData getMyProfile() {
        UserResponseData userResponseData = new UserResponseData();
        User user = new User();
        MyClass myClass = new MyClass();


        final SQLiteDatabase db = getWritableDatabase();
        final String sql = "SELECT * FROM MY_PROFILE LIMIT 1";
        final Cursor cursor = db.rawQuery(sql, null);

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

        cursor.close();
        db.close();

        return userResponseData;
    }

    public void updateMyToken(String token, String email) {
        SQLiteDatabase db = getWritableDatabase();

        String sql = "UPDATE MY_PROFILE SET token=? WHERE email=?;";
        final SQLiteStatement updateStmt = db.compileStatement(sql);
        updateStmt.clearBindings();
        updateStmt.bindString(1, token);
        updateStmt.bindString(2, email);
        updateStmt.executeUpdateDelete();
        db.close();
    }

    public void deleteMyProfile() {
        final SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM MY_PROFILE;");
        db.close();
    }
}
