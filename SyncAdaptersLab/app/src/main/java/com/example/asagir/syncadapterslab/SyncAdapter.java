package com.example.asagir.syncadapterslab;

import android.accounts.Account;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SyncResult;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by asagir on 3/2/16.
 */
public class SyncAdapter extends AbstractThreadedSyncAdapter {
    private static String TAG = SyncAdapter.class.getCanonicalName();

    // Global variables
    // Define a variable to contain a content resolver instance
    ContentResolver mContentResolver;

    /**
     * Set up the sync adapter
     */
    public SyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
        /*
         * If your app uses a content resolver, get an instance of it
         * from the incoming Context
         */
        mContentResolver = context.getContentResolver();
    }


    /**
     * Set up the sync adapter. This form of the
     * constructor maintains compatibility with Android 3.0
     * and later platform versions
     */
    public SyncAdapter(
            Context context,
            boolean autoInitialize,
            boolean allowParallelSyncs) {
        super(context, autoInitialize, allowParallelSyncs);
        /*
         * If your app uses a content resolver, get an instance of it
         * from the incoming Context
         */
        mContentResolver = context.getContentResolver();

    }

    @Override
    public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider, SyncResult syncResult) {
        String data ="";
        Gson gson = new Gson();
        try {
            URL url = new URL("http://dev.markitondemand.com/MODApis/Api/v2/Quote/json?symbol=AAPL&callback=myFunction");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            InputStream inStream = connection.getInputStream();
            data = getInputData(inStream);
        } catch (Throwable e) {
            e.printStackTrace();
        }

        StockItem APPL = gson.fromJson(data,StockItem.class);
        String APPLName = APPL.getName();
        double APPLLastPrice = APPL.getLastPrice();

        Log.d(TAG, "Company Name: " + APPLName + " Is Posting A Last Stock Price of $" + APPLLastPrice);

        try {
            URL url = new URL("http://dev.markitondemand.com/MODApis/Api/v2/Quote/json?symbol=GE&callback=myFunction");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            InputStream inStream = connection.getInputStream();
            data = getInputData(inStream);
        } catch (Throwable e) {
            e.printStackTrace();
        }

        StockItem GE = gson.fromJson(data,StockItem.class);
        String GEName = GE.getName();
        double GELastPrice = GE.getLastPrice();

        Log.d(TAG, "Company Name: " + GEName + " Is Posting A Last Stock Price of $" + GELastPrice);

        try {
            URL url = new URL("http://dev.markitondemand.com/MODApis/Api/v2/Quote/json?symbol=MSFT&callback=myFunction");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            InputStream inStream = connection.getInputStream();
            data = getInputData(inStream);
        } catch (Throwable e) {
            e.printStackTrace();
        }

        StockItem MSFT = gson.fromJson(data,StockItem.class);
        String MSFTName = MSFT.getName();
        double MSFTLastPrice = MSFT.getLastPrice();

        Log.d(TAG, "Company Name: " + MSFTName + " Is Posting A Last Stock Price of $" + MSFTLastPrice);
    }

    private String getInputData(InputStream inStream) throws IOException {
        StringBuilder builder = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inStream));

        String data = null;

        while ((data = reader.readLine()) != null){
            builder.append(data);
        }

        reader.close();

        return builder.toString();
    }
}
