package com.example.runner_game;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Size;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.io.File;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Camera camera;
    FrameLayout frameLayout;
    ShowCamera showCamera;

    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        frameLayout = (FrameLayout)findViewById(R.id.frameLayout);

        imageView = (ImageView)findViewById(R.id.imageButton);
        ActivityCompat.requestPermissions(this,
                new String[] { Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                0);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
    {
        if(requestCode == 0)
        {
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
            && grantResults[1] == PackageManager.PERMISSION_GRANTED)
            {
                // *hick*
            }
        }
    }

    public void TakePicture(View view)
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File file = Uri.fromFile(getOutputMediaFile());             //Uuuuuuuuurgh
    }

    public void startGame(View view)
    {
        /*Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 0);*/

        int cameraCount = camera.getNumberOfCameras();
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();

        for(int camIdx = 0; camIdx < cameraCount; camIdx++)
        {
            camera.getCameraInfo(camIdx, cameraInfo);

            if(cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT)
            {
                try
                {
                    camera = camera.open(camIdx);

                    Camera.Parameters params = camera.getParameters();
                    //camera.startPreview();

                    List<Camera.Size> sizes = params.getSupportedPictureSizes();
                    //List<Camera.Size> sizes = params.getSupportedPreviewSizes();
                    Camera.Size size = camera.new Size(0, 0);       //Magic temporary numbers
                    Display d = ((WindowManager)getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();

                    //camera.stopPreview();

                    for(Camera.Size s : sizes)
                    {
                        if(s.width == d.getWidth() && s.height == d.getHeight())
                        {
                            size.width = s.width / 2;
                            size.height = s.height / 2;
                            break;
                        }
                    }

                    params.setPictureSize(size.width, size.height);
                    //params.setPreviewSize(size.width, size.height);

                    try
                    {
                        camera.setParameters(params);                      //This is where it fails

                        //camera.startPreview();
                        showCamera = new ShowCamera(this, camera);
                        frameLayout.addView(showCamera);
                    }
                    catch(Exception e)
                    {
                        //showCamera = new ShowCamera(this, camera);
                        //frameLayout.addView(showCamera);
                    }
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

    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bitmap = (Bitmap)data.getExtras().get("data");
        imageView.setImageBitmap(bitmap);
    }*/
}
