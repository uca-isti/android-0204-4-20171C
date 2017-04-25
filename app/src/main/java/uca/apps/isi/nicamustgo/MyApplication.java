package uca.apps.isi.nicamustgo;

import android.app.Application;

import io.realm.Realm;

/**
 * Created by isi3 on 24/4/2017.
 */

public class MyApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
    }
}
