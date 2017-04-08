package com.joe.nfortics;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;



public class App extends Application {




    User user;
    @Override
    public void onCreate() {
//        user = new User();
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .schemaVersion(0)
                .build();

        Realm.setDefaultConfiguration(config);

    }

//    public User getUser() {
//        return user;
//    }
}
