package com.sfeir.testant.utils.device;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.os.PowerManager;
import android.os.SystemClock;
import android.provider.Settings;
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

    public static void lockScreen(Activity activity){


    }

    public static void unlockScreen(Activity activity){
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON|
                WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD|
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED|
                WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
    }

    public void rotateScreen(int degree){}
}
