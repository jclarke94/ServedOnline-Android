package com.servedonline.servedonline_android.util;

import android.database.Cursor;

public class CursorUtils {

    /**************************************************
     *
     *                 CURSOR UTILS
     *
     **************************************************/

    /**
     * Gets the Value of the specified column or the default value if the column is NULL
     * @param cursor            Cursor to get data from
     * @param columnName        Cursor name to get data for
     * @param defaultValue      Default value to return instead of NULL
     * @return                  Value from cursor or the default value
     */
    public static int getCursorValue(Cursor cursor, String columnName, int defaultValue) {
        int columnIndex = cursor.getColumnIndex(columnName);
        if (cursor.isNull(columnIndex)) {
            return defaultValue;
        }
        return cursor.getInt(columnIndex);
    }

    /**
     * Gets the Value of the specified column or the default value if the column is NULL
     * @param cursor            Cursor to get data from
     * @param columnName        Cursor name to get data for
     * @param defaultValue      Default value to return instead of NULL
     * @return                  Value from cursor or the default value
     */
    public static String getCursorValue(Cursor cursor, String columnName, String defaultValue) {
        int columnIndex = cursor.getColumnIndex(columnName);
        if (cursor.isNull(columnIndex)) {
            return defaultValue;
        }
        return cursor.getString(columnIndex);
    }

    /**
     * Gets the Value of the specified column or the default value if the column is NULL
     * @param cursor            Cursor to get data from
     * @param columnName        Cursor name to get data for
     * @param defaultValue      Default value to return instead of NULL
     * @return                  Value from cursor or the default value
     */
    public static long getCursorValue(Cursor cursor, String columnName, long defaultValue) {
        int columnIndex = cursor.getColumnIndex(columnName);
        if (cursor.isNull(columnIndex)) {
            return defaultValue;
        }
        return cursor.getLong(columnIndex);
    }

    /**
     * Gets the Value of the specified column or the default value if the column is NULL
     * @param cursor            Cursor to get data from
     * @param columnName        Cursor name to get data for
     * @param defaultValue      Default value to return instead of NULL
     * @return                  Value from cursor or the default value
     */
    public static float getCursorValue(Cursor cursor, String columnName, float defaultValue) {
        int columnIndex = cursor.getColumnIndex(columnName);
        if (cursor.isNull(columnIndex)) {
            return defaultValue;
        }
        return cursor.getFloat(columnIndex);
    }

    /**
     * Gets the Value of the specified column or the default value if the column is NULL
     * @param cursor            Cursor to get data from
     * @param columnName        Cursor name to get data for
     * @param defaultValue      Default value to return instead of NULL
     * @return                  Value from cursor or the default value
     */
    public static double getCursorValue(Cursor cursor, String columnName, double defaultValue) {
        int columnIndex = cursor.getColumnIndex(columnName);
        if (cursor.isNull(columnIndex)) {
            return defaultValue;
        }
        return cursor.getDouble(columnIndex);
    }

    /**
     * Gets the Value of the specified column or the default value if the column is NULL
     * @param cursor            Cursor to get data from
     * @param columnName        Cursor name to get data for
     * @param defaultValue      Default value to return instead of NULL
     * @return                  Value from the cursor or the default value
     */
    public static boolean getCursorValue(Cursor cursor, String columnName, boolean defaultValue) {
        int columnIndex = cursor.getColumnIndex(columnName);
        if (cursor.isNull(columnIndex)) {
            return defaultValue;
        }
        return cursor.getInt(columnIndex) == 1;
    }
}