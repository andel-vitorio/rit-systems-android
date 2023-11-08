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

    private static final String SQL_CREATE_UNDERGRADUATE_STUDENT = "CREATE TABLE undergraduate_students (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "name TEXT," +
            "dateOfEntry TEXT," +
            "registration TEXT," +
            "email TEXT," +
            "phoneNumber TEXT," +
            "nameOfMentee TEXT," +
            "status TEXT," +
            "projectName TEXT," +
            "typeOfOrientation TEXT" +
            ")";

    private static final String SQL_CREATE_GRADUATE_STUDENT = "CREATE TABLE graduate_students (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "name TEXT," +
            "dateOfEntry TEXT," +
            "registration TEXT," +
            "email TEXT," +
            "phoneNumber TEXT," +
            "nameOfMentee TEXT," +
            "status TEXT," +
            "graduateProgram TEXT," +
            "researchTitle TEXT," +
            "defenseDate TEXT" +
            ")";

    private static final String SQL_CREATE_COORDINATION_ACTIVITIES = "CREATE TABLE coordination_activities (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "activityTitle TEXT NOT NULL, " +
            "nameOfPersonResponsible TEXT NOT NULL, " +
            "startDate INTEGER NOT NULL, " +
            "endDate INTEGER NOT NULL, " +
            "priority TEXT, " +
            "status TEXT, " +
            "description TEXT);";


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

    private static final String SQL_POPULATE_GRADUATE_STUDENT = "INSERT INTO graduate_students (name, dateOfEntry, registration, email, phoneNumber, nameOfMentee, status, graduateProgram, researchTitle, defenseDate)" +
            "VALUES " +
            "('John Doe', '1990-05-15', '123456', 'john.doe@example.com', '123-456-7890', 'Mary Brown', 'Active', 'Program 1', 'Title 1', '2023-09-30'), " +
            "('Alice Smith', '1985-02-20', '789012', 'alice.smith@example.com', '987-654-3210', 'Mary Brown', 'Inactive', 'Program 2', 'Title 2', '2023-10-01'), " +
            "('Bob Johnson', '1988-09-10', '456789', 'bob.johnson@example.com', '555-555-5555', 'Mary Brown', 'Active', 'Program 3', 'Title 3', '2023-10-05'), " +
            "('Jane Smith', '1992-11-25', '987654', 'jane.smith@example.com', '333-333-3333', 'Mary Brown', 'Active', 'Program 4', 'Title 4', '2023-10-10'), " +
            "('Michael Johnson', '1991-07-18', '654321', 'michael.johnson@example.com', '222-222-2222', 'Mary Brown', 'Inactive', 'Program 5', 'Title 5', '2023-10-15'), " +
            "('Emily Davis', '1989-04-30', '555555', 'emily.davis@example.com', '777-777-7777', 'Mary Brown', 'Active', 'Program 6', 'Title 6', '2023-10-20'), " +
            "('David Wilson', '1990-08-12', '777777', 'david.wilson@example.com', '999-999-9999', 'Emily Davis', 'Inactive', 'Program 7', 'Title 7', '2023-10-25'), " +
            "('Maria Garcia', '1993-01-05', '888888', 'maria.garcia@example.com', '111-111-1111', 'Emily Davis', 'Active', 'Program 8', 'Title 8', '2023-10-30'), " +
            "('Kevin Brown', '1992-03-28', '999999', 'kevin.brown@example.com', '444-444-4444', 'Emily Davis', 'Inactive', 'Program 9', 'Title 9', '2023-11-01'), " +
            "('Sarah Lee', '1987-06-17', '101010', 'sarah.lee@example.com', '666-666-6666', 'Matthew Martinez', 'Active', 'Program 10', 'Title 10', '2023-11-05'), " +
            "('Robert Smith', '1994-09-02', '121212', 'robert.smith@example.com', '333-333-3333', 'Matthew Martinez', 'Inactive', 'Program 11', 'Title 11', '2023-11-10'), " +
            "('Linda Johnson', '1993-02-22', '131313', 'linda.johnson@example.com', '222-222-2222', 'Matthew Martinez', 'Active', 'Program 12', 'Title 12', '2023-11-15'), " +
            "('William Davis', '1995-03-11', '141414', 'william.davis@example.com', '555-555-5555', 'Matthew Martinez', 'Inactive', 'Program 13', 'Title 13', '2023-11-20'), " +
            "('Susan Wilson', '1994-11-29', '151515', 'susan.wilson@example.com', '777-777-7777', 'Ava Jackson', 'Active', 'Program 14', 'Title 14', '2023-11-25'), " +
            "('Karen Garcia', '1996-07-03', '161616', 'karen.garcia@example.com', '999-999-9999', 'Ava Jackson', 'Inactive', 'Program 15', 'Title 15', '2023-12-01');";

    private static final String SQL_POPULATE_UNDERGRADUATE_STUDENT = "INSERT INTO undergraduate_students (name, dateOfEntry, registration, email, phoneNumber, nameOfMentee, status, projectName, typeOfOrientation)" +
            "VALUES " +
            "('John Doe', '1990-05-15', '123456', 'john.doe@example.com', '123-456-7890', 'MenteeChloe Hall', 'Active', 'Project 1', 'Orientation 1'), " +
            "('Alice Smith', '1985-02-20', '789012', 'alice.smith@example.com', '987-654-3210', 'Chloe Hall', 'Inactive', 'Project 2', 'Orientation 2'), " +
            "('Bob Johnson', '1988-09-10', '456789', 'bob.johnson@example.com', '555-555-5555', 'Chloe Hall', 'Active', 'Project 3', 'Orientation 3'), " +
            "('Emily Wilson', '1992-03-25', '234567', 'emily.wilson@example.com', '444-555-6666', 'James Taylor', 'Active', 'Project 4', 'Orientation 4'), " +
            "('Michael Brown', '1991-11-12', '345678', 'michael.brown@example.com', '555-666-7777', 'James Taylor', 'Inactive', 'Project 5', 'Orientation 5'), " +
            "('Sarah Davis', '1993-07-07', '456789', 'sarah.davis@example.com', '777-888-9999', 'James Taylor', 'Active', 'Project 6', 'Orientation 6'), " +
            "('Christopher Garcia', '1994-09-30', '567890', 'christopher.garcia@example.com', '888-999-0000', 'James Taylor', 'Active', 'Project 7', 'Orientation 7'), " +
            "('Linda Johnson', '1995-01-15', '678901', 'linda.johnson@example.com', '999-000-1111', 'Oliver', 'Inactive', 'Project 8', 'Orientation 8'), " +
            "('Daniel Smith', '1996-06-20', '789012', 'daniel.smith@example.com', '111-222-3333', 'Oliver', 'Active', 'Project 9', 'Orientation 9'), " +
            "('Jessica Hall', '1997-08-02', '890123', 'jessica.hall@example.com', '222-333-4444', 'Oliver', 'Inactive', 'Project 10', 'Orientation 10'), " +
            "('Kevin Wilson', '1996-12-10', '901234', 'kevin.wilson@example.com', '333-444-5555', 'Oliver', 'Active', 'Project 11', 'Orientation 11'), " +
            "('Natalie Davis', '1998-02-05', '123456', 'natalie.davis@example.com', '444-555-6666', 'Ava Jackson', 'Active', 'Project 12', 'Orientation 12'), " +
            "('Robert Smith', '1997-05-18', '234567', 'robert.smith@example.com', '555-666-7777', 'Ava Jackson', 'Inactive', 'Project 13', 'Orientation 13'), " +
            "('Olivia Johnson', '1999-03-28', '345678', 'olivia.johnson@example.com', '666-777-8888', 'Ava Jackson', 'Active', 'Project 14', 'Orientation 14'), " +
            "('Sophia Davis', '1998-07-20', '456789', 'sophia.davis@example.com', '777-888-9999', 'Ava Jackson', 'Inactive', 'Project 15', 'Orientation 15');";

    private static final String SQL_POPULATE_COORDINATION_ACTIVITIES = "INSERT INTO coordination_activities (activityTitle, nameOfPersonResponsible, startDate, endDate, priority, status, description) VALUES" +
            "('Atividade 1', 'Responsável 1', 1677298800000, 1677561600000, 'Alta', 'Concluída', 'Descrição 1')," +
            "('Atividade 2', 'Responsável 2', 1677753600000, 1678016400000, 'Média', 'Em andamento', 'Descrição 2')," +
            "('Atividade 3', 'Responsável 3', 1678100400000, 1678363200000, 'Baixa', 'Pendente', 'Descrição 3')," +
            "('Atividade 4', 'Responsável 4', 1678446800000, 1678709600000, 'Alta', 'Concluída', 'Descrição 4')," +
            "('Atividade 5', 'Responsável 5', 1678793200000, 1679056000000, 'Média', 'Em andamento', 'Descrição 5')," +
            "('Atividade 6', 'Responsável 6', 1679146800000, 1679409600000, 'Baixa', 'Pendente', 'Descrição 6')," +
            "('Atividade 7', 'Responsável 7', 1679492400000, 1679755200000, 'Alta', 'Concluída', 'Descrição 7')," +
            "('Atividade 8', 'Responsável 8', 1679838000000, 1680100800000, 'Média', 'Em andamento', 'Descrição 8')," +
            "('Atividade 9', 'Responsável 9', 1680183600000, 1680446400000, 'Baixa', 'Pendente', 'Descrição 9')," +
            "('Atividade 10', 'Responsável 10', 1680530000000, 1680792800000, 'Alta', 'Concluída', 'Descrição 10');";

    private static final String SQL_DELETE_PASS_SUBJECT = "DROP TABLE IF EXISTS teacher";
    private static final String SQL_DELETE_PASS_UNDERGRADUATE = "DROP TABLE IF EXISTS undergraduate_students";
    private static final String SQL_DELETE_PASS_GRADUATE = "DROP TABLE IF EXISTS graduate_students";
    private static final String SQL_DELETE_PASS_COORDINATION_ACTIVITIES= "DROP TABLE IF EXISTS coordination_activities";

    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate( SQLiteDatabase db ) {
        db.execSQL(SQL_CREATE_PASS_SUBJECT);
        db.execSQL(SQL_POPULATE_PASS_SUBJECT);

        db.execSQL(SQL_CREATE_UNDERGRADUATE_STUDENT);
        db.execSQL(SQL_POPULATE_UNDERGRADUATE_STUDENT);

        db.execSQL(SQL_CREATE_GRADUATE_STUDENT);
        db.execSQL(SQL_POPULATE_GRADUATE_STUDENT);

        db.execSQL(SQL_CREATE_COORDINATION_ACTIVITIES);
        db.execSQL(SQL_POPULATE_COORDINATION_ACTIVITIES);
    }

    public void onUpgrade( SQLiteDatabase db, int oldVersion, int newVersion ) {
        db.execSQL(SQL_DELETE_PASS_SUBJECT);
        db.execSQL(SQL_DELETE_PASS_UNDERGRADUATE);
        db.execSQL(SQL_DELETE_PASS_GRADUATE);
        db.execSQL(SQL_DELETE_PASS_COORDINATION_ACTIVITIES);
        onCreate(db);
    }
}
