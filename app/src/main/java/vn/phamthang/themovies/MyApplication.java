package vn.phamthang.themovies;

import android.app.Application;
import android.content.Context;

public class MyApplication extends Application {
    private static Context context;
    private static MyApplication instance;

    public static MyApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        context = getApplicationContext();

    }

    public static Context getContext() {
        return context;
    }
}
