package com.example.calculatorlab.logic;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.NonNull;

import com.example.calculatorlab.data.Point;

public class Graphic extends Calculator {

    public Graphic(Context context) {
        super(context);

        createTable();
    }

    public void createTable() {
        db.execSQL("CREATE TABLE IF NOT EXISTS graphic (_id INTEGER PRIMARY KEY AUTOINCREMENT, x FLOAT, y FLOAT)");
    }

    public void addCords(@NonNull Point [] points) {

        db.beginTransaction();

        try {

            for(int i = 0; i < points.length; i++) {
                ContentValues contentValues = new ContentValues();
                contentValues.put("x", points[i].getX());
                contentValues.put("y", points[i].getY());

                db.insert("graphic", null, contentValues);
            }

            db.setTransactionSuccessful();
        }
        finally {
            db.endTransaction();
        }
    }

    public Point[] getCords() {
        Cursor cordsCursor =  db.rawQuery("select * from graphic", null);
        int length = cordsCursor.getCount();
        //cordsCursor.moveToFirst();
        Point [] points = new Point[length];

        int j = 0;
        while(cordsCursor.moveToNext()) {
            points[j] = new Point();
            points[j].setX(cordsCursor.getFloat(1));
            points[j].setY(cordsCursor.getFloat(2));
            j++;
        }



        return points;
    }

    public void clearCords() {
        db.execSQL("DROP TABLE IF EXISTS graphic");
        createTable();
    }

    @Override
    public void finalize() throws Throwable {
        if(db != null && db.isOpen()) {
            db.close();
        }

        super.finalize();
    }


}
