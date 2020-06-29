package com.example.camertextureview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.CalendarView;
import android.widget.TextView;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private static final int MY_PERMISSIONS_REQUEST_CMERA = 1001;
    SurfaceView surfaceView;
    TextView textView;
    CameraSource cameraSource;


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case  MY_PERMISSIONS_REQUEST_CMERA:
            {
                if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    if (ActivityCompat.checkSelfPermission(this,Manifest.permission.CAMERA )!=PackageManager.PERMISSION_GRANTED){
                        return;
                    }
                    try {
                        cameraSource.start(surfaceView.getHolder());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        surfaceView=(SurfaceView) findViewById(R.id.surfaceView);
        textView =(TextView) findViewById(R.id.word);
        //Recognizer 를 생성하고, Detector.Processor 를 구현한 Proce
         final TextRecognizer textRecognizer =new TextRecognizer.Builder(getApplicationContext()).build();
        //
        if(!textRecognizer.isOperational()){
            Log.d("main","nonoo");

        }else{
            //카메라 이미지를 얻기 위한 CameraSoutce 객체 형성  이미지를 연속적으로 스트리밍한
            cameraSource = new CameraSource.Builder(getApplicationContext(), textRecognizer)
                    .setFacing(CameraSource.CAMERA_FACING_BACK)
                    .setRequestedPreviewSize(500, 500)
                    .setRequestedFps(2.0f) //초당 2 프레임
                    .setAutoFocusEnabled(true)
                    .build();
            surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
                @Override
                public void surfaceCreated(SurfaceHolder holder) {
                    try {
                        //permissionCamera();
//                        if(ActivityCompat.checkSelfPermission(getApplicationContext(),Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
//
//                            ActivityCompat.requestPermissions(MainActivity.this,new String []{Manifest.permission.CAMERA},MY_PERMISSIONS_REQUEST_CMERA);
//                            return;
//                        }
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (ActivityCompat.checkSelfPermission(getApplicationContext(),Manifest.permission.CAMERA )!=PackageManager.PERMISSION_GRANTED) {
                                //사용자가 허가를 할때까지 기다리게 하는 그런 설명이 필요할때
                                ActivityCompat.requestPermissions(MainActivity.this,new String []{Manifest.permission.CAMERA},MY_PERMISSIONS_REQUEST_CMERA);
                              return;


                            }
                        }
                        cameraSource.start(surfaceView.getHolder());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

                }


                @Override
                public void surfaceDestroyed(SurfaceHolder holder) {
                    cameraSource.stop();

                }
            });

            textRecognizer.setProcessor(new Detector.Processor<TextBlock>() {
                @Override
                public void release() {

                }

                @Override
                public void receiveDetections(Detector.Detections<TextBlock> detections) {
                    final SparseArray<TextBlock> items =detections.getDetectedItems();

                    String [] lang={"ko-Kore"};
                    if(items.size() !=0){
                        textView.post(new Runnable() {
                            @Override
                            public void run() {
                                StringBuilder stringBuilder=new StringBuilder();
                                for(int i=0; i<items.size();i++){
                                    TextBlock item=items.valueAt(i);
                                    stringBuilder.append(item.getValue());
                                    stringBuilder.append('\n');
                                }
                                textView.setText(stringBuilder.toString());


                            }
                        });
                    }

                }
            });

        }
    }
    public void permissionCamera(){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                //사용자가 허가를 할때까지 기다리게 하는 그런 설명이 필요할때
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {


                } else { //설명 필요 없이 그냥 requeset 요청

                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);


                }

            }

        }

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        cameraSource.release();
    }
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        switch (requestCode) {
//            case MY_PERMISSIONS_REQUEST_CMERA: {
//
//                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    // permission was granted, yay! Do the
//                    // contacts-related task you need to do
//                        return;
//                }
//
//                try {
//                        cameraSource.start(surfaceView.getHolder());
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//            }
//
//
//        }
//
//    }
}
