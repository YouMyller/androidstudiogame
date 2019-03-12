package com.example.runner_game;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class BackgroundLoop
{
    private Bitmap img;
    private int x, y, dx;

    public BackgroundLoop(Bitmap res)
    {
        img = res;
        y = 200;
    }


    public void Draw(Canvas canvas, GameView gameView)
    {
        x += dx;

        if(x < -gameView.deviceWidth)
        {
            x = 0;
        }

        canvas.drawBitmap(img, x, y, null);

        if(x < 0)
        {
            canvas.drawBitmap(img, x + gameView.deviceWidth, y, null);
        }
    }

    public void SetVector(int dx)
    {
        this.dx = dx;
    }
}
