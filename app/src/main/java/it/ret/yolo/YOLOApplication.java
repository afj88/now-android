package it.ret.yolo;

import android.app.Application;

import net.danlew.android.joda.JodaTimeAndroid;

/**
 * Created by Alessandro Frigerio on 30/01/2017.
 */

public class YOLOApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        JodaTimeAndroid.init(this.getApplicationContext());
    }
}
