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
import com.example.rit_system.entities.Paper;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;

public class PaperDAO {
    private Context context;
    private SQLiteDatabase database;

    public PaperDAO(Context context) {
        this.context = context;
        this.database = (new Database(context)).getWritableDatabase();
    }

    @SuppressLint("Range")
    public ArrayList<Paper> getPapers() {
        ArrayList<Paper> papers = new ArrayList<>();
        String[] columns = {
                "id",
                "title",
                "authors",
                "publicationDate",
                "keywords",
                "description",
                "category",
                "url"
        };

        Cursor cursor = database.query("papers", columns, null, null, null, null, null);

        while (cursor.moveToNext()) {
            Paper paper = new Paper();
            paper.setId(cursor.getInt(cursor.getColumnIndex("id")));
            paper.setTitle(cursor.getString(cursor.getColumnIndex("title")));
            paper.setAuthors(cursor.getString(cursor.getColumnIndex("authors")));

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                long publicationDateMillis = cursor.getLong(cursor.getColumnIndex("publicationDate"));
                paper.setPublicationDate(LocalDate.ofEpochDay(publicationDateMillis / (24 * 60 * 60 * 1000)));
            }

            paper.setKeywords(cursor.getString(cursor.getColumnIndex("keywords")));
            paper.setDescription(cursor.getString(cursor.getColumnIndex("description")));
            paper.setCategory(cursor.getString(cursor.getColumnIndex("category")));
            paper.setUrl(cursor.getString(cursor.getColumnIndex("url")));

            papers.add(paper);
        }

        return papers;
    }

    public boolean addPaper(Paper paper) {
        ContentValues values = new ContentValues();
        values.put("title", paper.getTitle());
        values.put("authors", paper.getAuthors());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            values.put("publicationDate", paper.getPublicationDate().atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli());
        }

        values.put("keywords", paper.getKeywords());
        values.put("description", paper.getDescription());
        values.put("category", paper.getCategory());
        values.put("url", paper.getUrl());

        try {
            long rowId = database.insert("papers", null, values);
            if (rowId != -1) {
                Toast.makeText(context, "Artigo salvo!", Toast.LENGTH_SHORT).show();
                return true;
            } else {
                Toast.makeText(context, "Erro ao salvar o artigo.", Toast.LENGTH_SHORT).show();
                return false;
            }
        } catch (SQLException e) {
            Toast.makeText(context, "Erro: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public boolean updatePaper(int id, Paper paper) {
        ContentValues values = new ContentValues();
        values.put("title", paper.getTitle());
        values.put("authors", paper.getAuthors());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            values.put("publicationDate", paper.getPublicationDate().atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli());
        }

        values.put("keywords", paper.getKeywords());
        values.put("description", paper.getDescription());
        values.put("category", paper.getCategory());
        values.put("url", paper.getUrl());

        String whereClause = "id = ?";
        String[] whereArgs = {String.valueOf(id)};

        try {
            int rowsAffected = database.update("papers", values, whereClause, whereArgs);
            if (rowsAffected > 0) {
                Toast.makeText(context, "Artigo atualizado!", Toast.LENGTH_SHORT).show();
                return true;
            } else {
                Toast.makeText(context, "Erro ao atualizar o artigo.", Toast.LENGTH_SHORT).show();
                return false;
            }
        } catch (SQLException e) {
            Toast.makeText(context, "Erro: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    @SuppressLint("Range")
    public Paper getPaperById(int id) {
        String[] columns = {
                "id",
                "title",
                "authors",
                "publicationDate",
                "keywords",
                "description",
                "category",
                "url"
        };
        String selection = "id = ?";
        String[] selectionArgs = {String.valueOf(id)};

        Cursor cursor = database.query("papers", columns, selection, selectionArgs, null, null, null);

        if (cursor.moveToFirst()) {
            Paper paper = new Paper();
            paper.setId(cursor.getInt(cursor.getColumnIndex("id")));
            paper.setTitle(cursor.getString(cursor.getColumnIndex("title")));
            paper.setAuthors(cursor.getString(cursor.getColumnIndex("authors")));

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                long publicationDateMillis = cursor.getLong(cursor.getColumnIndex("publicationDate"));
                paper.setPublicationDate(LocalDate.ofEpochDay(publicationDateMillis / (24 * 60 * 60 * 1000)));
            }

            paper.setKeywords(cursor.getString(cursor.getColumnIndex("keywords")));
            paper.setDescription(cursor.getString(cursor.getColumnIndex("description")));
            paper.setCategory(cursor.getString(cursor.getColumnIndex("category")));
            paper.setUrl(cursor.getString(cursor.getColumnIndex("url")));

            return paper;
        } else {
            return null;
        }
    }

    public boolean deletePaperById(int id) {
        String whereClause = "id = ?";
        String[] whereArgs = {String.valueOf(id)};

        try {
            int rowsDeleted = database.delete("papers", whereClause, whereArgs);
            if (rowsDeleted > 0) {
                Toast.makeText(context, "Artigo deletado com sucesso!", Toast.LENGTH_SHORT).show();
                return true;
            } else {
                Toast.makeText(context, "Erro ao deletar o artigo.", Toast.LENGTH_SHORT).show();
                return false;
            }
        } catch (SQLException e) {
            Toast.makeText(context, "Erro: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            return false;
        }
    }
}
