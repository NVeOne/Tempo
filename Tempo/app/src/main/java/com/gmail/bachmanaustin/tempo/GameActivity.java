package com.gmail.bachmanaustin.tempo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
public class GameActivity extends Activity {

    private static final String TAG = "MainActivity";
    private GameLoopThread thread;


    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GameSurfaceView surfaceView = new GameSurfaceView(this, null);
        setContentView(surfaceView);
        thread = surfaceView.getRenderThread();
        thread.start();


    }


    @Override
    protected void onDestroy() {
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