package com.gmail.bachmanaustin.tempo;


import android.content.Context;
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

    private Bitmap bg1 = BitmapFactory.decodeResource(getResources(), R.drawable.game_background);
    private Bitmap background = Bitmap.createScaledBitmap(bg1, 720, 1280, false);

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


    public GameSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        SurfaceHolder sh = getHolder();
        sh.addCallback(this);

        thread = new GameLoopThread(context, sh, this);
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

    @Override
    protected void onDraw(Canvas canvas){
        if(canvas == null){
            return;
        }
        //time in seconds from start
        time += 1/30.0;
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
        }
        if(greenY >= 1280){
            canvas.drawBitmap(miss, 0, 0, null);
            grnspeed = 0;
            greenY = -144;
        }
        if(blueY >= 1280){
            canvas.drawBitmap(miss, 0, 0, null);
            bluspeed = 0;
            blueY = -144;
        }
        if(yellowY >= 1280){
            canvas.drawBitmap(miss, 0, 0, null);
            yelspeed = 0;
            yellowY = -144;
        }
        redY += redspeed;
        greenY += grnspeed;
        blueY += bluspeed;
        yellowY += yelspeed;
        if(btn1press && redY >= 952 && redY <= 1168){
            redspeed = 0;
            redY = -144;
        }
        if(btn1press && (redY < 952 && redY != -144)){
            canvas.drawBitmap(miss, 0, 0, null);
        }
        if(btn2press && greenY >= 952 && greenY <= 1168){
            grnspeed = 0;
            greenY = -144;
        }
        if(btn2press && (greenY < 952 && greenY != -144)){
            canvas.drawBitmap(miss, 0, 0, null);
        }
        if(btn3press && blueY >= 952 && blueY <= 1168){
            bluspeed = 0;
            blueY = -144;
        }
        if(btn3press && (blueY < 952 && blueY != -144)){
            canvas.drawBitmap(miss, 0, 0, null);
        }
        if(btn4press && yellowY >= 952 && yellowY <= 1168){
            yelspeed = 0;
            yellowY = -144;
        }
        if(btn4press && (yellowY < 952 && yellowY != -144)){
            canvas.drawBitmap(miss, 0, 0, null);
        }

        //NOTE TIMING

        if(time >= 5.1 && time <= 5.3){
            redspeed = 50;
        }
        if(time >= 5.3 && time <= 5.5){
            bluspeed = 50;
        }


        //Buttons on bottom
        canvas.drawBitmap(button1, 72, 1024, null);
        canvas.drawBitmap(button2, 216, 1024, null);
        canvas.drawBitmap(button3, 360, 1024, null);
        canvas.drawBitmap(button4, 504, 1024, null);
    }
}
