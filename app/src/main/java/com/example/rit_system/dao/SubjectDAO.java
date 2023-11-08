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
import com.example.rit_system.entities.Subject;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class SubjectDAO {
    private Context context;
    private static SQLiteDatabase database;

    public SubjectDAO(Context context) {
        this.context = context;
        this.database = (new Database(context)).getWritableDatabase();
    }

    @SuppressLint("Range")
    public ArrayList<Subject> getList() {
        ArrayList<Subject> subjectList = new ArrayList<>();
        String[] columns = {"id", "code", "name", "description", "startTime", "endTime", "classroom", "teacherName", "requirements", "courseLoad", "credits", "numberOfVacancies"};

        Cursor cursor = database.query("subjects", columns, null, null, null, null, null);

        while (cursor.moveToNext()) {
            Subject subject = new Subject();
            subject.setId(cursor.getInt(cursor.getColumnIndex("id")));
            subject.setCode(cursor.getString(cursor.getColumnIndex("code")));
            subject.setName(cursor.getString(cursor.getColumnIndex("name")));
            subject.setDescription(cursor.getString(cursor.getColumnIndex("description")));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                long startTimeNanos = cursor.getLong(cursor.getColumnIndex("startTime"));
                long endTimeNanos = cursor.getLong(cursor.getColumnIndex("endTime"));
                LocalTime startTime = LocalTime.ofNanoOfDay(startTimeNanos);
                LocalTime endTime = LocalTime.ofNanoOfDay(endTimeNanos);

                subject.setStartTime(startTime);
                subject.setEndTime(endTime);
            }

            subject.setClassroom(cursor.getString(cursor.getColumnIndex("classroom")));
            subject.setTeacherName(cursor.getString(cursor.getColumnIndex("teacherName")));
            subject.setRequirements(cursor.getString(cursor.getColumnIndex("requirements")));
            subject.setCourseLoad(cursor.getInt(cursor.getColumnIndex("courseLoad")));
            subject.setCredits(cursor.getInt(cursor.getColumnIndex("credits")));
            subject.setNumberOfVacancies(cursor.getInt(cursor.getColumnIndex("numberOfVacancies")));
            subjectList.add(subject);
        }

        return subjectList;
    }

    public boolean add(Subject subject) {
        ContentValues values = new ContentValues();
        values.put("code", subject.getCode());
        values.put("name", subject.getName());
        values.put("description", subject.getDescription());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            long startTimeNanos = subject.getStartTime().toNanoOfDay();
            long endTimeNanos = subject.getEndTime().toNanoOfDay();
            values.put("startTime", startTimeNanos);
            values.put("endTime", endTimeNanos);
        }

        values.put("classroom", subject.getClassroom());
        values.put("teacherName", subject.getTeacherName());
        values.put("requirements", subject.getRequirements());
        values.put("courseLoad", subject.getCourseLoad());
        values.put("credits", subject.getCredits());
        values.put("numberOfVacancies", subject.getNumberOfVacancies());

        try {
            long rowId = database.insert("subjects", null, values);
            if (rowId != -1) {
                Toast.makeText(context, "Disciplina Salva!", Toast.LENGTH_SHORT).show();
                return true;
            } else {
                Toast.makeText(context, "Erro ao salvar a disciplina.", Toast.LENGTH_SHORT).show();
                return false;
            }
        } catch (SQLException e) {
            Toast.makeText(context, "Erro: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public boolean update(Subject subject) {
        ContentValues values = new ContentValues();
        values.put("code", subject.getCode());
        values.put("name", subject.getName());
        values.put("description", subject.getDescription());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            values.put("startTime", subject.getStartTime().toNanoOfDay());
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            values.put("endTime", subject.getEndTime().toNanoOfDay());
        }
        values.put("classroom", subject.getClassroom());
        values.put("teacherName", subject.getTeacherName());
        values.put("requirements", subject.getRequirements());
        values.put("courseLoad", subject.getCourseLoad());
        values.put("credits", subject.getCredits());
        values.put("numberOfVacancies", subject.getNumberOfVacancies());

        String whereClause = "id = ?";
        String[] whereArgs = {String.valueOf(subject.getId())};

        try {
            int rowsAffected = database.update("subjects", values, whereClause, whereArgs);
            if (rowsAffected > 0) {
                Toast.makeText(context, "Disciplina atualizada!", Toast.LENGTH_SHORT).show();
                return true;
            } else {
                Toast.makeText(context, "Erro ao atualizar a disciplina.", Toast.LENGTH_SHORT).show();
                return false;
            }
        } catch (SQLException e) {
            Toast.makeText(context, "Erro: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    @SuppressLint("Range")
    public Subject get(String id) {
        String[] columns = {"id", "code", "name", "description", "startTime", "endTime", "classroom", "teacherName", "requirements", "courseLoad", "credits", "numberOfVacancies"};
        String selection = "id = ?";
        String[] selectionArgs = {String.valueOf(id)};

        Cursor cursor = database.query("subjects", columns, selection, selectionArgs, null, null, null);

        if (cursor.moveToFirst()) {
            Subject subject = new Subject();
            subject.setId(cursor.getInt(cursor.getColumnIndex("id")));
            subject.setCode(cursor.getString(cursor.getColumnIndex("code")));
            subject.setName(cursor.getString(cursor.getColumnIndex("name")));
            subject.setDescription(cursor.getString(cursor.getColumnIndex("description")));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                subject.setStartTime(LocalTime.parse(cursor.getString(cursor.getColumnIndex("startTime"))));
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                subject.setEndTime(LocalTime.parse(cursor.getString(cursor.getColumnIndex("endTime"))));
            }
            subject.setClassroom(cursor.getString(cursor.getColumnIndex("classroom")));
            subject.setTeacherName(cursor.getString(cursor.getColumnIndex("teacherName")));
            subject.setRequirements(cursor.getString(cursor.getColumnIndex("requirements")));
            subject.setCourseLoad(cursor.getInt(cursor.getColumnIndex("courseLoad")));
            subject.setCredits(cursor.getInt(cursor.getColumnIndex("credits")));
            subject.setNumberOfVacancies(cursor.getInt(cursor.getColumnIndex("numberOfVacancies")));
            return subject;
        } else {
            return null;
        }
    }

    public boolean delete(String id) {
        String whereClause = "id = ?";
        String[] whereArgs = {String.valueOf(id)};

        try {
            int rowsDeleted = database.delete("subjects", whereClause, whereArgs);
            if (rowsDeleted > 0) {
                Toast.makeText(context, "Disciplina deletada com sucesso!", Toast.LENGTH_SHORT).show();
                return true;
            } else {
                Toast.makeText(context, "Erro ao deletar a disciplina.", Toast.LENGTH_SHORT).show();
                return false;
            }
        } catch (SQLException e) {
            Toast.makeText(context, "Erro: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            return false;
        }
    }
}
