package application;

import android.app.Application;
import android.content.Context;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import models.Parking;
import room.AppLocalDb;

public class WeParkApplication extends Application {
    ExecutorService executorService = Executors.newFixedThreadPool(4);
    private static Context context;
    public void onCreate() {
        super.onCreate();
        WeParkApplication.context = getApplicationContext();
    }
    public static Context getAppContext() {
        return WeParkApplication.context;
    }
}
