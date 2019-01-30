package com.servedonline.servedonline_android.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseManager extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "served.db";
    private static final int VERSION = 1;


    public DatabaseManager(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DatabaseManager(Context context) { super(context, DATABASE_NAME, null, VERSION); }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    private void initialiseDatabase(SQLiteDatabase db) {

    }

    public static class Columns {
        public static final String ID = "_id";


    }
}
