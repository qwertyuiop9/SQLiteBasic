package com.alssimpleapps.sqlitebasic.SQLite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.alssimpleapps.sqlitebasic.SQLite.DatabaseStrings.*;

public class DatabaseHelper extends SQLiteOpenHelper {

    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        String sqlCreation = "CREATE TABLE " + TABLE_NAME +
                " ( " + FIELD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                FIELD_NAME + FIELD_TYPE_TEXT + ", " +
                FIELD_SURNAME + FIELD_TYPE_TEXT + ", " +
                FIELD_PHONE + FIELD_TYPE_TEXT +
                " )";
        db.execSQL(sqlCreation);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
