package com.example.sshconnection;

import android.app.Application;
import android.content.Context;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.multidex.MultiDex;

public class AppController extends Application {

  private static AppController mInstance;

  @Override
  public void onCreate() {
    AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    super.onCreate();
    mInstance = this;
  }

  public static synchronized AppController getInstance() {
    return mInstance;
  }

  @Override
  protected void attachBaseContext(Context base) {
    super.attachBaseContext(base);
    MultiDex.install(this);
  }
}