package com.example.rit_system.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.widget.Toast;

import com.example.rit_system.Database;
import com.example.rit_system.entities.Teacher;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class TeacherDAO {
    private Context context;
    private static SQLiteDatabase database;

    public TeacherDAO(Context context) {
        this.context = context;
        this.database = (new Database(context)).getWritableDatabase();
    }

    @SuppressLint("Range")
    public ArrayList<Teacher> getList() {
        ArrayList<Teacher> teacherList = new ArrayList<>();
        String[] columns = {"id", "name", "birthDay", "identificationNumber", "email", "phone", "trainingArea", "yearsOfExperience"};

        Cursor cursor = database.query("teachers", columns, null, null, null, null, null);

        while (cursor.moveToNext()) {
            Teacher teacher = new Teacher();
            teacher.setId(cursor.getInt(cursor.getColumnIndex("id")));
            teacher.setName(cursor.getString(cursor.getColumnIndex("name")));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                teacher.setBirthDay(LocalDate.parse(cursor.getString(cursor.getColumnIndex("birthDay"))));
            }
            teacher.setIndentificatorNumber(cursor.getString(cursor.getColumnIndex("identificationNumber")));
            teacher.setEmail(cursor.getString(cursor.getColumnIndex("email")));
            teacher.setPhone(cursor.getString(cursor.getColumnIndex("phone")));
            teacher.setTrainingArea(cursor.getString(cursor.getColumnIndex("trainingArea")));
            teacher.setYearsOfExperience(cursor.getInt(cursor.getColumnIndex("yearsOfExperience")));
            teacherList.add(teacher);
        }

        return teacherList;
    }

    public boolean add(Teacher teacher) {
        ContentValues values = new ContentValues();
        values.put("name", teacher.getName());
        values.put("birthDay", teacher.getBirthDay().toString());
        values.put("identificationNumber", teacher.getIndentificatorNumber());
        values.put("email", teacher.getEmail());
        values.put("phone", teacher.getPhone());
        values.put("trainingArea", teacher.getTrainingArea());
        values.put("yearsOfExperience", teacher.getYearsOfExperience());

        try {
            long rowId = database.insert("teachers", null, values);
            if (rowId != -1) {
                Toast.makeText(context, "Professor Salvo!", Toast.LENGTH_SHORT).show();
                return true;
            } else {
                Toast.makeText(context, "Erro ao salvar o professor.", Toast.LENGTH_SHORT).show();
                return false;
            }
        } catch (SQLException e) {
            Toast.makeText(context, "Erro: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public boolean update(Teacher teacher) {
        ContentValues values = new ContentValues();
        values.put("name", teacher.getName());
        values.put("birthDay", teacher.getBirthDay().toString());
        values.put("identificationNumber", teacher.getIndentificatorNumber());
        values.put("email", teacher.getEmail());
        values.put("phone", teacher.getPhone());
        values.put("trainingArea", teacher.getTrainingArea());
        values.put("yearsOfExperience", teacher.getYearsOfExperience());

        String whereClause = "id = ?";
        String[] whereArgs = {String.valueOf(teacher.getId())};

        try {
            int rowsAffected = database.update("teachers", values, whereClause, whereArgs);
            if (rowsAffected > 0) {
                Toast.makeText(context, "Professor atualizado!", Toast.LENGTH_SHORT).show();
                return true;
            } else {
                Toast.makeText(context, "Erro ao atualizar o professor.", Toast.LENGTH_SHORT).show();
                return false;
            }
        } catch (SQLException e) {
            Toast.makeText(context, "Erro: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    @SuppressLint("Range")
    public Teacher get(String id) {
        String[] columns = {"id", "name", "birthDay", "identificationNumber", "email", "phone", "trainingArea", "yearsOfExperience"};
        String selection = "id = ?";
        String[] selectionArgs = {String.valueOf(id)};

        Cursor cursor = database.query("teachers", columns, selection, selectionArgs, null, null, null);

        if (cursor.moveToFirst()) {
            Teacher teacher = new Teacher();
            teacher.setId(cursor.getInt(cursor.getColumnIndex("id")));
            teacher.setName(cursor.getString(cursor.getColumnIndex("name")));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                teacher.setBirthDay(LocalDate.parse(cursor.getString(cursor.getColumnIndex("birthDay"))));
            }
            teacher.setIndentificatorNumber(cursor.getString(cursor.getColumnIndex("identificationNumber")));
            teacher.setEmail(cursor.getString(cursor.getColumnIndex("email")));
            teacher.setPhone(cursor.getString(cursor.getColumnIndex("phone")));
            teacher.setTrainingArea(cursor.getString(cursor.getColumnIndex("trainingArea")));
            teacher.setYearsOfExperience(cursor.getInt(cursor.getColumnIndex("yearsOfExperience")));
            return teacher;
        } else {
            return null;
        }
    }

    public boolean delete(String id) {
        String whereClause = "id = ?";
        String[] whereArgs = {String.valueOf(id)};

        try {
            int rowsDeleted = database.delete("teachers", whereClause, whereArgs);
            if (rowsDeleted > 0) {
                Toast.makeText(context, "Professor deletado com sucesso!", Toast.LENGTH_SHORT).show();
                return true;
            } else {
                Toast.makeText(context, "Erro ao deletar o professor.", Toast.LENGTH_SHORT).show();
                return false;
            }
        } catch (SQLException e) {
            Toast.makeText(context, "Erro: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            return false;
        }
    }
}
