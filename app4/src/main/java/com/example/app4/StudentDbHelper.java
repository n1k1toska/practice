package com.example.app4;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;

public class StudentDbHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "university.db";
    private static final int DB_VERSION = 1;

    private static final String TABLE_STUDENTS = "students";
    private static final String COL_ID = "id";
    private static final String COL_FIO = "fio";
    private static final String COL_AGE = "age";
    private static final String COL_COURSE = "course";
    private static final String COL_GPA = "gpa";

    public StudentDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_STUDENTS + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_FIO + " TEXT, " +
                COL_AGE + " INTEGER, " +
                COL_COURSE + " INTEGER, " +
                COL_GPA + " REAL" + ")";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENTS);
        onCreate(db);
    }

    public boolean addStudent(Student student) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_FIO, student.fio);
        cv.put(COL_AGE, student.age);
        cv.put(COL_COURSE, student.course);
        cv.put(COL_GPA, student.gpa);

        long result = db.insert(TABLE_STUDENTS, null, cv);
        db.close();
        return result != -1;
    }

    public Student searchStudent(String fio) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_STUDENTS, null, COL_FIO + "=?",
                new String[]{fio}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            Student s = new Student(
                    cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COL_FIO)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(COL_AGE)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(COL_COURSE)),
                    cursor.getDouble(cursor.getColumnIndexOrThrow(COL_GPA))
            );
            cursor.close();
            db.close();
            return s;
        }
        if (cursor != null) cursor.close();
        db.close();
        return null;
    }

    public boolean updateStudent(Student student) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_FIO, student.fio);
        cv.put(COL_AGE, student.age);
        cv.put(COL_COURSE, student.course);
        cv.put(COL_GPA, student.gpa);

        int result = db.update(TABLE_STUDENTS, cv, COL_ID + "=?", new String[]{String.valueOf(student.id)});
        db.close();
        return result > 0;
    }

    public boolean deleteStudent(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_STUDENTS, COL_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
        return result > 0;
    }

    public List<Student> getAllStudents() {
        List<Student> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_STUDENTS, null);

        if (cursor.moveToFirst()) {
            do {
                list.add(new Student(
                        cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COL_FIO)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(COL_AGE)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(COL_COURSE)),
                        cursor.getDouble(cursor.getColumnIndexOrThrow(COL_GPA))
                ));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }
}