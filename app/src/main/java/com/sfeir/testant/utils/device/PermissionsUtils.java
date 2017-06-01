package com.sfeir.testant.utils.device;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;

import com.sfeir.testant.enums.PermissionEnum;

import java.util.Map;

/**
 * Created by patrickvongpraseuth on 31/05/2017.
 */

public class PermissionsUtils {

    private Map<String, Boolean> localPermissions;

    /////////////////////////
    /////////////////////////
    // NOTE : HAVE TO ALLOW ALL PERMISSIONS IN MANIFEST
    // USE THIS LOCAL PERMISSIONS TO CHECK AND BEHAVE ?
    //https://inthecheesefactory.com/blog/things-you-need-to-know-about-android-m-permission-developer-edition/en
    /////////////////////////
    /////////////////////////


    public PermissionsUtils() {
        for (PermissionEnum perm :PermissionEnum.values()) {
            localPermissions.put(perm.name(), false);
        }
    }

    public void addPermission(String permission){
        localPermissions.put(permission, true);
    }

    public void removePermission(String permission){
        localPermissions.put(permission, false);
    }

    public boolean checkPermission(String permission){
        return localPermissions.get(permission);
    }







    public static void setParameter(Activity activity, String parameter, boolean allow){
        switch (parameter.toUpperCase()) {
            case "BLUETOOTH":
                BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                if (!allow && mBluetoothAdapter.isEnabled()) {
                    mBluetoothAdapter.disable();
                } else {
                    mBluetoothAdapter.enable();
                }
                break;
            case "WIFI":
                WifiManager wifiManager = (WifiManager) activity.getSystemService(Context.WIFI_SERVICE);
                wifiManager.setWifiEnabled(allow);
                break;
        }
    }


}
