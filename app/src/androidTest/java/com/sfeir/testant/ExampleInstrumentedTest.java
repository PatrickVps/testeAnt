package com.sfeir.testant;

import android.content.Context;
import android.content.pm.PackageManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.telephony.TelephonyManager;
import android.util.Log;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.sfeir.testant", appContext.getPackageName());

        ////////////////
        //CHECK CAMERA AVAILABILITY
        ////////////////

        PackageManager pm = appContext.getPackageManager();

        assertTrue(pm.hasSystemFeature(PackageManager.FEATURE_CAMERA));


        ////////////////
        //GET CARRIER NAME
        ////////////////

        TelephonyManager manager = (TelephonyManager)appContext.getSystemService(Context.TELEPHONY_SERVICE);
        String carrierName = manager.getNetworkOperatorName();

        assertEquals("",carrierName);


        ////////////////
        //CHECK WIFI NAME
        ////////////////

        WifiManager wifiMgr = (WifiManager) appContext.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiMgr.getConnectionInfo();
        String name = wifiInfo.getSSID();

        assertEquals("\"SFEIR\"", name);

    }
}
