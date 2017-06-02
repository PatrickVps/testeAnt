package com.sfeir.testant.utils.device;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.util.Log;

/**
 * Created by patrickvongpraseuth on 31/05/2017.
 */

public class InstallUtils {


    public void installApp(){
    }

    public static void uninstallApp(Activity activity){
        Intent intent = new Intent(Intent.ACTION_DELETE);
        intent.setData(Uri.parse("package:com.saschaha.one"));
        activity.startActivity(intent);
    }

    public static void updateApp(Activity activity){
        UpdateApp updater = new UpdateApp();
        updater.setContext(activity.getApplicationContext());
        updater.execute("http://dl-xda.xposed.info/modules/com.saschaha.one_v37_b39374.apk");
    }

    public void pauseApp(){
    }

    public void killApp(){
    }

    public static void launchService(Activity activity){
        Intent i = activity.getPackageManager().getLaunchIntentForPackage("com.saschaha.one");
        activity.startActivity(i);
    }


    public void createFileSystem(String name, String path){}

    public void removeFileSystem(String path){}

}
