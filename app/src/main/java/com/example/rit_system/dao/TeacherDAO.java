package com.example.rit_system.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.rit_system.Database;
import com.example.rit_system.entities.Teacher;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TeacherDAO {
    private SQLiteDatabase database;

    public TeacherDAO(Context context) {
        Database dbHelper = new Database(context);
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        if (database != null) {
            database.close();
        }
    }

    public long insertTeacher(Teacher teacher) {
        ContentValues values = new ContentValues();
        values.put("name", teacher.getName());
        values.put("birthDay", teacher.getBirthDay().toString()); // Assuming date format is "yyyy-MM-dd"
        values.put("indenticatorNumber", teacher.getIndentificatorNumber());
        values.put("email", teacher.getEmail());
        values.put("phone", teacher.getPhone());
        values.put("trainingArea", teacher.getTrainingArea());
        values.put("yearsOfExperience", teacher.getYearsOfExperience());

        return database.insert("teachers", null, values);
    }

    public int updateTeacher(Teacher teacher) {
        ContentValues values = new ContentValues();
        values.put("name", teacher.getName());
        values.put("birthDay", teacher.getBirthDay().toString());
        values.put("indenticatorNumber", teacher.getIndentificatorNumber());
        values.put("email", teacher.getEmail());
        values.put("phone", teacher.getPhone());
        values.put("trainingArea", teacher.getTrainingArea());
        values.put("yearsOfExperience", teacher.getYearsOfExperience());

        return database.update("teachers", values, "id = ?", new String[]{String.valueOf(teacher.getId())});
    }

    public void deleteTeacher(int teacherId) {
        database.delete("teachers", "id = ?", new String[]{String.valueOf(teacherId)});
    }

    public Teacher getTeacherById(int teacherId) {
        String[] columns = {"id", "name", "birthDay", "indenticatorNumber", "email", "phone", "trainingArea", "yearsOfExperience"};
        Cursor cursor = database.query("teachers", columns, "id = ?", new String[]{String.valueOf(teacherId)}, null, null, null);
        Teacher teacher = null;

        if (cursor != null && cursor.moveToFirst()) {
            teacher = cursorToTeacher(cursor);
            cursor.close();
        }

        return teacher;
    }

    public List<Teacher> getAllTeachers() {
        List<Teacher> teachers = new ArrayList<>();
        String[] columns = {"id", "name", "birthDay", "indenticatorNumber", "email", "phone", "trainingArea", "yearsOfExperience"};
        Cursor cursor = database.query("teachers", columns, null, null, null, null, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                Teacher teacher = cursorToTeacher(cursor);
                teachers.add(teacher);
            }
            cursor.close();
        }

        return teachers;
    }

    @SuppressLint("Range")
    private Teacher cursorToTeacher(Cursor cursor) {
        Teacher teacher = new Teacher();
        teacher.setId(cursor.getInt(cursor.getColumnIndex("id")));
        teacher.setName(cursor.getString(cursor.getColumnIndex("name")));

        String birthDayString = cursor.getString(cursor.getColumnIndex("birthDay"));
        LocalDate birthDay = null; // Parse date from string
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            birthDay = LocalDate.parse(birthDayString);
        }
        teacher.setBirthDay(birthDay);

        teacher.setIndentificatorNumber(cursor.getString(cursor.getColumnIndex("indenticatorNumber")));
        teacher.setEmail(cursor.getString(cursor.getColumnIndex("email")));
        teacher.setPhone(cursor.getString(cursor.getColumnIndex("phone")));
        teacher.setTrainingArea(cursor.getString(cursor.getColumnIndex("trainingArea")));
        teacher.setYearsOfExperience(cursor.getInt(cursor.getColumnIndex("yearsOfExperience")));

        return teacher;
    }
}
