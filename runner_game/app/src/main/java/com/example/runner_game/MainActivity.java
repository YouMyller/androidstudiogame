package com.example.runner_game;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Camera;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Size;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.util.List;

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
                    camera = camera.open(camIdx);

                    Camera.Parameters params = camera.getParameters();
                    List<Camera.Size> sizes = params.getSupportedPictureSizes();;
                    Camera.Size size = camera.new Size(0, 0);       //Magic temporary numbers
                    Display d = ((WindowManager)getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();

                    for(Camera.Size s : sizes)
                    {
                        //if(wanted resolution == size being iterated)
                        if(s.width == d.getWidth() && s.height == d.getHeight())
                        {
                            size.width = s.width / 2;
                            size.height = s.height / 2;
                            break;
                        }
                    }

                    params.setPictureSize(size.width, size.height);
                    camera.setParameters(params);

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
