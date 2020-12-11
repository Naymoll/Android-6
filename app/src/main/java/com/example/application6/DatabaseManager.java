package com.example.application6;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class DatabaseManager {
    private DatabaseHelper dbHelper;
    private Context context;
    private SQLiteDatabase database;

    public DatabaseManager(Context c) {
        context = c;
    }

    public DatabaseManager open() throws SQLException {
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();

        return this;
    }

    public void close() {
        dbHelper.close();
    }

    private Cursor fetch() {
        String[] columns = new String[]{
                DatabaseHelper._ID,
                DatabaseHelper.FIRST,
                DatabaseHelper.SECOND,
                DatabaseHelper.FUNCTION,
                DatabaseHelper.RESULT
        };

        Cursor cursor = database.query(DatabaseHelper.TABLE_NAME, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }

        return cursor;
    }

    public void insert(HistoryItem item) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(DatabaseHelper.FIRST, item.getFirst());
        contentValue.put(DatabaseHelper.SECOND, item.getSecond());
        contentValue.put(DatabaseHelper.FUNCTION, item.getFunction());
        contentValue.put(DatabaseHelper.RESULT, item.getResult());
        database.insert(DatabaseHelper.TABLE_NAME, null, contentValue);
    }

    public ArrayList<HistoryItem> getAll() {
        ArrayList<HistoryItem> list = new ArrayList();

        Cursor cursor = fetch();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String first, second, function, result;

            first = cursor.getString(cursor.getColumnIndex(DatabaseHelper.FIRST));
            second = cursor.getString(cursor.getColumnIndex(DatabaseHelper.SECOND));
            function = cursor.getString(cursor.getColumnIndex(DatabaseHelper.FUNCTION));
            result = cursor.getString(cursor.getColumnIndex(DatabaseHelper.RESULT));

            HistoryItem item = new HistoryItem(first, second, function, result);

            list.add(item);
            cursor.moveToNext();
        }

        return list;
    }
}
