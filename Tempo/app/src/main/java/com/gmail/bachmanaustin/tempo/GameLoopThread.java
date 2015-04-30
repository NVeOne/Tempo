package com.gmail.bachmanaustin.tempo;

import android.content.Context;
import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class GameLoopThread extends Thread {

    private SurfaceHolder holder;
    static final long FPS = 30;
    private boolean running = false;
    private boolean paused = true;


    private GameSurfaceView view;

    GameLoopThread(Context context, SurfaceHolder holder, GameSurfaceView view){
        this.holder = holder;
        this.view = view;
    }

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
                    sleep(30);
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

}