package kr.hs.dgsw.flow.database;

/**
 * SQLite ADB로 확인하는 법
 * 레퍼런스 주소: http://petra.tistory.com/718
 *
 * 0. adb root 로 관리자 권한 얻기
 * 1. adb shell 실행
 * 2. run-as '안드로이드 패키지명' (example. run-as 'kr.hs.dgsw.flow')
 * 3. sqlite3 flow.db 확인
 */

public class DBManager{
    String getMyProfile() {
        return myProfile;
    }

    private final String myProfile = "" +
            "CREATE TABLE MY_PROFILE" +
            "(email TEXT PRIMARY KEY," +
            "token TEXT, " +
            "name TEXT, gender TEXT, mobile TEXT," +
            "auth INTEGER, " + "grade INTEGER, " +
            "class_room INTEGER," + "class_number INTEGER" +
            ");";
}
