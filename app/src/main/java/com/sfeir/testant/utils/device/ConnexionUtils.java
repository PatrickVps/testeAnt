package com.sfeir.testant.utils.device;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.provider.Settings;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by patrickvongpraseuth on 31/05/2017.
 */

public class ConnexionUtils {

    public static void handleInternet(Activity activity, String source, boolean allow) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, NoSuchFieldException {
        if("WIFI".equals(source.toUpperCase())){
            WifiManager wifiManager = (WifiManager) activity.getSystemService(Context.WIFI_SERVICE);
            if(allow){
                wifiManager.reconnect();
            } else {
                wifiManager.disconnect();
            }
        }else{//3G
            setMobileDataEnabled(activity, allow);
        }
    }

    public void setMaxSpeedInternetConnexion(int speed){}

    ////////////////
    //TODO : A TESTER
    //https://yous.be/2013/12/07/how-to-check-and-toggle-wifi-or-3g-4g-state-in-android/
    ////////////////
    private static void setMobileDataEnabled(Context context, boolean enabled) throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        final ConnectivityManager conman = (ConnectivityManager)  context.getSystemService(Context.CONNECTIVITY_SERVICE);
        final Class conmanClass = Class.forName(conman.getClass().getName());
        final Field connectivityManagerField = conmanClass.getDeclaredField("mService");
        connectivityManagerField.setAccessible(true);
        final Object connectivityManager = connectivityManagerField.get(conman);
        final Class connectivityManagerClass =  Class.forName(connectivityManager.getClass().getName());
        final Method setMobileDataEnabledMethod = connectivityManagerClass.getDeclaredMethod("setMobileDataEnabled", Boolean.TYPE);
        setMobileDataEnabledMethod.setAccessible(true);

        setMobileDataEnabledMethod.invoke(connectivityManager, enabled);
    }

    public static void airplane(Activity activity ){

        //HS

        try{
            Process proc = Runtime.getRuntime()
                    .exec(new String[]{ "su","-c", "settings put global airplane_mode_on 1", "am broadcast -a android.intent.action.AIRPLANE_MODE" });
            proc.waitFor();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
