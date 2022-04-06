package com.example.calculatorlab;

import static android.graphics.Color.*;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Canvas;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

public class GraphicActivity extends AppCompatActivity {

    private final int stepX = 30;
    private final int stepY = 30;

    private final float maxX = 10;
    private final float minX = -10;

    private final float maxY = 1;
    private final float minY = -1;

    private final float lengthX = Math.abs(maxX) + Math.abs(minX);

    private float scaleX = 0;
    private float scaleY = 0;

    private Canvas canvas;
    private TextView rangeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_graphic);

        rangeTextView = (TextView)findViewById(R.id.range);

        SurfaceView surface = (SurfaceView) findViewById(R.id.surface);
        surface.getHolder().addCallback(new SurfaceHolder.Callback() {

            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                canvas = holder.lockCanvas();

                scaleX = canvas.getWidth() / lengthX;
                scaleY = canvas.getHeight() / 6; // [-1,1];  1 - (-1) = 2

                canvas.drawColor(BLACK);

                rangeTextView.setText(String.format("[%d; %d]", Math.round(minX), Math.round(maxX)));

                drawGrid(stepX, stepY);
                drawSinXGraph();

                holder.unlockCanvasAndPost(canvas);
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            }


        });
    }

    public void exit(View view) {
        this.finish();
    }

    public void drawGrid(int stepX, int stepY) {

        int width = canvas.getWidth();
        int height = canvas.getHeight();

        Paint paint = new Paint();
        paint.setColor(0xFF696969);
        paint.setStrokeWidth(2);

        int midX = width / 2;
        int midY = height / 2;

        for(int i = 0; i < width; i+=stepX) {
            canvas.drawLine(midX + i, 0,  midX + i, height, paint);
            canvas.drawLine(midX - i, 0,  midX - i, height, paint);
        }

        for(int i = 0; i < height; i+=stepY) {
            canvas.drawLine(0, midY + i,  width, midY + i, paint);
            canvas.drawLine(0, midY - i,  width, midY - i, paint);
        }

        paint.setColor(RED);
        paint.setStrokeWidth(3);
        canvas.drawLine(0, height / 2,  width, height / 2, paint);
        canvas.drawLine(width / 2, 0,  width / 2, height, paint);
    }

    public void drawSinXGraph() {
        int width = canvas.getWidth();
        int height = canvas.getHeight();

        Paint paint = new Paint();
        paint.setColor(GREEN);
        paint.setStrokeWidth(5);

        float offsetX = -10;
        float offsetY = height / 2;

        for(float i = 0f; i < lengthX - 0.1f; i += 0.1f) {

            float x = i * scaleX;
            float y = offsetY - (float)Math.sin(i + offsetX) * scaleY;

            float x2 = (i + 0.1f) * scaleX;
            float y2 = offsetY - (float)Math.sin(i + 0.1 + offsetX) * scaleY;

            canvas.drawLine(x, y,  x2, y2, paint);
        }
    }
}