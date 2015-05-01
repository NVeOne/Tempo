package com.gmail.bachmanaustin.tempo;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;

public class GameActivity extends Activity {

    private static final String TAG = "GameActivity";
    private GameLoopThread thread;
    MediaPlayer mp;


    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GameSurfaceView surfaceView = new GameSurfaceView(this, null);
        setContentView(surfaceView);
        mp = MediaPlayer.create(GameActivity.this, R.raw.wipeout);
        mp.start();
        thread = surfaceView.getRenderThread();
        thread.start();
    }
    @Override
    protected void onPause() {
        super.onPause();
        if (mp != null){
            mp.stop();
            if (isFinishing()){
                mp.stop();
                mp.release();
            }
        }
    }
    @Override
    protected void onDestroy() {
        mp.pause();
        super.onDestroy();
        Log.i(TAG, "destroying");

        thread.halt();
        boolean retry = true;
        while (retry) {
            try {
                thread.join();
                retry = false;
            } catch (InterruptedException e) {
            }
        }
    }
}