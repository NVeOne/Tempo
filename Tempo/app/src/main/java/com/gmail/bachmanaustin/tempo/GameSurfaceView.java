package com.gmail.bachmanaustin.tempo;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


public class GameSurfaceView extends SurfaceView implements SurfaceHolder.Callback{
    private final GameLoopThread thread;
    private float time = 0;
    private int life = 0;

    private Bitmap bg1 = BitmapFactory.decodeResource(getResources(), R.drawable.game_background);
    private Bitmap background = Bitmap.createScaledBitmap(bg1, 720, 1280, false);

    private Bitmap lb1 = BitmapFactory.decodeResource(getResources(), R.drawable.life_bar);
    private Bitmap lifebar = Bitmap.createScaledBitmap(lb1, 360, 75, false);
    private Bitmap bar1 = BitmapFactory.decodeResource(getResources(), R.drawable.bar);
    private Bitmap bar = Bitmap.createScaledBitmap(bar1, 10, 75, false);

    private Bitmap rn1 = BitmapFactory.decodeResource(getResources(), R.drawable.red_note);
    private Bitmap rednote = Bitmap.createScaledBitmap(rn1, 144, 144, false);
    private int redspeed = 0;
    private int redY = -144;

    private Bitmap gn1 = BitmapFactory.decodeResource(getResources(), R.drawable.green_note);
    private Bitmap grnnote = Bitmap.createScaledBitmap(gn1, 144, 144, false);
    private int grnspeed = 0;
    private int greenY = -144;

    private Bitmap bn1 = BitmapFactory.decodeResource(getResources(), R.drawable.blue_note);
    private Bitmap blunote = Bitmap.createScaledBitmap(bn1, 144, 144, false);
    private int bluspeed = 0;
    private int blueY = -144;

    private Bitmap yn1 = BitmapFactory.decodeResource(getResources(), R.drawable.yellow_note);
    private Bitmap yelnote = Bitmap.createScaledBitmap(yn1, 144, 144, false);
    private int yelspeed = 0;
    private int yellowY = -144;

    private Bitmap miss = BitmapFactory.decodeResource(getResources(), R.drawable.red_screen);
    private Bitmap b1 = BitmapFactory.decodeResource(getResources(), R.drawable.button);
    private Bitmap button1 = Bitmap.createScaledBitmap(b1, 144, 144, false);
    public boolean btn1press = false;
    private Bitmap button2 = Bitmap.createScaledBitmap(b1, 144, 144, false);
    public boolean btn2press = false;
    private Bitmap button3 = Bitmap.createScaledBitmap(b1, 144, 144, false);
    public boolean btn3press = false;
    private Bitmap button4 = Bitmap.createScaledBitmap(b1, 144, 144, false);
    public boolean btn4press = false;

    private int hit = 0;
    private double total = 385;
    public static double percent;

    public GameSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        SurfaceHolder sh = getHolder();
        sh.addCallback(this);

        thread = new GameLoopThread(context, sh, this);
    }
    public static double getPercent(){
        return percent * 100;
    }
    public void surfaceCreated(SurfaceHolder holder) {
        thread.unpause();
    }
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
    }
    public void surfaceDestroyed(SurfaceHolder holder) {
        thread.pause();
    }
    public GameLoopThread getRenderThread(){
        return thread;
    }
    public boolean onTouchEvent(MotionEvent e){
        int action = e.getAction();
        int x = (int) e.getRawX();
        int y = (int) e.getRawY();
        if(action == MotionEvent.ACTION_DOWN){
            if(x >= 72 && x < 216 && y >= 1024 && y <= 1280){
                btn1press = true;
            }
            if(x >= 216 && x < 360 && y >= 1024 && y <= 1280){
                btn2press = true;
            }
            if(x >= 360 && x < 504 && y >= 1024 && y <= 1280){
                btn3press = true;
            }
            if(x >= 504 && x < 648 && y >= 1024 && y <= 1280){
                btn4press = true;
            }
        }
        else{
            btn1press = false;
            btn2press = false;
            btn3press = false;
            btn4press = false;
        }
        return true;
    }
    private double space = 0.3667;
    private double start = 4.7671;
    private double notelength = 0.35;
    private int speed = 55;
    @Override
    protected void onDraw(Canvas canvas){
        if(canvas == null){
            return;
        }
        if(life == -10){
            Context mContext = getContext();
            Intent intent = new Intent(mContext, LoseGame.class);
            thread.pause();
            mContext.startActivity(intent);
        }
        //score
        percent = hit / total;
        //time in seconds from start
        time += 1/30.0;
        if(time >= 150.0){
            Context c = getContext();
            Intent i = new Intent(c, WinGame.class);
            thread.pause();
            c.startActivity(i);
        }
        //background
        canvas.drawColor(Color.BLACK);
        canvas.drawBitmap(background, 0, 0, null);

        //Notes
        canvas.drawBitmap(rednote, 72, redY, null);
        canvas.drawBitmap(grnnote, 216, greenY, null);
        canvas.drawBitmap(blunote, 360, blueY, null);
        canvas.drawBitmap(yelnote, 504, yellowY, null);

        //logic
        if(redY >= 1280){
            canvas.drawBitmap(miss, 0, 0, null);
            redspeed = 0;
            redY = -144;
            life--;
        }
        if(greenY >= 1280){
            canvas.drawBitmap(miss, 0, 0, null);
            grnspeed = 0;
            greenY = -144;
            life--;
        }
        if(blueY >= 1280){
            canvas.drawBitmap(miss, 0, 0, null);
            bluspeed = 0;
            blueY = -144;
            life--;
        }
        if(yellowY >= 1280){
            canvas.drawBitmap(miss, 0, 0, null);
            yelspeed = 0;
            yellowY = -144;
            life--;
        }
        redY += redspeed;
        greenY += grnspeed;
        blueY += bluspeed;
        yellowY += yelspeed;
        if(btn1press && redY >= 952 && redY <= 1168){
            redspeed = 0;
            redY = -144;
            if(life < 10) {
                life++;
            }
            hit++;
        }
        if(btn1press && (redY < 952 && redY != -144)){
            canvas.drawBitmap(miss, 0, 0, null);
            life--;
        }
        if(btn2press && greenY >= 952 && greenY <= 1168){
            grnspeed = 0;
            greenY = -144;
            if(life < 10) {
                life++;
            }
            hit++;
        }
        if(btn2press && (greenY < 952 && greenY != -144)){
            canvas.drawBitmap(miss, 0, 0, null);
            life--;
        }
        if(btn3press && blueY >= 952 && blueY <= 1168){
            bluspeed = 0;
            blueY = -144;
            if(life < 10) {
                life++;
            }
            hit++;
        }
        if(btn3press && (blueY < 952 && blueY != -144)){
            canvas.drawBitmap(miss, 0, 0, null);
            life--;
        }
        if(btn4press && yellowY >= 952 && yellowY <= 1168){
            yelspeed = 0;
            yellowY = -144;
            if(life < 10) {
                life++;
            }
            hit++;
        }
        if(btn4press && (yellowY < 952 && yellowY != -144)){
            canvas.drawBitmap(miss, 0, 0, null);
            life--;
        }

        //NOTE TIMING

        if(time >= start && time <= start + notelength){
            redspeed = speed;
        }
        if(time >= start + space && time <= start + space + notelength){
            grnspeed = speed;
        }
        if(time >= start + space * 2 && time <= start + space * 2 + notelength){
            bluspeed = speed;
        }
        if(time >= start + space * 4 && time <= start + space * 4 + notelength){
            yelspeed = speed;
        }
        if(time >= start + space * 5.5  && time <= start + space * 5.5 + notelength){
            redspeed = speed;
        }
        if(time >= start + space * 6.5 && time <= start + space * 6.5 + notelength){
            grnspeed = speed;
        }

        if(time >= start + space * 8 && time <= start + notelength + space * 8){
            redspeed = speed;
        }
        if(time >= start + space + space * 8 && time <= start + space + notelength + space * 8){
            grnspeed = speed;
        }
        if(time >= start + space * 2 + space * 8 && time <= start + space * 2 + notelength + space * 8){
            bluspeed = speed;
        }
        if(time >= start + space * 4 + space * 8 && time <= start + space * 4 + notelength + space * 8){
            yelspeed = speed;
        }
        if(time >= start + space * 5.5 + space * 8  && time <= start + space * 5.5 + notelength + space * 8){
            redspeed = speed;
        }
        if(time >= start + space * 6.5 + space * 8 && time <= start + space * 6.5 + notelength + space * 8){
            grnspeed = speed;
        }

        if(time >= start + space * 17 && time <= start + notelength + space * 17){
            yelspeed = speed;
        }
        if(time >= start + space * 18 && time <= start + notelength + space * 18){
            grnspeed = speed;
        }
        if(time >= start + space * 19 && time <= start + notelength + space * 19) {
            bluspeed = speed;
        }
        if(time >= start + space * 20 && time <= start + notelength + space * 20){
            redspeed = speed;
        }

        if(time >= start + space * 22.5 && time <= start + notelength + space * 22.5) {
            redspeed = speed;
        }
        if(time >= start + space * 23 && time <= start + notelength + space * 23) {
            bluspeed = speed;
        }
        if(time >= start + space * 23.5 && time <= start + notelength + space * 23.5) {
            grnspeed = speed;
        }
        if(time >= start + space * 24 && time <= start + notelength + space * 24) {
            yelspeed = speed;
        }

        if(time >= start + space * 25.5 && time <= start + notelength + space * 25.5) {
            redspeed = speed;
        }
        if(time >= start + space * 26 && time <= start + notelength + space * 26) {
            bluspeed = speed;
        }

        if(time >= start + space * 27.5 && time <= start + notelength + space * 27.5) {
            yelspeed = speed;
        }
        if(time >= start + space * 28 && time <= start + notelength + space * 28) {
            grnspeed = speed;
        }
        if(time >= start + space * 28.5 && time <= start + notelength + space * 28.5) {
            bluspeed = speed;
        }
        if(time >= start + space * 29 && time <= start + notelength + space * 29) {
            redspeed = speed;
        }

        if(time >= start + space * 30.5 && time <= start + notelength + space * 30.5) {
            yelspeed = speed;
        }
        if(time >= start + space * 31 && time <= start + notelength + space * 31) {
            grnspeed = speed;
        }

        if(time >= start + space * 32.5 && time <= start + notelength + space * 32.5) {
            redspeed = speed;
        }
        if(time >= start + space * 33 && time <= start + notelength + space * 33) {
            yelspeed = speed;
        }
        if(time >= start + space * 33.5 && time <= start + notelength + space * 33.5) {
            grnspeed = speed;
        }
        if(time >= start + space * 34 && time <= start + notelength + space * 34) {
            bluspeed = speed;
        }

        if(time >= start + space * 35.5 && time <= start + notelength + space * 35.5) {
            redspeed = speed;
        }
        if(time >= start + space * 36 && time <= start + notelength + space * 36) {
            yelspeed = speed;
        }

        if(time >= start + space * 37.5 && time <= start + notelength + space * 37.5) {
            grnspeed = speed;
        }
        if(time >= start + space * 38 && time <= start + notelength + space * 38) {
            bluspeed = speed;
        }
        if(time >= start + space * 38.5 && time <= start + notelength + space * 38.5) {
            redspeed = speed;
        }
        if(time >= start + space * 39 && time <= start + notelength + space * 39) {
            yelspeed = speed;
        }

        if(time >= start + space * 40.5 && time <= start + notelength + space * 40.5) {
            grnspeed = speed;
        }
        if(time >= start + space * 41 && time <= start + notelength + space * 41) {
            bluspeed = speed;
        }

        if(time >= start + space * 42.5 && time <= start + notelength + space * 42.5) {
            yelspeed = speed;
        }
        if(time >= start + space * 43 && time <= start + notelength + space * 43) {
            redspeed = speed;
        }
        if(time >= start + space * 43.5 && time <= start + notelength + space * 43.5) {
            bluspeed = speed;
        }
        if(time >= start + space * 44 && time <= start + notelength + space * 44) {
            grnspeed = speed;
        }

        if(time >= start + space * 45.5 && time <= start + notelength + space * 45.5) {
            yelspeed = speed;
        }
        if(time >= start + space * 46 && time <= start + notelength + space * 46) {
            redspeed = speed;
        }

        if(time >= start + space * 47.5 && time <= start + notelength + space * 47.5) {
            bluspeed = speed;
        }
        if(time >= start + space * 48 && time <= start + notelength + space * 48) {
            grnspeed = speed;
        }
        if(time >= start + space * 48.5 && time <= start + notelength + space * 48.5) {
            yelspeed = speed;
        }
        if(time >= start + space * 49 && time <= start + notelength + space * 49) {
            redspeed = speed;
        }

        if(time >= start + space * 50.5 && time <= start + notelength + space * 50.5) {
            bluspeed = speed;
        }
        if(time >= start + space * 51 && time <= start + notelength + space * 51) {
            grnspeed = speed;
        }

        if(time >= start + space * 53 && time <= start + notelength + space * 53) {
            redspeed = speed;
        }
        if(time >= start + space * 53.5 && time <= start + notelength + space * 53.5) {
            grnspeed = speed;
        }
        if(time >= start + space * 54 && time <= start + notelength + space * 54) {
            bluspeed = speed;
        }
        if(time >= start + space * 54.5 && time <= start + notelength + space * 54.5) {
            yelspeed = speed;
        }

        if(time >= start + space * 55.5 && time <= start + notelength + space * 55.5) {
            redspeed = speed;
        }
        if(time >= start + space * 56 && time <= start + notelength + space * 56) {
            grnspeed = speed;
        }
        if(time >= start + space * 56.5 && time <= start + notelength + space * 56.5) {
            bluspeed = speed;
        }
        if(time >= start + space * 57 && time <= start + notelength + space * 57) {
            yelspeed = speed;
        }

        if(time >= start + space * 59.5 && time <= start + notelength + space * 59.5) {
            yelspeed = speed;
        }
        if(time >= start + space * 60 && time <= start + notelength + space * 60) {
            bluspeed = speed;
        }
        if(time >= start + space * 60.5 && time <= start + notelength + space * 60.5) {
            grnspeed = speed;
        }
        if(time >= start + space * 61 && time <= start + notelength + space * 61) {
            redspeed = speed;
        }

        if(time >= start + space * 62 && time <= start + notelength + space * 62) {
            yelspeed = speed;
        }
        if(time >= start + space * 62.5 && time <= start + notelength + space * 62.5) {
            bluspeed = speed;
        }
        if(time >= start + space * 63 && time <= start + notelength + space * 63) {
            grnspeed = speed;
        }
        if(time >= start + space * 63.5 && time <= start + notelength + space * 63.5) {
            redspeed = speed;
        }

        if(time >= start + space * 66 && time <= start + notelength + space * 66) {
            redspeed = speed;
        }
        if(time >= start + space * 66.5 && time <= start + notelength + space * 66.5) {
            grnspeed = speed;
        }
        if(time >= start + space * 67 && time <= start + notelength + space * 67) {
            bluspeed = speed;
        }
        if(time >= start + space * 67.5 && time <= start + notelength + space * 67.5) {
            yelspeed = speed;
        }

        if(time >= start + space * 70 && time <= start + notelength + space * 70) {
            yelspeed = speed;
        }
        if(time >= start + space * 70.5 && time <= start + notelength + space * 70.5) {
            bluspeed = speed;
        }
        if(time >= start + space * 71 && time <= start + notelength + space * 71) {
            grnspeed = speed;
        }
        if(time >= start + space * 71.5 && time <= start + notelength + space * 71.5) {
            redspeed = speed;
        }

        if(time >= start + space * 72.5 && time <= start + notelength + space * 72.5) {
            yelspeed = speed;
        }
        if(time >= start + space * 73 && time <= start + notelength + space * 73) {
            bluspeed = speed;
        }
        if(time >= start + space * 73.5 && time <= start + notelength + space * 73.5) {
            grnspeed = speed;
        }
        if(time >= start + space * 74 && time <= start + notelength + space * 74) {
            redspeed = speed;
        }

        if(time >= start + space * 75 && time <= start + space * 75 + notelength){
            yelspeed = speed;
        }
        if(time >= start + space * 76 && time <= start + space * 76 + notelength){
            bluspeed = speed;
        }
        if(time >= start + space * 77 && time <= start + space * 77 + notelength){
            grnspeed = speed;
        }
        if(time >= start + space * 79 && time <= start + space * 79 + notelength){
            redspeed = speed;
        }
        if(time >= start + space * 80.5  && time <= start + space * 80.5 + notelength){
            yelspeed = speed;
        }
        if(time >= start + space * 81.5 && time <= start + space * 81.5 + notelength){
            bluspeed = speed;
        }

        if(time >= start + space * 83 && time <= start + space * 83 + notelength){
            yelspeed = speed;
        }
        if(time >= start + space * 84 && time <= start + space * 84 + notelength){
            bluspeed = speed;
        }
        if(time >= start + space * 85 && time <= start + space * 85 + notelength){
            grnspeed = speed;
        }
        if(time >= start + space * 87 && time <= start + space * 87 + notelength){
            redspeed = speed;
        }
        if(time >= start + space * 88.5  && time <= start + space * 88.5 + notelength){
            yelspeed = speed;
        }
        if(time >= start + space * 89.5 && time <= start + space * 89.5 + notelength){
            bluspeed = speed;
        }

        if(time >= start + space * 91 && time <= start + space * 91 + notelength){
            redspeed = speed;
        }
        if(time >= start + space * 92 && time <= start + space * 92 + notelength){
            grnspeed = speed;
        }
        if(time >= start + space * 93 && time <= start + space * 93 + notelength){
            bluspeed = speed;
        }
        if(time >= start + space * 95 && time <= start + space * 95 + notelength){
            yelspeed = speed;
        }
        if(time >= start + space * 96.5  && time <= start + space * 96.5 + notelength){
            redspeed = speed;
        }
        if(time >= start + space * 97.5 && time <= start + space * 97.5 + notelength){
            grnspeed = speed;
        }

        if(time >= start + space * 99 && time <= start + space * 99 + notelength){
            redspeed = speed;
        }
        if(time >= start + space * 100 && time <= start + space * 100 + notelength){
            grnspeed = speed;
        }
        if(time >= start + space * 101 && time <= start + space * 101 + notelength){
            bluspeed = speed;
        }
        if(time >= start + space * 103 && time <= start + space * 103 + notelength){
            yelspeed = speed;
        }
        if(time >= start + space * 104.5  && time <= start + space * 104.5 + notelength){
            redspeed = speed;
        }
        if(time >= start + space * 105.5 && time <= start + space * 105.5 + notelength){
            grnspeed = speed;
        }

        if(time >= start + space * 108 && time <= start + notelength + space * 108) {
            redspeed = speed;
        }
        if(time >= start + space * 108.5 && time <= start + notelength + space * 108.5) {
            grnspeed = speed;
        }
        if(time >= start + space * 109 && time <= start + notelength + space * 109) {
            bluspeed = speed;
        }

        if(time >= start + space * 110.5 && time <= start + notelength + space * 110.5) {
            redspeed = speed;
        }
        if(time >= start + space * 111 && time <= start + notelength + space * 111) {
            grnspeed = speed;
        }
        if(time >= start + space * 111.5 && time <= start + notelength + space * 111.5) {
            bluspeed = speed;
        }

        if(time >= start + space * 113.5 && time <= start + notelength + space * 113.5) {
            yelspeed = speed;
        }
        if(time >= start + space * 114 && time <= start + notelength + space * 114) {
            bluspeed = speed;
        }
        if(time >= start + space * 114.5 && time <= start + notelength + space * 114.5) {
            grnspeed = speed;
        }

        if(time >= start + space * 116 && time <= start + notelength + space * 116) {
            yelspeed = speed;
        }
        if(time >= start + space * 116.5 && time <= start + notelength + space * 116.5) {
            bluspeed = speed;
        }
        if(time >= start + space * 117 && time <= start + notelength + space * 117.5) {
            grnspeed = speed;
        }

        if(time >= start + space * 119 && time <= start + notelength + space * 119) {
            redspeed = speed;
        }
        if(time >= start + space * 119.5 && time <= start + notelength + space * 119.5) {
            grnspeed = speed;
        }
        if(time >= start + space * 120 && time <= start + notelength + space * 120) {
            bluspeed = speed;
        }

        if(time >= start + space * 123 && time <= start + notelength + space * 123) {
            redspeed = speed;
        }
        if(time >= start + space * 123.5 && time <= start + notelength + space * 123.5) {
            bluspeed = speed;
        }
        if(time >= start + space * 124 && time <= start + notelength + space * 124) {
            grnspeed = speed;
        }
        if(time >= start + space * 124.5 && time <= start + notelength + space * 124.5) {
            yelspeed = speed;
        }

        if(time >= start + space * 125.5 && time <= start + notelength + space * 125.5) {
            redspeed = speed;
        }
        if(time >= start + space * 126 && time <= start + notelength + space * 126) {
            bluspeed = speed;
        }

        if(time >= start + space * 127.5 && time <= start + notelength + space * 127.5) {
            yelspeed = speed;
        }
        if(time >= start + space * 128 && time <= start + notelength + space * 128) {
            grnspeed = speed;
        }
        if(time >= start + space * 128.5 && time <= start + notelength + space * 128.5) {
            bluspeed = speed;
        }
        if(time >= start + space * 129 && time <= start + notelength + space * 129) {
            redspeed = speed;
        }

        if(time >= start + space * 130.5 && time <= start + notelength + space * 130.5) {
            yelspeed = speed;
        }
        if(time >= start + space * 131 && time <= start + notelength + space * 131) {
            grnspeed = speed;
        }

        if(time >= start + space * 132.5 && time <= start + notelength + space * 132.5) {
            redspeed = speed;
        }
        if(time >= start + space * 133 && time <= start + notelength + space * 133) {
            yelspeed = speed;
        }
        if(time >= start + space * 133.5 && time <= start + notelength + space * 133.5) {
            grnspeed = speed;
        }
        if(time >= start + space * 134 && time <= start + notelength + space * 134) {
            bluspeed = speed;
        }

        if(time >= start + space * 135.5 && time <= start + notelength + space * 135.5) {
            redspeed = speed;
        }
        if(time >= start + space * 136 && time <= start + notelength + space * 136) {
            yelspeed = speed;
        }

        if(time >= start + space * 137.5 && time <= start + notelength + space * 137.5) {
            grnspeed = speed;
        }
        if(time >= start + space * 138 && time <= start + notelength + space * 138) {
            bluspeed = speed;
        }
        if(time >= start + space * 138.5 && time <= start + notelength + space * 138.5) {
            redspeed = speed;
        }
        if(time >= start + space * 139 && time <= start + notelength + space * 139) {
            yelspeed = speed;
        }

        if(time >= start + space * 140.5 && time <= start + notelength + space * 140.5) {
            grnspeed = speed;
        }
        if(time >= start + space * 141 && time <= start + notelength + space * 141) {
            bluspeed = speed;
        }

        if(time >= start + space * 142.5 && time <= start + notelength + space * 142.5) {
            yelspeed = speed;
        }
        if(time >= start + space * 143 && time <= start + notelength + space * 143) {
            redspeed = speed;
        }
        if(time >= start + space * 143.5 && time <= start + notelength + space * 143.5) {
            bluspeed = speed;
        }
        if(time >= start + space * 144 && time <= start + notelength + space * 144) {
            grnspeed = speed;
        }

        if(time >= start + space * 145.5 && time <= start + notelength + space * 145.5) {
            yelspeed = speed;
        }
        if(time >= start + space * 146 && time <= start + notelength + space * 146) {
            redspeed = speed;
        }

        if(time >= start + space * 147.5 && time <= start + notelength + space * 147.5) {
            bluspeed = speed;
        }
        if(time >= start + space * 148 && time <= start + notelength + space * 148) {
            grnspeed = speed;
        }
        if(time >= start + space * 148.5 && time <= start + notelength + space * 148.5) {
            yelspeed = speed;
        }
        if(time >= start + space * 149 && time <= start + notelength + space * 149) {
            redspeed = speed;
        }

        if(time >= start + space * 150.5 && time <= start + notelength + space * 150.5) {
            bluspeed = speed;
        }
        if(time >= start + space * 151 && time <= start + notelength + space * 151) {
            grnspeed = speed;
        }

        if(time >= start + space * 153 && time <= start + notelength + space * 153) {
            redspeed = speed;
        }
        if(time >= start + space * 153.5 && time <= start + notelength + space * 153.5) {
            grnspeed = speed;
        }
        if(time >= start + space * 154 && time <= start + notelength + space * 154) {
            bluspeed = speed;
        }
        if(time >= start + space * 154.5 && time <= start + notelength + space * 154.5) {
            yelspeed = speed;
        }

        if(time >= start + space * 155.5 && time <= start + notelength + space * 155.5) {
            redspeed = speed;
        }
        if(time >= start + space * 156 && time <= start + notelength + space * 156) {
            grnspeed = speed;
        }
        if(time >= start + space * 156.5 && time <= start + notelength + space * 156.5) {
            bluspeed = speed;
        }
        if(time >= start + space * 157 && time <= start + notelength + space * 157) {
            yelspeed = speed;
        }

        if(time >= start + space * 159.5 && time <= start + notelength + space * 159.5) {
            yelspeed = speed;
        }
        if(time >= start + space * 160 && time <= start + notelength + space * 160) {
            bluspeed = speed;
        }
        if(time >= start + space * 160.5 && time <= start + notelength + space * 160.5) {
            grnspeed = speed;
        }
        if(time >= start + space * 161 && time <= start + notelength + space * 161) {
            redspeed = speed;
        }

        if(time >= start + space * 162 && time <= start + notelength + space * 162) {
            yelspeed = speed;
        }
        if(time >= start + space * 162.5 && time <= start + notelength + space * 162.5) {
            bluspeed = speed;
        }
        if(time >= start + space * 163 && time <= start + notelength + space * 163) {
            grnspeed = speed;
        }
        if(time >= start + space * 163.5 && time <= start + notelength + space * 163.5) {
            redspeed = speed;
        }

        if(time >= start + space * 166 && time <= start + notelength + space * 166) {
            redspeed = speed;
        }
        if(time >= start + space * 166.5 && time <= start + notelength + space * 166.5) {
            grnspeed = speed;
        }
        if(time >= start + space * 167 && time <= start + notelength + space * 167) {
            bluspeed = speed;
        }
        if(time >= start + space * 167.5 && time <= start + notelength + space * 167.5) {
            yelspeed = speed;
        }

        if(time >= start + space * 170 && time <= start + notelength + space * 170) {
            yelspeed = speed;
        }
        if(time >= start + space * 170.5 && time <= start + notelength + space * 170.5) {
            bluspeed = speed;
        }
        if(time >= start + space * 171 && time <= start + notelength + space * 171) {
            grnspeed = speed;
        }
        if(time >= start + space * 171.5 && time <= start + notelength + space * 171.5) {
            redspeed = speed;
        }

        if(time >= start + space * 172.5 && time <= start + notelength + space * 172.5) {
            yelspeed = speed;
        }
        if(time >= start + space * 173 && time <= start + notelength + space * 173) {
            bluspeed = speed;
        }
        if(time >= start + space * 173.5 && time <= start + notelength + space * 173.5) {
            grnspeed = speed;
        }
        if(time >= start + space * 174 && time <= start + notelength + space * 174) {
            redspeed = speed;
        }

        if(time >= start + space * 177 && time <= start + space * 177 + notelength){
            yelspeed = speed;
        }
        if(time >= start + space * 178 && time <= start + space * 178 + notelength){
            bluspeed = speed;
        }
        if(time >= start + space * 179 && time <= start + space * 179 + notelength){
            grnspeed = speed;
        }
        if(time >= start + space * 181 && time <= start + space * 181 + notelength){
            redspeed = speed;
        }
        if(time >= start + space * 182.5  && time <= start + space * 182.5 + notelength){
            yelspeed = speed;
        }
        if(time >= start + space * 183.5 && time <= start + space * 183.5 + notelength){
            bluspeed = speed;
        }

        if(time >= start + space * 185 && time <= start + space * 185 + notelength){
            yelspeed = speed;
        }
        if(time >= start + space * 186 && time <= start + space * 186 + notelength){
            bluspeed = speed;
        }
        if(time >= start + space * 187 && time <= start + space * 187 + notelength){
            grnspeed = speed;
        }
        if(time >= start + space * 189 && time <= start + space * 189 + notelength){
            redspeed = speed;
        }
        if(time >= start + space * 190.5  && time <= start + space * 190.5 + notelength){
            yelspeed = speed;
        }
        if(time >= start + space * 191.5 && time <= start + space * 191.5 + notelength){
            bluspeed = speed;
        }

        if(time >= start + space * 193 && time <= start + space * 193 + notelength){
            redspeed = speed;
        }
        if(time >= start + space * 194 && time <= start + space * 194 + notelength){
            grnspeed = speed;
        }
        if(time >= start + space * 195 && time <= start + space * 195 + notelength){
            bluspeed = speed;
        }
        if(time >= start + space * 197 && time <= start + space * 197 + notelength){
            yelspeed = speed;
        }
        if(time >= start + space * 198.5  && time <= start + space * 198.5 + notelength){
            redspeed = speed;
        }
        if(time >= start + space * 199.5 && time <= start + space * 199.5 + notelength){
            grnspeed = speed;
        }

        if(time >= start + space * 201 && time <= start + space * 201 + notelength){
            redspeed = speed;
        }
        if(time >= start + space * 202 && time <= start + space * 202 + notelength){
            grnspeed = speed;
        }
        if(time >= start + space * 203 && time <= start + space * 203 + notelength){
            bluspeed = speed;
        }
        if(time >= start + space * 205 && time <= start + space * 205 + notelength){
            yelspeed = speed;
        }
        if(time >= start + space * 206.5  && time <= start + space * 206.5 + notelength){
            redspeed = speed;
        }
        if(time >= start + space * 207.5 && time <= start + space * 207.5 + notelength){
            grnspeed = speed;
        }

        if(time >= start + space * 210 && time <= start + notelength + space * 210) {
            redspeed = speed;
        }
        if(time >= start + space * 210.5 && time <= start + notelength + space * 210.5) {
            grnspeed = speed;
        }
        if(time >= start + space * 211 && time <= start + notelength + space * 211) {
            bluspeed = speed;
        }

        if(time >= start + space * 212.5 && time <= start + notelength + space * 212.5) {
            redspeed = speed;
        }
        if(time >= start + space * 213 && time <= start + notelength + space * 213) {
            grnspeed = speed;
        }
        if(time >= start + space * 213.5 && time <= start + notelength + space * 213.5) {
            bluspeed = speed;
        }

        if(time >= start + space * 215.5 && time <= start + notelength + space * 215.5) {
            yelspeed = speed;
        }
        if(time >= start + space * 216 && time <= start + notelength + space * 216) {
            bluspeed = speed;
        }
        if(time >= start + space * 216.5 && time <= start + notelength + space * 216.5) {
            grnspeed = speed;
        }

        if(time >= start + space * 218 && time <= start + notelength + space * 218) {
            yelspeed = speed;
        }
        if(time >= start + space * 218.5 && time <= start + notelength + space * 218.5) {
            bluspeed = speed;
        }
        if(time >= start + space * 219 && time <= start + notelength + space * 219) {
            grnspeed = speed;
        }

        if(time >= start + space * 221 && time <= start + notelength + space * 221) {
            redspeed = speed;
        }
        if(time >= start + space * 221.5 && time <= start + notelength + space * 221.5) {
            grnspeed = speed;
        }
        if(time >= start + space * 222 && time <= start + notelength + space * 222) {
            bluspeed = speed;
        }

        if(time >= start + space * 223.5 && time <= start + notelength + space * 223.5) {
            redspeed = speed;
        }
        if(time >= start + space * 224 && time <= start + notelength + space * 224) {
            grnspeed = speed;
        }
        if(time >= start + space * 224.5 && time <= start + notelength + space * 224.5) {
            bluspeed = speed;
        }

        if(time >= start + space * 227 && time <= start + notelength + space * 227) {
            redspeed = speed;
        }
        if(time >= start + space * 228.25 && time <= start + notelength + space * 228.25) {
            grnspeed = speed;
        }
        if(time >= start + space * 229.5 && time <= start + notelength + space * 229.5) {
            redspeed = speed;
        }
        if(time >= start + space * 230.75 && time <= start + notelength + space * 230.75) {
            grnspeed = speed;
        }
        if(time >= start + space * 232 && time <= start + notelength + space * 232) {
            redspeed = speed;
        }
        if(time >= start + space * 233.25 && time <= start + notelength + space * 233.25) {
            grnspeed = speed;
        }
        if(time >= start + space * 234.5 && time <= start + notelength + space * 234.5) {
            bluspeed = speed;
        }
        if(time >= start + space * 235.75 && time <= start + notelength + space * 235.75) {
            yelspeed = speed;
        }
        if(time >= start + space * 237 && time <= start + notelength + space * 237) {
            bluspeed = speed;
        }
        if(time >= start + space * 238.25 && time <= start + notelength + space * 238.25) {
            yelspeed = speed;
        }
        if(time >= start + space * 239.5 && time <= start + notelength + space * 239.5) {
            bluspeed = speed;
        }
        if(time >= start + space * 240.75 && time <= start + notelength + space * 240.75) {
            yelspeed = speed;
        }
        if(time >= start + space * 242 && time <= start + notelength + space * 242) {
            bluspeed = speed;
        }
        if(time >= start + space * 243.25 && time <= start + notelength + space * 243.25) {
            grnspeed = speed;
        }
        if(time >= start + space * 244.5 && time <= start + notelength + space * 244.5) {
            redspeed = speed;
        }
        if(time >= start + space * 245.75 && time <= start + notelength + space * 245.75) {
            grnspeed = speed;
        }
        if(time >= start + space * 247 && time <= start + notelength + space * 247) {
            bluspeed = speed;
        }
        if(time >= start + space * 248.25 && time <= start + notelength + space * 248.25) {
            yelspeed = speed;
        }
        if(time >= start + space * 249.5 && time <= start + notelength + space * 249.5) {
            bluspeed = speed;
        }
        if(time >= start + space * 250.75 && time <= start + notelength + space * 250.75) {
            grnspeed = speed;
        }
        if(time >= start + space * 252 && time <= start + notelength + space * 252) {
            redspeed = speed;
        }
        if(time >= start + space * 253.25 && time <= start + notelength + space * 253.25) {
            grnspeed = speed;
        }
        if(time >= start + space * 254.5 && time <= start + notelength + space * 254.5) {
            bluspeed = speed;
        }
        if(time >= start + space * 255.75 && time <= start + notelength + space * 255.75) {
            yelspeed = speed;
        }
        if(time >= start + space * 257 && time <= start + notelength + space * 257) {
            bluspeed = speed;
        }
        if(time >= start + space * 258.25 && time <= start + notelength + space * 258.25) {
            grnspeed = speed;
        }
        if(time >= start + space * 259.5 && time <= start + notelength + space * 259.5) {
            bluspeed = speed;
        }
        if(time >= start + space * 260.75 && time <= start + notelength + space * 260.75) {
            grnspeed = speed;
        }
        if(time >= start + space * 262 && time <= start + notelength + space * 262) {
            bluspeed = speed;
        }
        if(time >= start + space * 263.25 && time <= start + notelength + space * 263.25) {
            grnspeed = speed;
        }

        if(time >= start + space * 265.5 && time <= start + notelength + space * 265.5) {
            redspeed = speed;
        }
        if(time >= start + space * 266 && time <= start + notelength + space * 266) {
            grnspeed = speed;
        }
        if(time >= start + space * 266.5 && time <= start + notelength + space * 266.5) {
            bluspeed = speed;
        }
        if(time >= start + space * 267 && time <= start + notelength + space * 267) {
            yelspeed = speed;
        }

        if(time >= start + space * 268 && time <= start + notelength + space * 268) {
            redspeed = speed;
        }
        if(time >= start + space * 268.5 && time <= start + notelength + space * 268.5) {
            grnspeed = speed;
        }
        if(time >= start + space * 269 && time <= start + notelength + space * 269) {
            bluspeed = speed;
        }
        if(time >= start + space * 269.5 && time <= start + notelength + space * 269.5) {
            yelspeed = speed;
        }

        if(time >= start + space * 272 && time <= start + notelength + space * 272) {
            yelspeed = speed;
        }
        if(time >= start + space * 272.5 && time <= start + notelength + space * 272.5) {
            bluspeed = speed;
        }
        if(time >= start + space * 273 && time <= start + notelength + space * 273) {
            grnspeed = speed;
        }
        if(time >= start + space * 273.5 && time <= start + notelength + space * 273.5) {
            redspeed = speed;
        }

        if(time >= start + space * 274.5 && time <= start + notelength + space * 274.5) {
            yelspeed = speed;
        }
        if(time >= start + space * 275 && time <= start + notelength + space * 275) {
            bluspeed = speed;
        }
        if(time >= start + space * 275.5 && time <= start + notelength + space * 275.5) {
            grnspeed = speed;
        }
        if(time >= start + space * 276 && time <= start + notelength + space * 276) {
            redspeed = speed;
        }

        if(time >= start + space * 277 && time <= start + notelength + space * 277) {
            yelspeed = speed;
        }
        if(time >= start + space * 277.5 && time <= start + notelength + space * 277.5) {
            bluspeed = speed;
        }
        if(time >= start + space * 278 && time <= start + notelength + space * 278) {
            grnspeed = speed;
        }
        if(time >= start + space * 278.5 && time <= start + notelength + space * 278.5) {
            redspeed = speed;
        }

        if(time >= start + space * 281.5 && time <= start + space * 281.5 + notelength){
            yelspeed = speed;
        }
        if(time >= start + space * 282.5 && time <= start + space * 282.5 + notelength){
            bluspeed = speed;
        }
        if(time >= start + space * 283.5 && time <= start + space * 283.5 + notelength){
            grnspeed = speed;
        }
        if(time >= start + space * 285.5 && time <= start + space * 285.5 + notelength){
            redspeed = speed;
        }
        if(time >= start + space * 287  && time <= start + space * 287 + notelength){
            yelspeed = speed;
        }
        if(time >= start + space * 288 && time <= start + space * 288 + notelength){
            bluspeed = speed;
        }

        if(time >= start + space * 289.5 && time <= start + space * 289.5 + notelength){
            yelspeed = speed;
        }
        if(time >= start + space * 290.5 && time <= start + space * 290.5 + notelength){
            bluspeed = speed;
        }
        if(time >= start + space * 291.5 && time <= start + space * 291.5 + notelength){
            grnspeed = speed;
        }
        if(time >= start + space * 293.5 && time <= start + space * 293.5 + notelength){
            redspeed = speed;
        }
        if(time >= start + space * 295  && time <= start + space * 295 + notelength){
            yelspeed = speed;
        }
        if(time >= start + space * 296 && time <= start + space * 296 + notelength){
            bluspeed = speed;
        }

        if(time >= start + space * 297.5 && time <= start + space * 297.5 + notelength){
            redspeed = speed;
        }
        if(time >= start + space * 298.5 && time <= start + space * 298.5 + notelength){
            grnspeed = speed;
        }
        if(time >= start + space * 299.5 && time <= start + space * 299.5 + notelength){
            bluspeed = speed;
        }
        if(time >= start + space * 301.5 && time <= start + space * 301.5 + notelength){
            yelspeed = speed;
        }
        if(time >= start + space * 303  && time <= start + space * 303 + notelength){
            redspeed = speed;
        }
        if(time >= start + space * 304 && time <= start + space * 304 + notelength){
            grnspeed = speed;
        }

        if(time >= start + space * 305.5 && time <= start + space * 305.5 + notelength){
            redspeed = speed;
        }
        if(time >= start + space * 306.5 && time <= start + space * 306.5 + notelength){
            grnspeed = speed;
        }
        if(time >= start + space * 307.5 && time <= start + space * 307.5 + notelength){
            bluspeed = speed;
        }
        if(time >= start + space * 309.5 && time <= start + space * 309.5 + notelength){
            yelspeed = speed;
        }
        if(time >= start + space * 311  && time <= start + space * 311 + notelength){
            redspeed = speed;
        }
        if(time >= start + space * 312 && time <= start + space * 312 + notelength){
            grnspeed = speed;
        }

        if(time >= start + space * 314.5 && time <= start + notelength + space * 314.5) {
            redspeed = speed;
        }
        if(time >= start + space * 315 && time <= start + notelength + space * 315) {
            grnspeed = speed;
        }
        if(time >= start + space * 315.5 && time <= start + notelength + space * 315.5) {
            bluspeed = speed;
        }

        if(time >= start + space * 317 && time <= start + notelength + space * 317) {
            redspeed = speed;
        }
        if(time >= start + space * 317.5 && time <= start + notelength + space * 317.5) {
            grnspeed = speed;
        }
        if(time >= start + space * 318 && time <= start + notelength + space * 318) {
            bluspeed = speed;
        }

        if(time >= start + space * 320 && time <= start + notelength + space * 320) {
            yelspeed = speed;
        }
        if(time >= start + space * 320.5 && time <= start + notelength + space * 320.5) {
            bluspeed = speed;
        }
        if(time >= start + space * 321 && time <= start + notelength + space * 321) {
            grnspeed = speed;
        }

        if(time >= start + space * 322.5 && time <= start + notelength + space * 322.5) {
            yelspeed = speed;
        }
        if(time >= start + space * 323 && time <= start + notelength + space * 323) {
            bluspeed = speed;
        }
        if(time >= start + space * 323.5 && time <= start + notelength + space * 323.5) {
            grnspeed = speed;
        }

        if(time >= start + space * 325.5 && time <= start + notelength + space * 325.5) {
            redspeed = speed;
        }
        if(time >= start + space * 326 && time <= start + notelength + space * 326) {
            grnspeed = speed;
        }
        if(time >= start + space * 326.5 && time <= start + notelength + space * 326.5) {
            bluspeed = speed;
        }

        if(time >= start + space * 328 && time <= start + notelength + space * 328) {
            redspeed = speed;
        }
        if(time >= start + space * 328.5 && time <= start + notelength + space * 328.5) {
            grnspeed = speed;
        }
        if(time >= start + space * 329 && time <= start + notelength + space * 329) {
            bluspeed = speed;
        }

        if(time >= start + space * 332 && time <= start + notelength + space * 332) {
            redspeed = speed;
        }
        if(time >= start + space * 332.5 && time <= start + notelength + space * 332.5) {
            bluspeed = speed;
        }
        if(time >= start + space * 333 && time <= start + notelength + space * 333) {
            grnspeed = speed;
        }
        if(time >= start + space * 333.5 && time <= start + notelength + space * 333.5) {
            yelspeed = speed;
        }

        if(time >= start + space * 335 && time <= start + notelength + space * 335) {
            redspeed = speed;
        }
        if(time >= start + space * 335.5 && time <= start + notelength + space * 335.5) {
            bluspeed = speed;
        }

        if(time >= start + space * 337 && time <= start + notelength + space * 337) {
            yelspeed = speed;
        }
        if(time >= start + space * 337.5 && time <= start + notelength + space * 337.5) {
            grnspeed = speed;
        }
        if(time >= start + space * 338 && time <= start + notelength + space * 338) {
            bluspeed = speed;
        }
        if(time >= start + space * 338.5 && time <= start + notelength + space * 338.5) {
            redspeed = speed;
        }

        if(time >= start + space * 340 && time <= start + notelength + space * 340) {
            yelspeed = speed;
        }
        if(time >= start + space * 340.5 && time <= start + notelength + space * 340.5) {
            grnspeed = speed;
        }

        if(time >= start + space * 342 && time <= start + notelength + space * 342) {
            redspeed = speed;
        }
        if(time >= start + space * 342.5 && time <= start + notelength + space * 342.5) {
            yelspeed = speed;
        }
        if(time >= start + space * 343 && time <= start + notelength + space * 343) {
            grnspeed = speed;
        }
        if(time >= start + space * 343.5 && time <= start + notelength + space * 343.5) {
            bluspeed = speed;
        }

        if(time >= start + space * 345 && time <= start + notelength + space * 345) {
            redspeed = speed;
        }
        if(time >= start + space * 345.5 && time <= start + notelength + space * 345.5) {
            yelspeed = speed;
        }

        if(time >= start + space * 347 && time <= start + notelength + space * 347) {
            grnspeed = speed;
        }
        if(time >= start + space * 347.5 && time <= start + notelength + space * 347.5) {
            bluspeed = speed;
        }
        if(time >= start + space * 348 && time <= start + notelength + space * 348) {
            redspeed = speed;
        }
        if(time >= start + space * 348.5 && time <= start + notelength + space * 348.5) {
            yelspeed = speed;
        }

        if(time >= start + space * 350 && time <= start + notelength + space * 350) {
            grnspeed = speed;
        }
        if(time >= start + space * 350.5 && time <= start + notelength + space * 350.5) {
            bluspeed = speed;
        }

        if(time >= start + space * 352 && time <= start + notelength + space * 352) {
            yelspeed = speed;
        }
        if(time >= start + space * 352.5 && time <= start + notelength + space * 352.5) {
            redspeed = speed;
        }
        if(time >= start + space * 353 && time <= start + notelength + space * 353) {
            bluspeed = speed;
        }
        if(time >= start + space * 353.5 && time <= start + notelength + space * 353.5) {
            grnspeed = speed;
        }

        if(time >= start + space * 355 && time <= start + notelength + space * 355) {
            yelspeed = speed;
        }
        if(time >= start + space * 355.5 && time <= start + notelength + space * 355.5) {
            redspeed = speed;
        }

        if(time >= start + space * 357 && time <= start + notelength + space * 357) {
            bluspeed = speed;
        }
        if(time >= start + space * 357.5 && time <= start + notelength + space * 357.5) {
            grnspeed = speed;
        }
        if(time >= start + space * 358 && time <= start + notelength + space * 358) {
            yelspeed = speed;
        }
        if(time >= start + space * 358.5 && time <= start + notelength + space * 358.5) {
            redspeed = speed;
        }

        if(time >= start + space * 360 && time <= start + notelength + space * 360) {
            bluspeed = speed;
        }
        if(time >= start + space * 360.5 && time <= start + notelength + space * 360.5) {
            grnspeed = speed;
        }

        if(time >= start + space * 362.5 && time <= start + notelength + space * 362.5) {
            redspeed = speed;
        }
        if(time >= start + space * 363 && time <= start + notelength + space * 363) {
            grnspeed = speed;
        }
        if(time >= start + space * 363.5 && time <= start + notelength + space * 363.5) {
            bluspeed = speed;
        }
        if(time >= start + space * 364 && time <= start + notelength + space * 364) {
            yelspeed = speed;
        }

        if(time >= start + space * 365 && time <= start + notelength + space * 365) {
            redspeed = speed;
        }
        if(time >= start + space * 365.5 && time <= start + notelength + space * 365.5) {
            grnspeed = speed;
        }
        if(time >= start + space * 366 && time <= start + notelength + space * 366) {
            bluspeed = speed;
        }
        if(time >= start + space * 366.5 && time <= start + notelength + space * 366.5) {
            yelspeed = speed;
        }

        if(time >= start + space * 369 && time <= start + notelength + space * 369) {
            yelspeed = speed;
        }
        if(time >= start + space * 369.5 && time <= start + notelength + space * 369.5) {
            bluspeed = speed;
        }
        if(time >= start + space * 370 && time <= start + notelength + space * 370) {
            grnspeed = speed;
        }
        if(time >= start + space * 370.5 && time <= start + notelength + space * 370.5) {
            redspeed = speed;
        }

        if(time >= start + space * 371.5 && time <= start + notelength + space * 371.5) {
            yelspeed = speed;
        }
        if(time >= start + space * 372 && time <= start + notelength + space * 372) {
            bluspeed = speed;
        }
        if(time >= start + space * 372.5 && time <= start + notelength + space * 372.5) {
            grnspeed = speed;
        }
        if(time >= start + space * 373 && time <= start + notelength + space * 373) {
            redspeed = speed;
        }

        if(time >= start + space * 375.5 && time <= start + notelength + space * 375.5) {
            redspeed = speed;
        }
        if(time >= start + space * 376 && time <= start + notelength + space * 376) {
            grnspeed = speed;
        }
        if(time >= start + space * 376.5 && time <= start + notelength + space * 376.5) {
            bluspeed = speed;
        }
        if(time >= start + space * 377 && time <= start + notelength + space * 377) {
            yelspeed = speed;
        }

        if(time >= start + space * 378 && time <= start + notelength + space * 378) {
            redspeed = speed;
        }
        if(time >= start + space * 378.5 && time <= start + notelength + space * 378.5) {
            grnspeed = speed;
        }
        if(time >= start + space * 379 && time <= start + notelength + space * 379) {
            bluspeed = speed;
        }
        if(time >= start + space * 379.5 && time <= start + notelength + space * 379.5) {
            yelspeed = speed;
        }

        if(time >= start + space * 382 && time <= start + notelength + space * 382) {
            yelspeed = speed;
        }
        if(time >= start + space * 382.5 && time <= start + notelength + space * 382.5) {
            bluspeed = speed;
        }
        if(time >= start + space * 383 && time <= start + notelength + space * 383) {
            grnspeed = speed;
        }
        if(time >= start + space * 383.5 && time <= start + notelength + space * 383.5) {
            redspeed = speed;
        }

        if(time >= start + space * 384.5 && time <= start + notelength + space * 384.5) {
            yelspeed = speed;
        }
        if(time >= start + space * 385 && time <= start + notelength + space * 385) {
            bluspeed = speed;
        }
        if(time >= start + space * 385.5 && time <= start + notelength + space * 385.5) {
            grnspeed = speed;
        }
        if(time >= start + space * 386 && time <= start + notelength + space * 386) {
            redspeed = speed;
        }


        //Buttons on bottom, lifebar
        canvas.drawBitmap(lifebar, 180, 30, null);
        canvas.drawBitmap(bar, 355 + (life * 18), 30, null);
        canvas.drawBitmap(button1, 72, 1024, null);
        canvas.drawBitmap(button2, 216, 1024, null);
        canvas.drawBitmap(button3, 360, 1024, null);
        canvas.drawBitmap(button4, 504, 1024, null);
    }
}
