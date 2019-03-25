package com.example.runner_game;

import android.app.Activity;
import android.content.Intent;
import android.hardware.Camera;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    //ImageView imageView;
    Camera camera;
    FrameLayout frameLayout;
    ShowCamera showCamera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        frameLayout = (FrameLayout)findViewById(R.id.frameLayout);
    }

    public void startGame(View view)
    {
        int cameraCount = camera.getNumberOfCameras();
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();

        for(int camIdx = 0; camIdx < cameraCount; camIdx++)     //Camera works but does not face front. FIX
        {
            camera.getCameraInfo(camIdx, cameraInfo);

            if(cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT)
            {
                try
                {
                    camera = camera.open();
                    showCamera = new ShowCamera(this, camera);
                    frameLayout.addView(showCamera);
                }
                catch(Exception e)
                {
                    //kakka
                }
            }
        }
        /*Intent intent = new Intent(this, StartGame.class);
        startActivity(intent);
        finish();*/
    }
}
