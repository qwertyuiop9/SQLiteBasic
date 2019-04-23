package com.alssimpleapps.sqlitebasic.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import static com.alssimpleapps.sqlitebasic.SQLite.DatabaseStrings.*;

public class DatabaseManager {

    private DatabaseHelper dbHelper;

    public DatabaseManager(Context context) {
        this.dbHelper = new DatabaseHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void addUser(String name, String surname, String phone) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(FIELD_NAME, name);
        contentValues.put(FIELD_SURNAME, surname);
        contentValues.put(FIELD_PHONE, phone);

        try {
            db.insert(TABLE_NAME, null, contentValues);
        } catch (SQLiteException sqle) {
            Log.d("addUser", "Error inserting a new user in table " + TABLE_NAME);
        }
    }

    public boolean deleteUser(int userId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        boolean result = false;

        try {
            if (db.delete(TABLE_NAME, FIELD_ID + " = ? ", new String[]{Long.toString(userId)}) > 0) {
                result = true;
            } else {
                result = false;
            }

        } catch (SQLiteException sqle) {
            Log.d("deleteUser", "Error deleting a user in table " + TABLE_NAME);
        }

        return result;
    }


    public Cursor getTableRecords(String tableName) {

        Cursor cursor;
        try {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            cursor=db.query(TABLE_NAME, null, null, null, null, null, null, null);
        }
        catch(SQLiteException sqle) {
            return null;
        }
        return cursor;
    }


}
