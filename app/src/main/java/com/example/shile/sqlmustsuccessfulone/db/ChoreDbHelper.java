package com.example.shile.sqlmustsuccessfulone.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class ChoreDbHelper extends SQLiteOpenHelper {

    public ChoreDbHelper(Context context) {
        super(context, ChoreContract.DB_NAME, null, ChoreContract.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + ChoreContract.ChoreEntry.TABLE + " ( " +
                ChoreContract.ChoreEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ChoreContract.ChoreEntry.COL_CHORE_TITLE +  " TEXT NOT NULL);";

        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ChoreContract.ChoreEntry.TABLE);
        onCreate(db);
    }

}