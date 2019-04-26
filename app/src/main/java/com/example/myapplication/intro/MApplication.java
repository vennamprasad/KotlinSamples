package com.example.myapplication.intro;

import android.app.Application;
import android.content.IntentFilter;
import android.net.ConnectivityManager;

public class MApplication extends Application {
    private static MApplication mInstance;
    NetworkCheck networkConnectvity;
    IntentFilter filter;
    private boolean isRegistered;

    public static MApplication getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        networkConnectvity = new NetworkCheck();
    }

    public void setConnectivityListener(NetworkCheck.ConnectivityReceiverListener listener) {
        NetworkCheck.connectivityReceiverListener = listener;
        registerReceiver(networkConnectvity, filter);
        isRegistered = true;
    }

    public void resetConnectivityListener(NetworkCheck.ConnectivityReceiverListener listener) {
        if (isRegistered) {
            isRegistered = false;
            NetworkCheck.connectivityReceiverListener = null;
            unregisterReceiver(networkConnectvity);
        }
    }
}