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
import com.example.rit_system.entities.CoordinationActivity;

import java.time.LocalDate;
import java.util.ArrayList;

public class CoordinationActivityDAO {
    private Context context;
    private static SQLiteDatabase database;

    public CoordinationActivityDAO(Context context) {
        this.context = context;
        this.database = (new Database(context)).getWritableDatabase();
    }

    @SuppressLint("Range")
    public ArrayList<CoordinationActivity> getCoordinationActivities() {
        ArrayList<CoordinationActivity> coordinationActivities = new ArrayList<>();
        String[] columns = {
                "id",
                "activityTitle",
                "nameOfPersonResponsible",
                "startDate",
                "endDate",
                "priority",
                "status",
                "description"
        };

        Cursor cursor = database.query("coordination_activities", columns, null, null, null, null, null);

        while (cursor.moveToNext()) {
            CoordinationActivity activity = new CoordinationActivity();
            activity.setId(cursor.getInt(cursor.getColumnIndex("id")));
            activity.setActivityTitle(cursor.getString(cursor.getColumnIndex("activityTitle")));
            activity.setNameOfPersonResponsible(cursor.getString(cursor.getColumnIndex("nameOfPersonResponsible")));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                long startDateMillis = cursor.getLong(cursor.getColumnIndex("startDate"));
                long endDateMillis = cursor.getLong(cursor.getColumnIndex("endDate"));
                activity.setStartDate(LocalDate.ofEpochDay(startDateMillis / (24 * 60 * 60 * 1000)));
                activity.setEndDate(LocalDate.ofEpochDay(endDateMillis / (24 * 60 * 60 * 1000)));
            }
            activity.setPriority(cursor.getString(cursor.getColumnIndex("priority")));
            activity.setStatus(cursor.getString(cursor.getColumnIndex("status")));
            activity.setDescription(cursor.getString(cursor.getColumnIndex("description")));
            coordinationActivities.add(activity);
        }

        return coordinationActivities;
    }

    public boolean addCoordinationActivity(CoordinationActivity activity) {
        ContentValues values = new ContentValues();
        values.put("activityTitle", activity.getActivityTitle());
        values.put("nameOfPersonResponsible", activity.getNameOfPersonResponsible());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            values.put("startDate", activity.getStartDate().toEpochDay() * (24 * 60 * 60 * 1000));
            values.put("endDate", activity.getEndDate().toEpochDay() * (24 * 60 * 60 * 1000));
        }
        values.put("priority", activity.getPriority());
        values.put("status", activity.getStatus());
        values.put("description", activity.getDescription());

        try {
            long rowId = database.insert("coordination_activities", null, values);
            if (rowId != -1) {
                Toast.makeText(context, "Atividade de coordenação salva!", Toast.LENGTH_SHORT).show();
                return true;
            } else {
                Toast.makeText(context, "Erro ao salvar a atividade de coordenação.", Toast.LENGTH_SHORT).show();
                return false;
            }
        } catch (SQLException e) {
            Toast.makeText(context, "Erro: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public boolean updateCoordinationActivity(int id, CoordinationActivity activity) {
        ContentValues values = new ContentValues();
        values.put("activityTitle", activity.getActivityTitle());
        values.put("nameOfPersonResponsible", activity.getNameOfPersonResponsible());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            values.put("startDate", activity.getStartDate().toEpochDay() * (24 * 60 * 60 * 1000));
            values.put("endDate", activity.getEndDate().toEpochDay() * (24 * 60 * 60 * 1000));
        }
        values.put("priority", activity.getPriority());
        values.put("status", activity.getStatus());
        values.put("description", activity.getDescription());

        String whereClause = "id = ?";
        String[] whereArgs = {String.valueOf(id)};

        try {
            int rowsAffected = database.update("coordination_activities", values, whereClause, whereArgs);
            if (rowsAffected > 0) {
                Toast.makeText(context, "Atividade de coordenação atualizada!", Toast.LENGTH_SHORT).show();
                return true;
            } else {
                Toast.makeText(context, "Erro ao atualizar a atividade de coordenação.", Toast.LENGTH_SHORT).show();
                return false;
            }
        } catch (SQLException e) {
            Toast.makeText(context, "Erro: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    @SuppressLint("Range")
    public CoordinationActivity getCoordinationActivityById(int id) {
        String[] columns = {
                "id",
                "activityTitle",
                "nameOfPersonResponsible",
                "startDate",
                "endDate",
                "priority",
                "status",
                "description"
        };
        String selection = "id = ?";
        String[] selectionArgs = {String.valueOf(id)};

        Cursor cursor = database.query("coordination_activities", columns, selection, selectionArgs, null, null, null);

        if (cursor.moveToFirst()) {
            CoordinationActivity activity = new CoordinationActivity();
            activity.setId(cursor.getInt(cursor.getColumnIndex("id")));
            activity.setActivityTitle(cursor.getString(cursor.getColumnIndex("activityTitle")));
            activity.setNameOfPersonResponsible(cursor.getString(cursor.getColumnIndex("nameOfPersonResponsible")));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                long startDateMillis = cursor.getLong(cursor.getColumnIndex("startDate"));
                long endDateMillis = cursor.getLong(cursor.getColumnIndex("endDate"));
                activity.setStartDate(LocalDate.ofEpochDay(startDateMillis / (24 * 60 * 60 * 1000)));
                activity.setEndDate(LocalDate.ofEpochDay(endDateMillis / (24 * 60 * 60 * 1000)));
            }
            activity.setPriority(cursor.getString(cursor.getColumnIndex("priority")));
            activity.setStatus(cursor.getString(cursor.getColumnIndex("status")));
            activity.setDescription(cursor.getString(cursor.getColumnIndex("description")));
            return activity;
        } else {
            return null;
        }
    }

    public boolean deleteCoordinationActivityById(int id) {
        String whereClause = "id = ?";
        String[] whereArgs = {String.valueOf(id)};

        try {
            int rowsDeleted = database.delete("coordination_activities", whereClause, whereArgs);
            if (rowsDeleted > 0) {
                Toast.makeText(context, "Atividade de coordenação deletada com sucesso!", Toast.LENGTH_SHORT).show();
                return true;
            } else {
                Toast.makeText(context, "Erro ao deletar a atividade de coordenação.", Toast.LENGTH_SHORT).show();
                return false;
            }
        } catch (SQLException e) {
            Toast.makeText(context, "Erro: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            return false;
        }
    }
}
