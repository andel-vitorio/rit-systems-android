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
import com.example.rit_system.entities.UndergraduateStudent;

import java.time.LocalDate;
import java.util.ArrayList;

public class UndergraduateStudentDAO {
    private Context context;
    private static SQLiteDatabase database;

    public UndergraduateStudentDAO(Context context) {
        this.context = context;
        this.database = (new Database(context)).getWritableDatabase();
    }

    @SuppressLint("Range")
    public ArrayList<UndergraduateStudent> getUndergraduateStudents() {
        ArrayList<UndergraduateStudent> undergraduateStudents = new ArrayList<>();
        String[] columns = {
                "id",
                "name",
                "dateOfEntry",
                "registration",
                "email",
                "phoneNumber",
                "nameOfMentee",
                "status",
                "projectName",
                "typeOfOrientation"
        };

        Cursor cursor = database.query("undergraduate_students", columns, null, null, null, null, null);

        while (cursor.moveToNext()) {
            UndergraduateStudent student = new UndergraduateStudent();
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
            student.setProjectName(cursor.getString(cursor.getColumnIndex("projectName")));
            student.setTypeOfOrientation(cursor.getString(cursor.getColumnIndex("typeOfOrientation")));
            undergraduateStudents.add(student);
        }

        return undergraduateStudents;
    }

    public boolean addUndergraduateStudent(UndergraduateStudent student) {
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
        values.put("projectName", student.getProjectName());
        values.put("typeOfOrientation", student.getTypeOfOrientation());

        try {
            long rowId = database.insert("undergraduate_students", null, values);
            if (rowId != -1) {
                Toast.makeText(context, "Estudante de graduação salvo!", Toast.LENGTH_SHORT).show();
                return true;
            } else {
                Toast.makeText(context, "Erro ao salvar o estudante de graduação.", Toast.LENGTH_SHORT).show();
                return false;
            }
        } catch (SQLException e) {
            Toast.makeText(context, "Erro: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public boolean updateUndergraduateStudent(int id, UndergraduateStudent student) {
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
        values.put("projectName", student.getProjectName());
        values.put("typeOfOrientation", student.getTypeOfOrientation());

        String whereClause = "id = ?";
        String[] whereArgs = {String.valueOf(id)};

        try {
            int rowsAffected = database.update("undergraduate_students", values, whereClause, whereArgs);
            if (rowsAffected > 0) {
                Toast.makeText(context, "Estudante de graduação atualizado!", Toast.LENGTH_SHORT).show();
                return true;
            } else {
                Toast.makeText(context, "Erro ao atualizar o estudante de graduação.", Toast.LENGTH_SHORT).show();
                return false;
            }
        } catch (SQLException e) {
            Toast.makeText(context, "Erro: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    @SuppressLint("Range")
    public UndergraduateStudent getUndergraduateStudentById(int id) {
        String[] columns = {
                "id",
                "name",
                "dateOfEntry",
                "registration",
                "email",
                "phoneNumber",
                "nameOfMentee",
                "status",
                "projectName",
                "typeOfOrientation"
        };
        String selection = "id = ?";
        String[] selectionArgs = {String.valueOf(id)};

        Cursor cursor = database.query("undergraduate_students", columns, selection, selectionArgs, null, null, null);

        if (cursor.moveToFirst()) {
            UndergraduateStudent student = new UndergraduateStudent();
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
            student.setProjectName(cursor.getString(cursor.getColumnIndex("projectName")));
            student.setTypeOfOrientation(cursor.getString(cursor.getColumnIndex("typeOfOrientation")));
            return student;
        } else {
            return null;
        }
    }

    public boolean deleteUndergraduateStudentById(int id) {
        String whereClause = "id = ?";
        String[] whereArgs = {String.valueOf(id)};

        try {
            int rowsDeleted = database.delete("undergraduate_students", whereClause, whereArgs);
            if (rowsDeleted > 0) {
                Toast.makeText(context, "Estudante de graduação deletado com sucesso!", Toast.LENGTH_SHORT).show();
                return true;
            } else {
                Toast.makeText(context, "Erro ao deletar o estudante de graduação.", Toast.LENGTH_SHORT).show();
                return false;
            }
        } catch (SQLException e) {
            Toast.makeText(context, "Erro: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            return false;
        }
    }
}
