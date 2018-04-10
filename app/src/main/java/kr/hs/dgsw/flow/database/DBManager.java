package kr.hs.dgsw.flow.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
                    "auth INTEGER, " + "grade INTEGER, " + "my_class INTEGER," +
                    "my_class_number INTEGER" +
                ");";
        db.execSQL(myProfileTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
