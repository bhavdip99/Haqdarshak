package com.bhavdip.haqdarshak;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;

/**
 * Created by bhavdip on 30/5/17.
 */

public class AppApplication extends Application {
    private static Context mContext;

    @Override
    public void onCreate() {

        super.onCreate();
        Fabric.with(this, new Crashlytics());
        mContext = getApplicationContext();

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                //TODO your background code
                Log.d("TAG:", "Async in application called");
                Fresco.initialize(mContext);
            }
        });
    }

}
