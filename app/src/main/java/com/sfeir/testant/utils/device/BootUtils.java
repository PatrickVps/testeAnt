package com.sfeir.testant.utils.device;

import android.app.Activity;
import android.app.AlarmManager;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.PowerManager;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by patrickvongpraseuth on 31/05/2017.
 */

public class BootUtils {

    public static void rebootPhone(Activity activity, boolean unlockSim) throws IOException, InterruptedException {
        try {
            Process proc = Runtime.getRuntime()
                    .exec(new String[]{ "su", "-c", "reboot" });
            proc.waitFor();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void upgradeOS(String version){}

    public static void downgradeOS(String version){}

    public static void getAlarms(Activity activity){
//        final String tag_alarm = "tag_alarm";
//        Uri uri = Uri.parse("content://com.android.alarmclock/alarm");
//        Cursor c = activity.getContentResolver().query(uri, null, null, null, null);
//        if (c != null) {
//            Log.i(tag_alarm, "no of records are " + c.getCount());
//            Log.i(tag_alarm, "no of columns are " + c.getColumnCount());
//
//            String names[] = c.getColumnNames();
//            for (String temp : names) {
//                System.out.println(temp);
//            }
//            if (c.moveToFirst()) {
//                do {
//                    for (int j = 0; j < c.getColumnCount(); j++) {
//                        Log.i(tag_alarm, c.getColumnName(j)
//                                + " which has value " + c.getString(j));
//                    }
//                } while (c.moveToNext());
//            }
//        } else {
//            Log.e("no alarm","test");
//        }

    }
}
