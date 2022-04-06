package com.example.calculatorlab;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.util.DisplayMetrics;
import android.app.AlertDialog;

import java.util.function.Function;

public class MainActivity extends AppCompatActivity {

    TextView firstNumberTextView;
    TextView secondNumberTextView;
    TextView resultTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firstNumberTextView = (TextView)findViewById(R.id.firstNumber);
        secondNumberTextView = (TextView)findViewById(R.id.secondNumber);
        resultTextView = (TextView)findViewById(R.id.result);

        checkSupported();
    }

    public void checkSupported() {
        DisplayMetrics display = getResources().getDisplayMetrics();
        boolean stop = true;
        if ((display.heightPixels>=1920 & display.widthPixels>=1080)||
                (display.heightPixels>=1080 & display.widthPixels>=1920)) {
            stop = false;
        }
        if (stop)
        {
            showAlert("Несовместимое устройство!");
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        }
    }

    public void showAlert(String text) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        builder.setTitle("Ошибка!")
                .setMessage(text)
                .setCancelable(false)
                .setNegativeButton("Выход",
                        (dialog, id) -> {
                            dialog.cancel();
                        });

        AlertDialog alert = builder.create();
        alert.show();
    }

    public double [] getOperands(View view) {

        try {
            double firstNumber = Integer.parseInt(firstNumberTextView.getText().toString());
            double secondNumber = Integer.parseInt(secondNumberTextView.getText().toString());

            return new double[]{firstNumber, secondNumber};
        }
        catch(Exception e) {
            throw e;
        }
    }

    public void Add(View view) {
        try {
            double [] operands = getOperands(view);
            double result = operands[0] + operands[1];
            resultTextView.setText(Double.toString(result));
        }
        catch(NumberFormatException e) {
            showAlert("Некорректно введены числа!");
        }
        catch (Exception e) {
            showAlert(e.getMessage());
        }
    }

    public void Sub(View view) {
        try {
            double [] operands = getOperands(view);
            double result = operands[0] - operands[1];
            resultTextView.setText(Double.toString(result));
        }
        catch(NumberFormatException e) {
            showAlert("Некорректно введены числа!");
        }
        catch (Exception e) {
            showAlert(e.getMessage());
        }
    }

    public void Mul(View view) {
        try {
            double [] operands = getOperands(view);
            double result = operands[0] * operands[1];
            resultTextView.setText(Double.toString(result));
        }
        catch(NumberFormatException e) {
            showAlert("Некорректно введены числа!");
        }
        catch (Exception e) {
            showAlert(e.getMessage());
        }
    }

    public void Div(View view) {
        try {
            double [] operands = getOperands(view);
            double result = operands[0] / operands[1];
            resultTextView.setText(Double.toString(result));
        }
        catch(NumberFormatException e) {
            showAlert("Некорректно введены числа!");
        }
        catch (Exception e) {
            showAlert(e.getMessage());
        }
    }
}