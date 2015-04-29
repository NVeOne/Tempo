package com.gmail.bachmanaustin.tempo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.SurfaceHolder;

public class GameLoopThread extends Thread {
    private SurfaceHolder holder;
    static final long FPS = 10;
    private boolean running = false;
    private boolean paused = true;
    /*private Bitmap bg1 = BitmapFactory.decodeResource(getResources(), R.drawable.game_background);
    private Bitmap background = Bitmap.createScaledBitmap(bg1, 720, 1280, false);

    private Bitmap rn1 = BitmapFactory.decodeResource(getResources(), R.drawable.red_note);
    private Bitmap rednote = Bitmap.createScaledBitmap(rn1, 144, 144, false);
    private int redY = -144;

    private Bitmap b1 = BitmapFactory.decodeResource(getResources(), R.drawable.button);
    private Bitmap button1 = Bitmap.createScaledBitmap(b1, 144, 144, false);
    private Bitmap button2 = Bitmap.createScaledBitmap(b1, 144, 144, false);
    private Bitmap button3 = Bitmap.createScaledBitmap(b1, 144, 144, false);
    private Bitmap button4 = Bitmap.createScaledBitmap(b1, 144, 144, false);*/

    private GameSurfaceView view;

    GameLoopThread(Context context, SurfaceHolder holder, GameSurfaceView view){
        this.holder = holder;
        this.view = view;
        //this.game = new Game(context);
    }
    //public Game getGame(){
        //return game;
    //}

    @Override
    public void run() {
        long ticksPS = 1000 / FPS;
        long startTime;
        long sleepTime;
        while (running) {
            while (paused) {
                try{
                Thread.sleep(50L);
            }catch(InterruptedException ignore){
            }
        }
            Canvas canvas = null;
            startTime = System.currentTimeMillis();
            try {
                canvas = holder.lockCanvas(null);
                view.onDraw(canvas);
            } finally {
                if (canvas != null) {
                    holder.unlockCanvasAndPost(canvas);
                }
            }
            sleepTime = ticksPS - (System.currentTimeMillis() - startTime);
            try {
                if (sleepTime > 0)
                    sleep(sleepTime);
                else
                    sleep(10);
            } catch (Exception e) {
            }
        }
    }
    public void halt(){
        paused = true;
        running = false;
    }
    public void pause(){
        paused = true;
    }
    public void unpause(){
        paused = false;
    }

    @Override
    public synchronized void start(){
        super.start();
        running = true;
        paused = false;
    }

    /*private void draw(Canvas canvas){
        if(canvas == null){
            return;
        }
        canvas.drawColor(Color.RED);

        game.render(canvas);
    }*/
}