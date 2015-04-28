package com.gmail.bachmanaustin.tempo;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by Owner on 4/28/2015.
 */
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

        thread = new GameLoopThread(context, sh);
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



}
