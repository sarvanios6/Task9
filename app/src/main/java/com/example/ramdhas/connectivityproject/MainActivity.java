package com.example.ramdhas.connectivityproject;

import android.support.v7.app.AppCompatActivity;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = "CheckNetworkStatus";// private initiate values
    private NetworkChangeReceiver receiver;
    private boolean isConnected = false;
    private TextView networkStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) //create a method to show the instance state to main page
    {
        super.onCreate(savedInstanceState); // call to super class oncreate funciton for getdelegate some state
        setContentView(R.layout.activity_main); // show the content in main view

        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);// initialized object name for intenfilter
        receiver = new NetworkChangeReceiver(); // initialized the object
        registerReceiver(receiver, filter); // send the result to registerReceiver

        networkStatus = (TextView) findViewById(R.id.networkStatus);// send the text result to acitivi_main.xml
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
       // getMenuInflater().inflate(R.menu.acti, menu);
        return true;
    }

    @Override
    protected void onDestroy()// if connection not available in this device it will be throw the signal in network status
    {
        Log.v(LOG_TAG, "onDestory");
        super.onDestroy();

        unregisterReceiver(receiver);

    }

    public class NetworkChangeReceiver extends BroadcastReceiver
    {

        @Override
        public void onReceive(final Context context, final Intent intent)
        {

            Log.v(LOG_TAG, "Receieved notification about network status");
            isNetworkAvailable(context);// if connection is availabel in the device it will be throw the signal

        }

        private boolean isNetworkAvailable(Context context)
        {
            ConnectivityManager connectivity = (ConnectivityManager)//create a obj name from connectivity manager
                    context.getSystemService(Context.CONNECTIVITY_SERVICE);// collect system context from connectivity service
            if (connectivity != null) //
            {
                NetworkInfo[] info = connectivity.getAllNetworkInfo();
                if (info != null)// network info is not null
                {
                    for (int i = 0; i < info.length; i++)
                    {
                        if (info[i].getState() == NetworkInfo.State.CONNECTED) //get the state from info
                        {
                            if(!isConnected) //not equal to isconnected is false
                            {
                            Log.v(LOG_TAG, "Now you are connected to Internet!");
                            networkStatus.setText("Now you are connected to Internet!");
//                                isConnected = true;
                            //do your processing here ---
                            //if you need to post any data to the server or get status
                            //update from the server
                            }
                            return true;
                        }
                    }
                }
            }
            Log.v(LOG_TAG, "You are not connected to Internet!");
            networkStatus.setText("You are not connected to Internet!");
            isConnected = false;
            return false;
        }
    }

}
