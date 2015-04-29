package com.gmail.bachmanaustin.tempo;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
//import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


public class GameSurfaceView extends SurfaceView implements SurfaceHolder.Callback{
    private final GameLoopThread thread;
    private float time = 0;

    private Bitmap bg1 = BitmapFactory.decodeResource(getResources(), R.drawable.game_background);
    private Bitmap background = Bitmap.createScaledBitmap(bg1, 720, 1280, false);

    private Bitmap rn1 = BitmapFactory.decodeResource(getResources(), R.drawable.red_note);
    private Bitmap rednote = Bitmap.createScaledBitmap(rn1, 144, 144, false);
    private int redY = -144;

    private Bitmap gn1 = BitmapFactory.decodeResource(getResources(), R.drawable.green_note);
    private Bitmap grnnote = Bitmap.createScaledBitmap(gn1, 144, 144, false);
    private int greenY = -144;

    private Bitmap bn1 = BitmapFactory.decodeResource(getResources(), R.drawable.blue_note);
    private Bitmap blunote = Bitmap.createScaledBitmap(bn1, 144, 144, false);
    private int blueY = -144;

    private Bitmap yn1 = BitmapFactory.decodeResource(getResources(), R.drawable.yellow_note);
    private Bitmap yelnote = Bitmap.createScaledBitmap(yn1, 144, 144, false);
    private int yellowY = -144;

    private Bitmap b1 = BitmapFactory.decodeResource(getResources(), R.drawable.button);
    private Bitmap button1 = Bitmap.createScaledBitmap(b1, 144, 144, false);
    private Bitmap button2 = Bitmap.createScaledBitmap(b1, 144, 144, false);
    private Bitmap button3 = Bitmap.createScaledBitmap(b1, 144, 144, false);
    private Bitmap button4 = Bitmap.createScaledBitmap(b1, 144, 144, false);


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
            redY = -144;
        }
        redY += 45;

        if(time >= 2){
            if(greenY >= 1280){
                greenY = -144;
            }
            greenY += 45;
        }
        if(time >= 3){
            if(blueY >= 1280){
                blueY = -144;
            }
            blueY += 45;
        }
        if(time >= 5){
            if(yellowY >= 1280){
                yellowY = -144;
            }
            yellowY += 45;
        }

        //Buttons on bottom
        canvas.drawBitmap(button1, 72, 1024, null);
        canvas.drawBitmap(button2, 216, 1024, null);
        canvas.drawBitmap(button3, 360, 1024, null);
        canvas.drawBitmap(button4, 504, 1024, null);
    }
}
