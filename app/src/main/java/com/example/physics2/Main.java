package com.example.physics2;

import static java.lang.Math.abs;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

import android.content.res.Resources;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.media.PlaybackParams;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;


public class Main extends AppCompatActivity {
    Playground playground;
    ObstacleLine celling, floor, left_wall, right_wall;
    Circle startCircle;
    ArrayList<ObstacleLine> obstacles = new ArrayList<>();
    ArrayList<Circle> circles = new ArrayList<>();
    private boolean isActive = true;

    float height = Resources.getSystem().getDisplayMetrics().heightPixels;;
    float width = Resources.getSystem().getDisplayMetrics().widthPixels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playground = findViewById(R.id.playground);

        startCircle = new Circle(5,9.8F, 1.F,10.F, height/2, 5.F, Color.RED);
        circles.add(startCircle);
        playground.setCircles(circles);

        celling = new ObstacleLine(this, 0.F, 100.F, 2000.F, 100.F, Color.BLACK, 1.01F, 10F);
        floor = new ObstacleLine(this,0.0F, height-200, 2000.F, height-200, Color.BLACK, 1.01F, 10F);
        left_wall = new ObstacleLine(this, 10.F, 100.F, 10.F, height-200, Color.BLACK, 1.01F);
        right_wall = new ObstacleLine(this, width-10, 100.F, width-10, height-200, Color.BLACK, 1.01F);
        ArrayList<ObstacleLine> obs = new ArrayList<>(Arrays.asList(celling, floor, left_wall, right_wall));
        obstacles.addAll(obs);

        playground.setObstacles(obstacles);
        playground.invalidate();
        colHandler.post(colLog);
        cannonHandler.post(cannonLog);
        updateHandler.post(updateRun);
        playground.invalidate();
        // The logic for
        /*
        circleDraw.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                float x = motionEvent.getX();
                float y = motionEvent.getY();

                circleDraw.setSpeed(((+x-circleDraw.getPosX())/5), (+y-circleDraw.getPosY())/5);

                return true;

            }
        });
         */
    }
    Handler updateHandler = new Handler();
    Runnable updateRun = new Runnable() {
        @Override
        public void run() {
            ArrayList<Circle> cs = playground.getCirclesToDraw();
            for (Circle c : cs) {
                c.tick();
            }
            playground.setCircles(cs);
            if (isActive) updateHandler.postDelayed(this, 10);
        }
    };

    Handler colHandler = new Handler();
    Runnable colLog = new Runnable() {
        @Override
        public void run() {
            playground.removeOld();
            for (ObstacleLine o : obstacles){
                for (Circle c : circles) {
                    if (isColliding(o,c)) {
                        MediaPlayer newMp = MediaPlayer.create(getApplicationContext(), R.raw.glasstap);

                        PlaybackParams params = new PlaybackParams();
                        params.setPitch((float) (0.5F * pow(2, (height - c.getPosY()) / 1000))); // Change the pitch level here (1.0 is normal pitch)

                        newMp.setPlaybackParams(params);
                        newMp.setVolume(5.0F, 5.0F);
                        newMp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer mediaPlayer) {
                                mediaPlayer.release(); // Release the MediaPlayer after the sound completes
                            }
                        });

                        newMp.start();
                        if (o.getAngle() == 90) {
                            c.setSpeedY(-c.getSpeedY() * o.getDampCol());
                        } else if (o.getAngle() == 0) {
                            c.setSpeedX(-c.getSpeedX() * o.getDampCol());
                        }
                    }
                }
            }
            if (isActive) colHandler.postDelayed(this, 10);
        }
    };

    Handler cannonHandler = new Handler();
    Runnable cannonLog = new Runnable() {
        @Override
        public void run() {
            Circle newCircle = new Circle(100,9.8F, (float) (1.F+generateRandomNumber(-100,200)),100.F+generateRandomNumber(1,100), height/2+generateRandomNumber(1,100)/10, 125.F+generateRandomNumber(1,100)/10, Color.BLACK+generateRandomNumber(0,-Color.BLACK-1));
            circles.add(newCircle);
            playground.setCircles(circles);
            if(isActive) cannonHandler.postDelayed(this, 1000);
        }
    };

    public boolean isColliding(ObstacleLine o, Circle c){
        float dot = (float) (( ((c.getPosX()-o.getPosXStart())*(o.getPosXEnd()-o.getPosXStart())) + ((c.getPosY()-o.getPosYStart())*(o.getPosYEnd()-o.getPosYStart())) ) / pow(o.getLength(),2));
        float closestX = o.getPosXStart() + (dot * (o.getPosXEnd()-o.getPosXStart()));
        float closestY = o.getPosYStart() + (dot * (o.getPosYEnd()-o.getPosYStart()));
        float distX = closestX - c.getPosX();
        float distY = closestY - c.getPosY();
        float distance = (float) sqrt( (distX*distX) + (distY*distY) );
        if (distance <= c.getRadius()+o.getThickness()) {
            return true;
        }
        return false;
    }

    public boolean isColliding(Circle c1, Circle c2){
        if (pow(pow(c1.getPosX()-c2.getPosX(),2)+pow(c1.getPosY(),c2.getPosY()), 0.5) < c1.getRadius()+c2.getRadius())
            return true;
        return false;
    }

    public static int generateRandomNumber(int a, int b) {
        Random random = new Random();
        return random.nextInt(b) + a;
    }

    @Override
    protected void onPause() {
        super.onPause();
        isActive = false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isActive = false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        isActive = true;
        colHandler.post(colLog);
        cannonHandler.post(cannonLog);
        updateHandler.post(updateRun);
    }

}
