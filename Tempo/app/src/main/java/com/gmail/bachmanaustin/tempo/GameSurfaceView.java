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

    private Bitmap bg1 = BitmapFactory.decodeResource(getResources(), R.drawable.game_background);
    private Bitmap background = Bitmap.createScaledBitmap(bg1, 720, 1280, false);

    private Bitmap rn1 = BitmapFactory.decodeResource(getResources(), R.drawable.red_note);
    private Bitmap rednote = Bitmap.createScaledBitmap(rn1, 144, 144, false);
    private int redY = -144;

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
        canvas.drawColor(Color.BLACK);
        canvas.drawBitmap(background, 0, 0, null);
        canvas.drawBitmap(button1, 72, 1024, null);
        canvas.drawBitmap(button2, 216, 1024, null);
        canvas.drawBitmap(button3, 360, 1024, null);
        canvas.drawBitmap(button4, 504, 1024, null);
        canvas.drawBitmap(rednote, 72, redY, null);
        redY++;
    }

}
