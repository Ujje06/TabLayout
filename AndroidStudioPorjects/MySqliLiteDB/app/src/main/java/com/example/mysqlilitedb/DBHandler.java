package com.example.mysqlilitedb;




import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHandler extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "mydatabase";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "mytable";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_OCCUPATION = "occupation";
    private static final String COLUMN_SERIAL_NUMBER = "serial_number";

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_NAME + " TEXT, " +
                COLUMN_OCCUPATION + " TEXT, " +
                COLUMN_SERIAL_NUMBER + " INTEGER PRIMARY KEY)";
        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void insertData(String name, String occupation, int serialNumber) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_OCCUPATION, occupation);
        values.put(COLUMN_SERIAL_NUMBER, serialNumber);
        db.insert(TABLE_NAME, null, values);
        db.close();
    }




    public void deleteData(int serialNumber) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_SERIAL_NUMBER + " = ?", new String[]{String.valueOf(serialNumber)});
        db.close();
    }

    public void updateData(int serialNumber, String newName, String newOccupation) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, newName);
        values.put(COLUMN_OCCUPATION, newOccupation);
        db.update(TABLE_NAME, values, COLUMN_SERIAL_NUMBER + " = ?", new String[]{String.valueOf(serialNumber)});
        db.close();
    }


    public String fetchData() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        StringBuilder data = new StringBuilder();

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
                @SuppressLint("Range") String occupation = cursor.getString(cursor.getColumnIndex(COLUMN_OCCUPATION));
                @SuppressLint("Range") int serialNumber = cursor.getInt(cursor.getColumnIndex(COLUMN_SERIAL_NUMBER));

                data.append("Name: ").append(name).append(", Occupation: ").append(occupation)
                        .append(", Serial Number: ").append(serialNumber).append("\n");
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return data.toString();
    }
}
