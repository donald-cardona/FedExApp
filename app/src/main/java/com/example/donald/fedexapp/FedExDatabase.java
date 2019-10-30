package com.example.donald.fedexapp;

/**
 * Created by Donald on 10/16/2019.
 */


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import java.util.Random;

public class FedExDatabase extends SQLiteOpenHelper{
    public static final String DATABASE_NAME = "FedEx.db";
    public static final String TABLE_NAME = "orders";
    public static final String COL_1 = "TRACKNUMBER";
    public static final String COL_2 = "WEIGHT";
    public static final String COL_3 = "PIECES";
    public static final String COL_4 = "DIMENSIONS";
    public static final String COL_5 = "STARTLOC";
    public static final String COL_6 = "ENDLOC";

    private Random rand;
    private FedExDistCenters distCenters;


    public FedExDatabase(Context context) {
        super(context, DATABASE_NAME, null, 1);
        SQLiteDatabase db = this.getWritableDatabase();
        rand = new Random();
        distCenters = new FedExDistCenters();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + " ("+COL_1+" INTEGER, "
                + COL_2 + " INTEGER, "+COL_3+" INTEGER, "+ COL_4 +" TEXT, " + COL_5 + " TEXT, " +
                COL_6 + " TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public int addOrder() {
        int trackNum = rand.nextInt(9000) +  1000;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues trackInfo = new ContentValues();
        trackInfo.put(COL_1, trackNum);
        trackInfo.put(COL_2, rand.nextInt(100) + 1);
        trackInfo.put(COL_3, rand.nextInt(10) + 1);
        trackInfo.put(COL_4, (rand.nextInt(99) + 1) + "x" + (rand.nextInt(99) + 1) + "x"
                + (rand.nextInt(99) + 1) + " in.");
        trackInfo.put(COL_5, distCenters.getNode(rand.nextInt(25)).getLocation());
        trackInfo.put(COL_6, distCenters.getNode(rand.nextInt(25)).getLocation());
        db.insert(TABLE_NAME, null, trackInfo);

        return trackNum;
    }

    public Cursor getData(int trackNum) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("select * from " + TABLE_NAME + " where " + COL_1 + " = " + trackNum, null);
        return result;
    }

    public boolean orderExistCheck(int trackNum) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("select " + COL_1 + " from " + TABLE_NAME, null);
        while(result.moveToNext()) {
            if(result.getInt(0) == trackNum)
                return true;
        }
        return false;
    }

    public String printDatabase() {
        String list = "Track Order List\n";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("select * from " + TABLE_NAME, null);
        while(result.moveToNext()) {
            list += result.getInt(0) + "\t " + result.getInt(1) + " lbs ";
            list += result.getInt(2) + " pieces " + result.getString(3) + "  ";
            list += result.getString(4) + "  " + result.getString(5) + "\n";
        }
        return list;
    }


}
