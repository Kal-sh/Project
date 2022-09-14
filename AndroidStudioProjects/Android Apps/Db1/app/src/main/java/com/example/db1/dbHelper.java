package com.example.db1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class dbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "example.db";
    public static final String TABLE_NAME = "Example_table";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_NAME = "NAME";
    public static final String COLUMN_NUMBER = "NUMBER";
    Context context;
    public dbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE = "CREATE TABLE " + TABLE_NAME + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_NAME + " TEXT, " + COLUMN_NUMBER + " INTEGER);" ;
        db.execSQL(SQL_CREATE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        String SQL_DELETE = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(SQL_DELETE);
        onCreate(db);
    }

    public void insertData(String name, int num){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_NUMBER, num);
        long result = db.insert(TABLE_NAME, null, values);
        if(result == -1){
            Toast.makeText(context, "Data not saved", Toast.LENGTH_SHORT).show();
        }else
            Toast.makeText(context, "Data saved successfully", Toast.LENGTH_SHORT).show();
    }
    public void updateData(String id, String name, String num){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_NUMBER, num);
        long result = db.update(TABLE_NAME, values, "ID = ?", new String []{id});
        if(result == -1)
            Toast.makeText(context, "Data not updated", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(context, "Data updated successfully", Toast.LENGTH_SHORT).show();
    }

    public Cursor readData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);
        return cursor;
    }

    public Cursor searchData(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = new String[]{COLUMN_ID,COLUMN_NAME,COLUMN_NUMBER};
        Cursor cursor = db.query(TABLE_NAME, columns, "ID = ?", new String[]{id}, null, null, null,null);
        return cursor;
    }
}
