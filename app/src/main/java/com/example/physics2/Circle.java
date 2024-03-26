package com.example.physics2;

import android.graphics.Paint;

public class Circle {
    private Paint paint;
    private int radius;
    private float gravity = 9.8F;
    private float speedY, speedX;
    private float posY;
    private float posX;
    private float age=500;

    public Circle(int radius, float gravity, float speedY, float speedX, float posY, float posX, int color) {
        this.radius = radius;
        this.gravity = gravity;
        this.speedY = speedY;
        this.speedX = speedX;
        this.posY = posY;
        this.posX = posX;

        paint = new Paint();
        paint.setColor(color); // Set circle color
        paint.setStyle(Paint.Style.FILL);
    }

    public void tick(){
        posY += speedY/10;
        speedY = speedY + gravity/5;
        posX += speedX/10;
        age--;
    }

    public float getSpeedY() {
        return speedY;
    }

    public void setSpeedY(float speedY) {
        this.speedY = speedY;
    }

    public float getSpeedX() {
        return speedX;
    }

    public void setSpeedX(float speedX) {
        this.speedX = speedX;
    }

    public float getPosY() {
        return posY;
    }

    public void setPosY(float posY) {
        this.posY = posY;
    }

    public void setSpeed(float speedX, float speedY){
        this.speedY = speedY;
        this.speedX = speedX;
    }

    public float getPosX() {
        return posX;
    }

    public void setPosX(float posX) {
        this.posX = posX;
    }

    public int getRadius() {
        return radius;
    }

    public float getAge() {
        return age;
    }

    public Paint getPaint() {
        return paint;
    }

    public void setColor(int color){
        paint = new Paint();
        paint.setColor(color);
        paint.setStyle(Paint.Style.FILL);
    }


}
