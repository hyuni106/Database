package kr.co.tjeit.database.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by the on 2017-09-11.
 */

public class StudentDBManager {
//    DB도 하나의 파일
    static final String DB_STUDENT = "student.db";
//    학생 정보가 저장되는 표(Table) => Excel 의 시트
    static final String TABLE_STUDENT = "students";
//    DB구조가 변경될 때 버전화하여 기록, 앱을 업데이트해도 문제 발생 x
    static final int DB_VERSION = 1;

    Context mContext;

//    데이터베이스의 관리자의 경우엔 동시에 여러개의 객체가 생존해서 조흥ㄹ게 없음
//    데이터베이스 관리 시스템은 단 하나의 객체만 존재하도록 보장
//    이런 기법을 SingleTone 패턴이라고 함
//    프로그래밍의 디자인 패턴
    private static StudentDBManager mDBManager = null;
    private SQLiteDatabase mDB = null;

    public static StudentDBManager getInstance(Context context) {
        if (mDBManager == null) {
            mDBManager = new StudentDBManager(context);
        }

        return mDBManager;
    }

    private StudentDBManager(Context context) {
        mContext = context;
//        mDB 새로 만들거나 기존의 디비 체계를 받아옴
        mDB = mContext.openOrCreateDatabase(DB_STUDENT, Context.MODE_PRIVATE, null);

//         DB 내부에 저장될 실제 표 (테이블) 만드는 과정
        mDB.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_STUDENT + "(" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "stdNum TEXT, " +
                    "name TEXT, " +
                    "department TEXT, " +
                    "grade INTEGER" + ");");
    }

    public long insert(ContentValues addRowValue) {
        return mDB.insert(TABLE_STUDENT, null, addRowValue);
    }

    public Cursor query(String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
        return mDB.query(TABLE_STUDENT, columns, selection, selectionArgs, groupBy, having, orderBy);
    }

//    영문과 4학년 학생 받아오는 쿼리
    public Cursor getQuery00() {
        return mDB.rawQuery("SELECT name FROM " + TABLE_STUDENT + " WHERE department='영문' AND grade = 4;", null);
    }

    public Cursor getQuery01() {
        return mDB.rawQuery("SELECT department FROM " + TABLE_STUDENT + " WHERE name='박석영';", null);
    }

    public Cursor getQuery02() {
        return mDB.rawQuery("SELECT name, department FROM " + TABLE_STUDENT + " WHERE grade=1;", null);
    }

    public Cursor getQuery03() {
        return mDB.rawQuery("SELECT name, grade FROM " + TABLE_STUDENT + " WHERE department='기계';", null);
    }

    public Cursor getQuery04() {
        return mDB.rawQuery("SELECT stdNum FROM " + TABLE_STUDENT + " WHERE grade=2;", null);
    }
}
