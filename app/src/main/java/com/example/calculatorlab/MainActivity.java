package com.example.calculatorlab;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void toCalculator(View view) {
        Intent intent = new Intent(this, CalculatorActivity.class);
        startActivity(intent);
    }

    public void toGraphs(View view) {
        Intent intent = new Intent(this, GraphicActivity.class);
        startActivity(intent);
    }

    public void toExit(View view) {
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
    }
}