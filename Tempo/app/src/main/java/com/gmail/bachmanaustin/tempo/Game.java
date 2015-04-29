package com.gmail.bachmanaustin.tempo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;


public class Game {
    Context ctx;
    public Game (Context context){
        ctx = context;
    }
    public Bitmap bg1 = BitmapFactory.decodeResource(ctx.getResources(), R.drawable.game_background);
    public Bitmap background = Bitmap.createScaledBitmap(bg1, 720, 1280, false);

    public Bitmap rn1 = BitmapFactory.decodeResource(ctx.getResources(), R.drawable.red_note);
   /* public Bitmap rednote = Bitmap.createScaledBitmap(rn1, 144, 144, false);
    public int redY = -144;

    public Bitmap b1 = BitmapFactory.decodeResource(ctx.getResources(), R.drawable.button);
    public Bitmap button1 = Bitmap.createScaledBitmap(b1, 144, 144, false);
    public Bitmap button2 = Bitmap.createScaledBitmap(b1, 144, 144, false);
    public Bitmap button3 = Bitmap.createScaledBitmap(b1, 144, 144, false);
    public Bitmap button4 = Bitmap.createScaledBitmap(b1, 144, 144, false);*/

    public void render(Canvas canvas){
        canvas.drawBitmap(background, 0, 0, null);
        //canvas.drawBitmap(button1, 72, 1024, null);
    }
}
