package com.example.rit_system;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Database extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "RitSystem.db";
    private static final String SQL_CREATE_PASS_SUBJECT = "CREATE TABLE subjects (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "code TEXT," +
            "name TEXT," +
            "description TEXT," +
            "startTime TIME," +
            "endTime TIME," +
            "classroom TEXT," +
            "teacherName TEXT," +
            "requirements TEXT," +
            "courseLoad INT," +
            "credits INT," +
            "numberOfVacancies INT" +
            ")";


    private static final String SQL_POPULATE_PASS_SUBJECT = "INSERT INTO subjects (code, name, description, startTime, endTime, classroom, teacherName, requirements, courseLoad, credits, numberOfVacancies)" +
            "VALUES" +
            "    ('MAT101', 'Matemática Básica', 'Introdução à matemática', '08:00:00', '10:00:00', 'Sala 101', 'Prof. Silva', 'Nenhum', 60, 3, 30)," +
            "    ('PHY201', 'Física I', 'Mecânica clássica', '10:15:00', '12:15:00', 'Sala 202', 'Prof. Santos', 'MAT101', 60, 3, 25)," +
            "    ('CHEM101', 'Química Geral', 'Introdução à química', '08:00:00', '10:00:00', 'Sala 103', 'Prof. Vieira', 'Nenhum', 60, 3, 28)," +
            "    ('BIO101', 'Biologia Celular', 'Introdução à biologia celular', '14:00:00', '16:00:00', 'Sala 105', 'Prof. Lima', 'Nenhum', 60, 3, 30)," +
            "    ('ENG101', 'Inglês I', 'Inglês básico', '16:15:00', '18:15:00', 'Sala 201', 'Prof. Johnson', 'Nenhum', 60, 3, 25)," +
            "    ('HIS101', 'História Mundial', 'História global', '08:00:00', '10:00:00', 'Sala 301', 'Prof. Smith', 'Nenhum', 60, 3, 30)," +
            "    ('COMP101', 'Introdução à Programação', 'Fundamentos de programação', '10:15:00', '12:15:00', 'Sala 303', 'Prof. Brown', 'MAT101', 60, 3, 28)," +
            "    ('PSY101', 'Psicologia Geral', 'Introdução à psicologia', '14:00:00', '16:00:00', 'Sala 401', 'Prof. Garcia', 'Nenhum', 60, 3, 27)," +
            "    ('ART101', 'Artes Visuais', 'Introdução às artes', '16:15:00', '18:15:00', 'Sala 405', 'Prof. Rodriguez', 'Nenhum', 60, 3, 30)," +
            "    ('ECON101', 'Economia Básica', 'Introdução à economia', '08:00:00', '10:00:00', 'Sala 501', 'Prof. Davis', 'Nenhum', 60, 3, 29)," +
            "    ('PHIL101', 'Filosofia', 'Introdução à filosofia', '10:15:00', '12:15:00', 'Sala 503', 'Prof. Taylor', 'Nenhum', 60, 3, 26)," +
            "    ('GEO101', 'Geografia Humana', 'Introdução à geografia', '14:00:00', '16:00:00', 'Sala 601', 'Prof. Clark', 'Nenhum', 60, 3, 30)," +
            "    ('SOC101', 'Sociologia', 'Introdução à sociologia', '16:15:00', '18:15:00', 'Sala 603', 'Prof. Adams', 'Nenhum', 60, 3, 24)," +
            "    ('POL101', 'Ciência Política', 'Introdução à política', '08:00:00', '10:00:00', 'Sala 701', 'Prof. Miller', 'Nenhum', 60, 3, 30)," +
            "    ('PHY202', 'Física II', 'Eletromagnetismo', '10:15:00', '12:15:00', 'Sala 703', 'Prof. Johnson', 'PHY201', 60, 3, 22);";

    private static final String SQL_DELETE_PASS_SUBJECT = "DROP TABLE IF EXISTS teacher";

    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate( SQLiteDatabase db ) {
        db.execSQL(SQL_CREATE_PASS_SUBJECT);
        db.execSQL(SQL_POPULATE_PASS_SUBJECT);
    }

    public void onUpgrade( SQLiteDatabase db, int oldVersion, int newVersion ) {
        db.execSQL(SQL_DELETE_PASS_SUBJECT);
        onCreate(db);
    }
}
