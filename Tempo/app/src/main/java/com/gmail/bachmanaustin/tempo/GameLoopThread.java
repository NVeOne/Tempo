package com.gmail.bachmanaustin.tempo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.SurfaceHolder;

public class GameLoopThread extends Thread {
    private SurfaceHolder holder;
    //static final long FPS = 10;
    //private GameSurfaceView view;
    private boolean running = false;
    private boolean paused = true;

    private Game game;


    GameLoopThread(Context context, SurfaceHolder holder){
        this.holder = holder;
        this.game = new Game(context);
    }
    public Game getGame(){
        return game;
    }

    /*public void setRunning(boolean run) {
        running = run;
    }*/

    @Override
    public void run() {
       // long ticksPS = 1000 / FPS;
        //long startTime;
        //long sleepTime;
        while (running) {
            while (paused) {
                try{
                Thread.sleep(50L);
            }catch(InterruptedException ignore){
            }
        }
            Canvas canvas = null;
            //startTime = System.currentTimeMillis();
            try {
                canvas = holder.lockCanvas(null);
                draw(canvas);
            } finally {
                if (canvas != null) {
                    holder.unlockCanvasAndPost(canvas);
                }
            }
           // sleepTime = ticksPS - (System.currentTimeMillis() - startTime);
            /*try {
                if (sleepTime > 0)
                    sleep(sleepTime);
                else
                    sleep(10);
            } catch (Exception e) {
            }*/
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

    @Override
    public void draw(Canvas canvas){
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