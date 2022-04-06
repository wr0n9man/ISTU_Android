package com.example.calculatorlab.logic;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class Calculator {

    private static String DB_NAME = "calculator.db";

    protected final SQLiteDatabase db;

    public Calculator(Context context) {
        db = context.openOrCreateDatabase(DB_NAME, Context.MODE_PRIVATE, null);
    }
}
