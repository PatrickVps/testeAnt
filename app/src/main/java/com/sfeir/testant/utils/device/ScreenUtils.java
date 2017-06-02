package com.sfeir.testant.utils.device;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.admin.DeviceAdminReceiver;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.PowerManager;
import android.os.StrictMode;
import android.os.SystemClock;
import android.provider.Settings;
import android.view.Display;
import android.view.WindowManager;

import java.lang.reflect.Method;

import static android.content.Context.POWER_SERVICE;

/**
 * Created by patrickvongpraseuth on 31/05/2017.
 */

public class ScreenUtils {



    public static void turnOnScreen(Activity activity){
        PowerManager.WakeLock screenLock =    ((PowerManager)activity.getSystemService(POWER_SERVICE)).newWakeLock(
                PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP, "TAG");
        screenLock.acquire();

        //later
        screenLock.release();
    }

    public static void turnOffScreen(Activity activity){
        Settings.System.putInt(activity.getContentResolver(), Settings.System.SCREEN_OFF_TIMEOUT, 1000);
    }

    private static int RESULT_ENABLE = 1;

    public static void lockScreen(Activity activity){

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        DevicePolicyManager deviceManger = (DevicePolicyManager) activity.getSystemService(Context.DEVICE_POLICY_SERVICE);

        try {
            //deviceManger.resetPassword("4279",0);
            deviceManger.lockNow();
            activity.finish();
        } catch (SecurityException e) {
            Intent it = new         Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
            it.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, new ComponentName(activity, DeviceAdminReceiver.class));
            activity.startActivityForResult(it, 0);
        }

    }

    public static void unlockScreen(Activity activity){
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON|
                WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD|
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED|
                WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
    }

    public static void rotateScreen(Activity activity){
        if(activity.getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        } else {
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }
}
