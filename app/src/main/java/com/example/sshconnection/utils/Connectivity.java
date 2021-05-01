package com.example.sshconnection.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

public class Connectivity {

	/**
	 * Get the network info
	 */
	public static NetworkInfo getNetworkInfo(Context context){
	    ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
	    return cm.getActiveNetworkInfo();
	}

	/**
	 * Check if there is any connectivity issue
	 */
	public static boolean isConnected(Context context){
	    NetworkInfo info = Connectivity.getNetworkInfo(context);
	    return (info != null && info.isConnected());
	}

}