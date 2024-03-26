package com.example.physics2;

import static java.lang.Math.asin;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;
import static java.lang.Math.toDegrees;

import android.content.Context;
import android.graphics.Paint;
import android.view.View;

public class ObstacleLine extends View {
    private float posXStart;
    private float posYStart;
    private float posXEnd;
    private float posYEnd;
    private float length;
    private float angle;
    private float dampCol;
    private float thickness = 7.5F;
    private Paint paint;

    public ObstacleLine(Context context, float posXStart, float posYStart, float posXEnd, float posYEnd, int color, float dampCol) {
        super(context);
        this.dampCol = dampCol;
        paint = new Paint();
        paint.setColor(color);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(7.5F);
        this.posXStart = posXStart;
        this.posYStart = posYStart;
        this.posXEnd = posXEnd;
        this.posYEnd = posYEnd;
        length = (float) sqrt((posXEnd-posXStart)*(posXEnd-posXStart) + (posYEnd-posYStart)*(posYEnd-posYStart));
        angle= (float) toDegrees(asin((posXEnd-posXStart)/length));
    }
    public ObstacleLine(Context context, float posXStart, float posYStart, float posXEnd, float posYEnd, int color, float dampCol, float thickness) {
        super(context);
        this.dampCol = dampCol;
        paint = new Paint();
        paint.setColor(color);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(thickness);
        this.posXStart = posXStart;
        this.posYStart = posYStart;
        this.posXEnd = posXEnd;
        this.posYEnd = posYEnd;
        length = (float) sqrt((posXEnd-posXStart)*(posXEnd-posXStart) + (posYEnd-posYStart)*(posYEnd-posYStart));
        angle= (float) toDegrees(asin((posXEnd-posXStart)/length));
    }

    public Paint getPaint() {
        return paint;
    }

    public float getThickness() {
        return thickness;
    }

    public float getPosXStart() {
        return posXStart;
    }

    public float getPosYStart() {
        return posYStart;
    }

    public float getPosXEnd() {
        return posXEnd;
    }

    public float getDampCol() {
        return dampCol;
    }

    public float getPosYEnd() {
        return posYEnd;
    }

    public float getLength() {
        return length;
    }

    public float getAngle() {return angle; }
}
