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
import com.example.rit_system.entities.GraduateStudent;

import java.time.LocalDate;
import java.util.ArrayList;

public class GraduateStudentDAO {
    private Context context;
    private static SQLiteDatabase database;

    public GraduateStudentDAO(Context context) {
        this.context = context;
        this.database = (new Database(context)).getWritableDatabase();
    }

    @SuppressLint("Range")
    public ArrayList<GraduateStudent> getGraduateStudents() {
        ArrayList<GraduateStudent> graduateStudents = new ArrayList<>();
        String[] columns = {
                "id",
                "name",
                "dateOfEntry",
                "registration",
                "email",
                "phoneNumber",
                "nameOfMentee",
                "status",
                "graduateProgram",
                "researchTitle",
                "defenseDate"
        };

        Cursor cursor = database.query("graduate_students", columns, null, null, null, null, null);

        while (cursor.moveToNext()) {
            GraduateStudent student = new GraduateStudent();
            student.setId(cursor.getInt(cursor.getColumnIndex("id")));
            student.setName(cursor.getString(cursor.getColumnIndex("name")));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                long dateOfEntryMillis = cursor.getLong(cursor.getColumnIndex("dateOfEntry"));
                student.setDateOfEntry(LocalDate.ofEpochDay(dateOfEntryMillis / (24 * 60 * 60 * 1000)));
            }
            student.setRegistration(cursor.getString(cursor.getColumnIndex("registration")));
            student.setEmail(cursor.getString(cursor.getColumnIndex("email")));
            student.setPhoneNumber(cursor.getString(cursor.getColumnIndex("phoneNumber")));
            student.setNameOfMentee(cursor.getString(cursor.getColumnIndex("nameOfMentee")));
            student.setStatus(cursor.getString(cursor.getColumnIndex("status")));
            student.setGraduateProgram(cursor.getString(cursor.getColumnIndex("graduateProgram")));
            student.setResearchTitle(cursor.getString(cursor.getColumnIndex("researchTitle")));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                long defenseDateMillis = cursor.getLong(cursor.getColumnIndex("defenseDate"));
                student.setDefenseDate(LocalDate.ofEpochDay(defenseDateMillis / (24 * 60 * 60 * 1000)));
            }
            graduateStudents.add(student);
        }

        return graduateStudents;
    }

    public boolean addGraduateStudent(GraduateStudent student) {
        ContentValues values = new ContentValues();
        values.put("name", student.getName());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            values.put("dateOfEntry", student.getDateOfEntry().toEpochDay() * (24 * 60 * 60 * 1000));
        }
        values.put("registration", student.getRegistration());
        values.put("email", student.getEmail());
        values.put("phoneNumber", student.getPhoneNumber());
        values.put("nameOfMentee", student.getNameOfMentee());
        values.put("status", student.getStatus());
        values.put("graduateProgram", student.getGraduateProgram());
        values.put("researchTitle", student.getResearchTitle());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            values.put("defenseDate", student.getDefenseDate().toEpochDay() * (24 * 60 * 60 * 1000));
        }

        try {
            long rowId = database.insert("graduate_students", null, values);
            if (rowId != -1) {
                Toast.makeText(context, "Estudante de pós-graduação salvo!", Toast.LENGTH_SHORT).show();
                return true;
            } else {
                Toast.makeText(context, "Erro ao salvar o estudante de pós-graduação.", Toast.LENGTH_SHORT).show();
                return false;
            }
        } catch (SQLException e) {
            Toast.makeText(context, "Erro: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public boolean updateGraduateStudent(int id, GraduateStudent student) {
        ContentValues values = new ContentValues();
        values.put("name", student.getName());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            values.put("dateOfEntry", student.getDateOfEntry().toEpochDay() * (24 * 60 * 60 * 1000));
        }
        values.put("registration", student.getRegistration());
        values.put("email", student.getEmail());
        values.put("phoneNumber", student.getPhoneNumber());
        values.put("nameOfMentee", student.getNameOfMentee());
        values.put("status", student.getStatus());
        values.put("graduateProgram", student.getGraduateProgram());
        values.put("researchTitle", student.getResearchTitle());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            values.put("defenseDate", student.getDefenseDate().toEpochDay() * (24 * 60 * 60 * 1000));
        }

        String whereClause = "id = ?";
        String[] whereArgs = {String.valueOf(id)};

        try {
            int rowsAffected = database.update("graduate_students", values, whereClause, whereArgs);
            if (rowsAffected > 0) {
                Toast.makeText(context, "Estudante de pós-graduação atualizado!", Toast.LENGTH_SHORT).show();
                return true;
            } else {
                Toast.makeText(context, "Erro ao atualizar o estudante de pós-graduação.", Toast.LENGTH_SHORT).show();
                return false;
            }
        } catch (SQLException e) {
            Toast.makeText(context, "Erro: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    @SuppressLint("Range")
    public GraduateStudent getGraduateStudentById(int id) {
        String[] columns = {
                "id",
                "name",
                "dateOfEntry",
                "registration",
                "email",
                "phoneNumber",
                "nameOfMentee",
                "status",
                "graduateProgram",
                "researchTitle",
                "defenseDate"
        };
        String selection = "id = ?";
        String[] selectionArgs = {String.valueOf(id)};

        Cursor cursor = database.query("graduate_students", columns, selection, selectionArgs, null, null, null);

        if (cursor.moveToFirst()) {
            GraduateStudent student = new GraduateStudent();
            student.setId(cursor.getInt(cursor.getColumnIndex("id")));
            student.setName(cursor.getString(cursor.getColumnIndex("name")));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                long dateOfEntryMillis = cursor.getLong(cursor.getColumnIndex("dateOfEntry"));
                student.setDateOfEntry(LocalDate.ofEpochDay(dateOfEntryMillis / (24 * 60 * 60 * 1000)));
            }
            student.setRegistration(cursor.getString(cursor.getColumnIndex("registration")));
            student.setEmail(cursor.getString(cursor.getColumnIndex("email")));
            student.setPhoneNumber(cursor.getString(cursor.getColumnIndex("phoneNumber")));
            student.setNameOfMentee(cursor.getString(cursor.getColumnIndex("nameOfMentee")));
            student.setStatus(cursor.getString(cursor.getColumnIndex("status")));
            student.setGraduateProgram(cursor.getString(cursor.getColumnIndex("graduateProgram")));
            student.setResearchTitle(cursor.getString(cursor.getColumnIndex("researchTitle")));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                long defenseDateMillis = cursor.getLong(cursor.getColumnIndex("defenseDate"));
                student.setDefenseDate(LocalDate.ofEpochDay(defenseDateMillis / (24 * 60 * 60 * 1000)));
            }
            return student;
        } else {
            return null;
        }
    }

    public boolean deleteGraduateStudentById(int id) {
        String whereClause = "id = ?";
        String[] whereArgs = {String.valueOf(id)};

        try {
            int rowsDeleted = database.delete("graduate_students", whereClause, whereArgs);
            if (rowsDeleted > 0) {
                Toast.makeText(context, "Estudante de pós-graduação deletado com sucesso!", Toast.LENGTH_SHORT).show();
                return true;
            } else {
                Toast.makeText(context, "Erro ao deletar o estudante de pós-graduação.", Toast.LENGTH_SHORT).show();
                return false;
            }
        } catch (SQLException e) {
            Toast.makeText(context, "Erro: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            return false;
        }
    }
}
