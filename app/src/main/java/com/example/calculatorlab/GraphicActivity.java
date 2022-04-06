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

import com.example.calculatorlab.data.Point;
import com.example.calculatorlab.logic.Graphic;

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
    private TextView extremesTextView;

    private Graphic graphic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graphic);

        rangeTextView = (TextView)findViewById(R.id.range);
        extremesTextView = (TextView)findViewById(R.id.extremes);
        createSurface();

        graphic = new Graphic(getBaseContext());
    }

    public void createSurface() {
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
                drawExtremes();

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
        graphic.clearCords();

        int width = canvas.getWidth();
        int height = canvas.getHeight();

        Paint paint = new Paint();
        paint.setColor(GREEN);
        paint.setStrokeWidth(5);

        float offsetX = -10;
        float offsetY = height / 2;

        int length = Math.round((lengthX - 0.1f) * 10);

        Point [] points = new Point[length];
        for(int i = 0; i < length; i++) {
            points[i] = new Point();
        }

        int j = 0;
        //for(float i = 0f; i < lengthX - 0.1f; i += 0.1f, j++) {
        for(int i = 0; i < length; i ++, j++) {

            points[j].setX(i * 0.1f);
            points[j].setY((float)Math.sin(points[j].getX() + offsetX));

            float x = points[j].getX() * scaleX;
            float y = offsetY - points[j].getY() * scaleY;

            float x2 = (points[j].getX() + 0.1f) * scaleX;
            float y2 = offsetY - (float)Math.sin(points[j].getX() + 0.1 + offsetX) * scaleY;

            canvas.drawLine(x, y,  x2, y2, paint);
        }

        graphic.addCords(points);
    }

    public void drawExtremes() {
        final float E = 0.0012f;
        Point [] points = graphic.getCords();

        Paint paint = new Paint();
        paint.setColor(RED);
        paint.setStrokeWidth(15);

        float offsetY = canvas.getHeight() / 2;

        int j = 0;

        for(int i = 0; i < points.length; i++) {
            if(1 - Math.abs(points[i].getY()) <= E) {
                canvas.drawPoint(points[i].getX() * scaleX,
                                 offsetY - points[i].getY() * scaleY,
                                    paint);

                j++;
            }
        }

        extremesTextView.setText(String.format("Найдено %d экстремумов", j));
    }
}