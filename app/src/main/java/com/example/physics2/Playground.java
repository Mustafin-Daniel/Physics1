package com.example.physics2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.Iterator;

public class Playground extends View {
    private ArrayList<ObstacleLine> obstaclesToDraw;
    private ArrayList<Circle> circlesToDraw;

    public void setObstacles(ArrayList<ObstacleLine> obstacles) {
        this.obstaclesToDraw = obstacles;
        invalidate();  // Trigger the view to redraw
    }

    public void setCircles(ArrayList<Circle> circlesToDraw) {
        this.circlesToDraw = circlesToDraw;
        invalidate();
    }

    public ArrayList<Circle> getCirclesToDraw() {
        return circlesToDraw;
    }

    public Playground(Context context) {
        super(context);
    }

    public Playground(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Playground(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    public void removeOld() {
        if (circlesToDraw == null) return;

        Iterator<Circle> iterator = circlesToDraw.iterator();
        while (iterator.hasNext()) {
            Circle circle = iterator.next();
            if (circle.getAge() == 0) {
                iterator.remove();
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for (ObstacleLine obstacle : obstaclesToDraw){
            canvas.drawLine(obstacle.getPosXStart(),obstacle.getPosYStart(),obstacle.getPosXEnd(),obstacle.getPosYEnd(),obstacle.getPaint());
        }

        for (Circle circle : circlesToDraw){
            canvas.drawCircle(circle.getPosX(),circle.getPosY(),circle.getRadius(), circle.getPaint());
        }
    }


}
