package com.example.flashlightutils;

import android.content.Context;
import android.content.pm.FeatureInfo;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.util.Log;

public class FlashUtils {
    private CameraManager manager;//通过CameraManager控制闪光灯
    private Camera mCamera = null;
    private final Context context;
    private final Camera.Parameters parameters = null;
    public static boolean status = false; // 定义开关状态，状态为false，打开状态，状态为true，关闭状态

    FlashUtils(Context context){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            manager = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);
        }
        this.context = context;
    }
    //打开手电筒
    public void open() {
        if(status){//如果已经是打开状态，不需要打开
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            try {
                manager.setTorchMode("0", true);
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
        } else {
            PackageManager packageManager = context.getPackageManager();
            FeatureInfo[] features = packageManager.getSystemAvailableFeatures();
            for (FeatureInfo featureInfo : features) {
                if (PackageManager.FEATURE_CAMERA_FLASH.equals(featureInfo.name)) { // 判断设备是否支持闪光灯
                    if (null == mCamera) {
                        mCamera = Camera.open();
                    }
                    Camera.Parameters parameters = mCamera.getParameters();
                    parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                    mCamera.setParameters(parameters);
                    mCamera.startPreview();
                }
            }
        }
        status = true;//记录手电筒状态为打开
    }

    //关闭手电筒
    public void close() {
        if(!status){//如果已经是关闭状态，不需要打开
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            try {
                manager.setTorchMode("0", false);
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
        } else {
            if (mCamera != null) {
                mCamera.stopPreview();
                mCamera.release();
                mCamera = null;
            }
        }
        status = false;//记录手电筒状态为关闭
    }

    //改变手电筒状态
    public void converse(){
        if(status){
            close();
        }else{
            open();
        }
    }
}
