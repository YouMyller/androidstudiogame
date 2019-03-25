package com.example.runner_game;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import java.io.ByteArrayOutputStream;
import java.io.File;

public class BackgroundLoop
{
    private Bitmap img;
    private Bitmap scaledImg;

    private int posX, posY, posDX;

    public BackgroundLoop(Bitmap res)
    {
        img = res;
        posY = 0;        //Please note that 0 is the top of the screen, > 0 is below

        int origWidth = img.getWidth();
        int origHeight = img.getHeight();
        final int destHeight = 1800;
        final int destWidth = 1800;

        scaledImg = Bitmap.createScaledBitmap(img, destWidth, destHeight, false);
    }


    public void Draw(Canvas canvas, GameView gameView)
    {
        posX += posDX;

        if(posX < -gameView.deviceWidth)
        {
            posX = 0;
        }

        canvas.drawBitmap(scaledImg, posX, posY, null);

        if(posX < 0)
        {
            canvas.drawBitmap(scaledImg, posX + gameView.deviceWidth, posY, null);
        }
    }

    public void SetVector(int dx)
    {
        this.posDX = dx;
    }
}
