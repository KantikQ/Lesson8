package ru.mirea.zhurin.d.r.yandexmaps;

import android.app.Application;

import com.yandex.mapkit.MapKitFactory;

public class App extends Application {
    private final String MAPKIT_API_KEY = "8e8935fb-2e10-4743-9662-85289a137d5c";
    @Override
    public void onCreate() {
        super.onCreate();
        MapKitFactory.setApiKey(MAPKIT_API_KEY);
    }
}
