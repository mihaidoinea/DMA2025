package ro.ase.ie.g1087_s03;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MyApp extends Application
        implements Application.ActivityLifecycleCallbacks {

    private String MainActivityTag = MainActivity.class.getName().toLowerCase();

    @Override
    public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle bundle) {
        Log.i(MainActivityTag,"onActivityCreated");
    }

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {
        Log.i(MainActivityTag,"onActivityDestroyed");
    }

    @Override
    public void onActivityPaused(@NonNull Activity activity) {
        Log.i(MainActivityTag,"onActivityPaused");
    }

    @Override
    public void onActivityResumed(@NonNull Activity activity) {
        Log.i(MainActivityTag,"onActivityResumed");
    }

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle bundle) {
        Log.i(MainActivityTag,"onActivitySaveInstanceState");
    }

    @Override
    public void onActivityStarted(@NonNull Activity activity) {
        Log.i(MainActivityTag,"onActivityStarted");
    }

    @Override
    public void onActivityStopped(@NonNull Activity activity) {
        Log.i(MainActivityTag,"onActivityStopped");
    }
}
