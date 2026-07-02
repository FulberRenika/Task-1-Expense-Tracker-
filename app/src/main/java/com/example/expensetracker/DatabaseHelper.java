package com.example.expensetracker;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.database.Cursor;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME =
            "ExpenseDB";

    private static final int DATABASE_VERSION =
            1;

    public DatabaseHelper(Context context) {

        super(
                context,
                DATABASE_NAME,
                null,
                DATABASE_VERSION
        );
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String createTable =
                "CREATE TABLE expenses (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "category TEXT," +
                        "amount INTEGER)";

        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(
            SQLiteDatabase db,
            int oldVersion,
            int newVersion) {

        db.execSQL(
                "DROP TABLE IF EXISTS expenses"
        );

        onCreate(db);
    }

    public void insertExpense(
            String category,
            int amount) {

        SQLiteDatabase db =
                this.getWritableDatabase();

        ContentValues values =
                new ContentValues();

        values.put("category", category);
        values.put("amount", amount);

        db.insert(
                "expenses",
                null,
                values
        );

        db.close();
    }

    public String getAllExpenses() {

        SQLiteDatabase db =
                this.getReadableDatabase();

        Cursor cursor =
                db.rawQuery(
                        "SELECT * FROM expenses",
                        null
                );

        StringBuilder data =
                new StringBuilder();

        while(cursor.moveToNext()) {

            String category =
                    cursor.getString(1);

            int amount =
                    cursor.getInt(2);

            data.append(category)
                    .append(" - ₹")
                    .append(amount)
                    .append("\n");
        }

        cursor.close();

        return data.toString();
    }

    public int getTotalExpense() {

        SQLiteDatabase db =
                this.getReadableDatabase();

        Cursor cursor =
                db.rawQuery(
                        "SELECT SUM(amount) FROM expenses",
                        null
                );

        int total = 0;

        if(cursor.moveToFirst()) {

            total =
                    cursor.getInt(0);
        }

        cursor.close();

        return total;
    }

    public void clearAllExpenses() {

        SQLiteDatabase db =
                this.getWritableDatabase();

        db.delete(
                "expenses",
                null,
                null
        );

        db.close();
    }
}