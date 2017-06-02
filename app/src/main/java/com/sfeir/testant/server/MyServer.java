package com.sfeir.testant.server;

/**
 * Created by patrickvongpraseuth on 31/05/2017.
 */
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.provider.Settings;
import android.text.format.Formatter;

import com.sfeir.testant.tests.MyTestFramework;
import com.sfeir.testant.utils.device.BootUtils;
import com.sfeir.testant.utils.device.ConnexionUtils;
import com.sfeir.testant.utils.device.InstallUtils;
import com.sfeir.testant.utils.device.MockUtils;
import com.sfeir.testant.utils.device.PermissionsUtils;
import com.sfeir.testant.utils.device.ScreenUtils;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import fi.iki.elonen.NanoHTTPD;

import static android.content.Context.WIFI_SERVICE;

public class MyServer extends NanoHTTPD {

    private final static int PORT = 8080;

    private Context context;

    public MyServer(Context context) throws IOException {
        super(PORT);
        start();

        this.context = context;

        WifiManager wm = (WifiManager) context.getSystemService(WIFI_SERVICE);
        String ip = Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());

        System.out.println( "\nRunning! Point your browers to http://"+ip+":8080/ \n" );
    }

    @Override
    public Response serve(final IHTTPSession session) {

        switch (session.getUri()){
            case "/runTests":
                String[] array = {"com.sfeir.testant.tests.Test2Class"};
                try {
                    MyTestFramework.runTest(array);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                }
                break;

            case "/turnOn":
                ((Activity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ScreenUtils.turnOnScreen((Activity) context);
                    }
                });
                break;

            case "/turnOff":
                ((Activity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ScreenUtils.turnOffScreen((Activity) context);
                    }
                });
                break;

            case "/unlock":
                ((Activity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ScreenUtils.unlockScreen((Activity) context);
                    }
                });
                break;

            case "/lock":
                ((Activity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ScreenUtils.lockScreen((Activity) context);
                    }
                });
                break;
            // /parameter?name=bluetooth&active=false
            case "/parameter":
                ((Activity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        PermissionsUtils.setParameter(
                                (Activity) context,
                                session.getParms().get("name"),
                                Boolean.parseBoolean(session.getParms().get("active")));
                    }
                });
                break;

            // /internet?mode=wifi&active=false
            case "/internet":
                ((Activity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {

                            ConnexionUtils.handleInternet(
                                    (Activity) context,
                                    session.getParms().get("mode"),
                                    Boolean.parseBoolean(session.getParms().get("active")));

                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        } catch (NoSuchMethodException e) {
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (NoSuchFieldException e) {
                            e.printStackTrace();
                        }
                    }
                });
                break;

            // /mock?class=WebserviceAPI&method=getCountry&args=FRANCE+LONDON&return=USA
            case "/mock":
                ((Activity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        /*MockUtils.mock(
                                (Activity) context,
                                session.getParms().get("name"),
                                Boolean.parseBoolean(session.getParms().get("active")));
                         */
                    }
                });
                break;
            case "/reboot":
                ((Activity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            BootUtils.rebootPhone(
                                    (Activity) context, false);
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }
                });
                break;

//            case "/airplane":
//                ((Activity) context).runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        ConnexionUtils.airplane((Activity) context);
//                    }
//                });
//                break;

            case "/rotate":
                ((Activity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ScreenUtils.rotateScreen((Activity) context);
                    }
                });
                break;


            case "/alarms":
                ((Activity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        BootUtils.getAlarms((Activity) context);
                    }
                });
                break;


            case "/updateApp":
                ((Activity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        InstallUtils.updateApp((Activity) context);
                    }
                });
                break;


            case "/removeApp":
                ((Activity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        InstallUtils.uninstallApp((Activity) context);
                    }
                });
                break;

            case "/launchService":
                ((Activity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        InstallUtils.launchService((Activity) context);
                    }
                });
                break;

        }

        String msg = "<html><body><h1>Hello server</h1>\n";
        msg += "<p>We serve " + session.getUri() + " !</p>";
        return new Response( msg + "</body></html>\n" );
    }
}