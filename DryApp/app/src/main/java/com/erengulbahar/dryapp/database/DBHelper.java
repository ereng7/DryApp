package com.erengulbahar.dryapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper
{
    public DBHelper(Context context)
    {
        super(context,"Types.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        //We create table.
        db.execSQL("CREATE TABLE Costs(id INTEGER PRIMARY KEY, Jacket VARCHAR, Coat VARCHAR, Shirt VARCHAR, TShirt VARCHAR, Trouser VARCHAR, Short VARCHAR, Furniture VARCHAR, Carpet VARCHAR, Pillow VARCHAR)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS Costs");
        onCreate(db);
    }

    public void insertData(String jacket, String coat, String shirt, String tshirt, String trouser, String shrt, String furniture, String carpet, String pillow)
    {
        //We save datas to local database.
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("Jacket",jacket);
        values.put("Coat",coat);
        values.put("Shirt",shirt);
        values.put("TShirt",tshirt);
        values.put("Trouser",trouser);
        values.put("Short",shrt);
        values.put("Furniture",furniture);
        values.put("Carpet",carpet);
        values.put("Pillow",pillow);

        long newRowId = db.insert("Costs",null,values);
    }
}