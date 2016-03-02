package com.example.asagir.syncadapterslab;

/**
 * Created by asagir on 3/2/16.
 */

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;


public class PlaceholderAuthenticatorService extends Service {
    // Instance field that stores the authenticator object
    private PlaceholderAuthenticator mAuthenticator;
    @Override
    public void onCreate() {
        // Create a new authenticator object
        mAuthenticator = new PlaceholderAuthenticator(this);
    }
    /*
     * When the system binds to this Service to make the RPC call
     * return the authenticator's IBinder.
     */
    @Override
    public IBinder onBind(Intent intent) {
        return mAuthenticator.getIBinder();
    }
}