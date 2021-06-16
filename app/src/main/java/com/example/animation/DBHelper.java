package com.example.animation;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        super(context, "ballMaze.db", null, 1);
    }
    //creating Database with name ballMaze.db
    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL("create Table playerTable(userName TEXT primary key,password TEXT, highScore INTEGER)");
        //Creating Table with name playerTable
    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int oldVersion, int newVersion) {
        DB.execSQL("drop Table if exists playerTable");
        //Drops the Table if the Table already Exists in Database
    }



    public Boolean insertPlayerData(String userName, String password, int highScore)
    {
        //Function to insert data to database
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("userName", userName);
        contentValues.put("password", password);
        contentValues.put("highScore", highScore);

        long result=DB.insert("playerTable",null,contentValues);
        //runs insert query
        if(result==-1) {
            return false;
        }else{
            return true;
        }
    }



    public Boolean updateScore(String userName, String password, int highScore)
    {
        //Function to update data to database
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("userName", userName);
        contentValues.put("password", password);
        contentValues.put("highScore", highScore);
        Cursor cursor = DB.rawQuery("Select * from playerTable where userName = ?",new String[]{userName});
        if(cursor.getCount()>0) {
            long result = DB.update("playerTable", contentValues, "userName=?", new String[]{userName});
            //runs update query to playerTable where userName is given by user
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        }else{
            return false;
        }
    }


    public Cursor getPlayer(String userName)
    {
        //Function to select data from database
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("select * from playerTable where userName = ?", new String[]{userName});
        //runs select query to select data from playerTable  where userName is given by user
        return cursor;
    }



    public Cursor getTop3()
    {
        //Function to get Top3 data from database
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("SELECT * FROM playerTable ORDER BY highScore DESC", null);
        //runs select query to Top3 data from academy table

        return cursor;
    }


}
