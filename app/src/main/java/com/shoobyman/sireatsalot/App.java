package com.shoobyman.sireatsalot;

import android.app.Application;

public class App extends Application {
    public static String BREAKFAST_KEY;
    public static String LUNCH_KEY;
    public static String DINNER_KEY;
    public static String SNACK_KEY;

    @Override
    public void onCreate() {
        super.onCreate();
        BREAKFAST_KEY = getApplicationContext().getResources().getString(R.string.breakfast_item);
        LUNCH_KEY = getApplicationContext().getResources().getString(R.string.lunch_item);
        DINNER_KEY = getApplicationContext().getResources().getString(R.string.dinner_item);
        SNACK_KEY = getApplicationContext().getResources().getString(R.string.snack_item);
    }
}
