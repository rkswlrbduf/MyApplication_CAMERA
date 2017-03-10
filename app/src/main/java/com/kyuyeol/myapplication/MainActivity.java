package com.kyuyeol.myapplication;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.hardware.Camera;
import android.support.annotation.RawRes;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.hardware.camera2.*;
import android.text.Layout;
import android.util.Log;
import android.view.SurfaceView;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private TextureView mCameraTextureView;
    private Preview mPreview;

    Activity mainActivity = this;

    private static final String TAG = "MAINACTIVITY";

    static final int REQUEST_CAMERA = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addContentView(new DrawOnTop(this),new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        mCameraTextureView = (TextureView) findViewById(R.id.cameraTextureView);
        //mCameraTextureView.setOpaque(false);
        mPreview = new Preview(this, mCameraTextureView);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CAMERA:
                for (int i = 0; i < permissions.length; i++) {
                    String permission = permissions[i];
                    int grantResult = grantResults[i];
                    if (permission.equals(Manifest.permission.CAMERA)) {
                        if(grantResult == PackageManager.PERMISSION_GRANTED) {
                            mCameraTextureView = (TextureView) findViewById(R.id.cameraTextureView);
                            mPreview = new Preview(mainActivity, mCameraTextureView);
                            Log.d(TAG,"mPreview set");
                        } else {
                            Log.d(TAG,"Should have camera permission to run");
                            finish();
                        }
                    }
                }
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPreview.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mPreview.onPause();
    }
}