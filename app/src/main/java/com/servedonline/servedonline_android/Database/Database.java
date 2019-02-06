package com.servedonline.servedonline_android.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.Looper;

import com.servedonline.servedonline_android.Entity.User;

public class Database {

    private DatabaseThread dbThread;
    private Handler handler;
    private Context context;

    public Database(Context context) {
        this(context, context.getMainLooper());
    }

    public Database(Context context, Looper looper) {
        this.context = context;
        handler = new Handler(looper);
        dbThread = new DatabaseThread(context, handler);

        dbThread.start();
    }

    public void insert(final DatabaseGoverned item, DatabaseThread.OnDatabaseRequestComplete callback) {
        if (item == null) {
            if (callback != null) {
                callback.onRequestComplete(null);
            }
            return;
        }

        DatabaseThread.DatabaseRunnable request = new DatabaseThread.DatabaseRunnable() {
            @Override
            public Object run() {
                SQLiteDatabase db = dbThread.getDatabaseManager().getWritableDatabase();

                long id = db.insert(item.getDatabaseTable(), null, item.toContentValues());

                db.close();

                return id;
            }
        };

        dbThread.enqueueDatabaseRequest(new DatabaseThread.DatabaseRequest(request, callback));
    }

    public void insert(final DatabaseGoverned[] items, DatabaseThread.OnDatabaseRequestComplete callback) {
        if (items == null) {
            if (callback != null) {
                callback.onRequestComplete(null);
            }
            return;
        }

        DatabaseThread.DatabaseRunnable request = new DatabaseThread.DatabaseRunnable() {
            @Override
            public Object run() {
                long[] ids = Database.this.replace(items);

                return ids;
            }
        };

        dbThread.enqueueDatabaseRequest(new DatabaseThread.DatabaseRequest(request, callback));
    }

    public void replace(final DatabaseGoverned item, DatabaseThread.OnDatabaseRequestComplete callback) {
        if (item == null) {
            if (callback != null) {
                callback.onRequestComplete(null);
            }
            return;
        }

        DatabaseThread.DatabaseRunnable request = new DatabaseThread.DatabaseRunnable() {
            @Override
            public Object run() {
                SQLiteDatabase db = dbThread.getDatabaseManager().getWritableDatabase();

                long id = db.replace(item.getDatabaseTable(), null, item.toContentValues());

                db.close();

                return id;
            }
        };

        dbThread.enqueueDatabaseRequest(new DatabaseThread.DatabaseRequest(request, callback));
    }

    public void replace(final DatabaseGoverned[] items, DatabaseThread.OnDatabaseRequestComplete callback) {
        if (items == null) {
            if (callback != null) {
                callback.onRequestComplete(null);
            }
            return;
        }

        DatabaseThread.DatabaseRunnable request = new DatabaseThread.DatabaseRunnable() {
            @Override
            public Object run() {
                long[] ids = Database.this.replace(items);

                return ids;
            }
        };

        dbThread.enqueueDatabaseRequest(new DatabaseThread.DatabaseRequest(request, callback));
    }

    private long[] replace(DatabaseGoverned[] items) {
        long[] ids = new long[items.length];

        SQLiteDatabase db = dbThread.getDatabaseManager().getWritableDatabase();
        db.beginTransaction();

        for (int i = 0; i < items.length; i++) {
            DatabaseGoverned item = items[i];
            ids[i] = db.replace(item.getDatabaseTable(), null, item.toContentValues());
        }

        db.setTransactionSuccessful();
        db.endTransaction();

        db.close();
        return ids;
    }

    public void delete(final DatabaseGoverned item, DatabaseThread.OnDatabaseRequestComplete callback) {
        if (item == null) {
            if (callback != null) {
                callback.onRequestComplete(null);
            }
            return;
        }

        DatabaseThread.DatabaseRunnable request = new DatabaseThread.DatabaseRunnable() {
            @Override
            public Object run() {
                SQLiteDatabase db = dbThread.getDatabaseManager().getWritableDatabase();

                String whereClause = item.getDatabaseIdColumn() + " = ?";
                String[] whereArgs = new String[] { item.getDatabaseId() };
                db.delete(item.getDatabaseTable(), whereClause, whereArgs);

                db.close();

                return true;
            }
        };

        dbThread.enqueueDatabaseRequest(new DatabaseThread.DatabaseRequest(request, callback));
    }

    public void delete(final DatabaseGoverned[] items, DatabaseThread.OnDatabaseRequestComplete callback) {
        if (items == null) {
            if (callback != null) {
                callback.onRequestComplete(null);
            }
            return;
        }

        DatabaseThread.DatabaseRunnable request = new DatabaseThread.DatabaseRunnable() {
            @Override
            public Object run() {
                long[] results = new long[items.length];

                SQLiteDatabase db = dbThread.getDatabaseManager().getWritableDatabase();
                db.beginTransaction();

                for (int i = 0; i < items.length; i++) {
                    DatabaseGoverned item = items[i];
                    String whereClause = item.getDatabaseIdColumn() + " = ?";
                    String[] whereArgs = { item.getDatabaseId() };
                    results[i] = db.delete(item.getDatabaseTable(), whereClause, whereArgs);
                }

                db.setTransactionSuccessful();
                db.endTransaction();
                db.close();

                return results;
            }
        };

        dbThread.enqueueDatabaseRequest(new DatabaseThread.DatabaseRequest(request, callback));
    }

    public void clearTable(final String tableName, DatabaseThread.OnDatabaseRequestComplete<Boolean> callback) {
        DatabaseThread.DatabaseRunnable<Boolean> request = new DatabaseThread.DatabaseRunnable<Boolean>() {
            @Override
            public Boolean run() {
                SQLiteDatabase db = dbThread.getDatabaseManager().getWritableDatabase();

                int affectedRows = db.delete(tableName, null, null);

                db.close();

                return affectedRows > 0;
            }
        };

        dbThread.enqueueDatabaseRequest(request, callback);
    }

    public void getUser( DatabaseThread.OnDatabaseRequestComplete<User> callback) {
        DatabaseThread.DatabaseRunnable<User> runnable = new DatabaseThread.DatabaseRunnable<User>() {
            @Override
            public User run() {
                SQLiteDatabase db = dbThread.getDatabaseManager().getReadableDatabase();

                User out = null;

                Cursor cursor = db.query(DatabaseTables.USER, null, null, null, null, null, null);
                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    out = new User(cursor);
                }
                cursor.close();
                db.close();

                return out;

            }
        };
        dbThread.enqueueDatabaseRequest(runnable, callback);
    }

    public void checkLoginStatus(DatabaseThread.OnDatabaseRequestComplete<Boolean> callback) {
        DatabaseThread.DatabaseRunnable<Boolean> runnable = new DatabaseThread.DatabaseRunnable<Boolean>() {
            @Override
            public Boolean run() {
                SQLiteDatabase db = dbThread.getDatabaseManager().getReadableDatabase();

                Boolean out = false;

                Cursor cursor = db.query(DatabaseTables.USER, null, null, null, null, null, null);
                if (cursor.getCount() > 0) {
                    out = true;
                }
                cursor.close();
                db.close();

                return out;
            }
        };
        dbThread.enqueueDatabaseRequest(runnable, callback);
    }

}
