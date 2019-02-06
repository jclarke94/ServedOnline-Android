package com.servedonline.servedonline_android.Database;

import android.content.ContentValues;
import android.database.Cursor;

public abstract class DatabaseGoverned {

    public DatabaseGoverned() {

    }

    public DatabaseGoverned(Cursor cursor) {
        // STUB - Should be implemented in all subclasses
    }

    /**
     * The database table where this object is stored
     * @return      Database.Tables.*
     */
    public abstract String getDatabaseTable();

    /**
     * The Database Table ID value, needed for Replacing items
     * @return      Identifier for the given record
     */
    public abstract String getDatabaseId();

    /**
     * A ContentValues bundle including data to be inserted into the database
     * @return      ContentValues object. Try not to return NULL.
     */
    public abstract ContentValues toContentValues();

    /**
     * The Database Column which will is the identifying record column.
     * @return      Database.Columns.*
     */
    public String getDatabaseIdColumn() {
        return DatabaseColumns.ID;
    }
}

