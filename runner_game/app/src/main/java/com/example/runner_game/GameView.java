package com.example.runner_game;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.support.annotation.MainThread;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.View;
import android.os.Handler;

public class GameView extends View {

    Handler handler;        //Needed for scheduling the Runnable
    Runnable runnable;
    final int updateMilliSeconds = 30;

    MainThread thread;
    //Bitmap background;
    BackgroundLoop bg;
    Display display;
    Point point;
    public int deviceWidth;
    public int deviceHeight;
    Rect rect;

    int bgXPos, bgYPos;


    Bitmap[] playerAnim;
    int playerFrame;

    int speed = 0;
    int gravity = 3;

    float playerXPos, playerYPos;

    public GameView(Context context)
    {
        super(context);
        handler = new Handler();
        runnable = new Runnable()
        {
            @Override
            public void run ()
            {
                invalidate();       //Calls onDraw()
            }
        };

        //background = BitmapFactory.decodeResource(getResources(), R.drawable.bg);
        bg = new BackgroundLoop(BitmapFactory.decodeResource(getResources(), R.drawable.bg));
        bg.SetVector(-5);
        //thread.setRunning(true);
        //thread.Start();

        display = ((Activity)getContext()).getWindowManager().getDefaultDisplay();
        point = new Point();
        display.getSize(point);
        deviceWidth = point.x;
        deviceHeight = point.y;
        rect = new Rect(0,0,deviceWidth, deviceHeight);

        playerAnim = new Bitmap[2];
        playerAnim[0] = BitmapFactory.decodeResource(getResources(), R.drawable.graphic);
        playerAnim[1] = BitmapFactory.decodeResource(getResources(), R.drawable.graphic2);

        playerXPos = deviceWidth/2 - playerAnim[0].getWidth()/2;        //Center of the screen
        playerYPos = deviceHeight/2 - playerAnim[0].getHeight()/2;      //(The inital position of
                                                                        //the player)
}

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);

        final float scaleFactorX = (float)getWidth()/(float)deviceWidth;
        final float scaleFactorY = (float)getHeight()/(float)deviceHeight;

        if(canvas != null)
        {
            final int savedState = canvas.save();
            canvas.scale(scaleFactorX, scaleFactorY);
            bg.Draw(canvas, this);
            canvas.restoreToCount(savedState);
        }

        if(playerFrame == 0)
        {
            playerFrame = 1;
        }
        else
        {
            playerFrame = 0;
        }

        //Player is kept inside the screen
        //HUOM HUOM tän vois tehä silleen että pelaaja tippuu aina tiettyyn kohtaan (maahan)
        if(playerYPos < deviceHeight / 2 - playerAnim[0].getHeight() || speed < 0)
        {
            speed += gravity;
            playerYPos += speed;
        }

        canvas.drawBitmap(playerAnim[playerFrame], playerXPos, playerYPos, null);

        handler.postDelayed(runnable, updateMilliSeconds);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        int action = event.getAction();

        if(action == MotionEvent.ACTION_DOWN)       //Tap
        {
            speed = -30;
        }

        return true;        //True means the touch event is done and no further action is needed
    }
}
