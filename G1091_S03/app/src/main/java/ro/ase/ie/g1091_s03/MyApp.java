package ro.ase.ie.g1091_s03;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MyApp extends Application
        implements Application.ActivityLifecycleCallbacks {
    @Override
    public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle bundle) {
        Log.i("MyAppTag", "onActivityCreated");
    }

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {
        Log.i("MyAppTag", "onActivityDestroyed");
    }

    @Override
    public void onActivityPaused(@NonNull Activity activity) {
        Log.i("MyAppTag", "onActivityPaused");
    }

    @Override
    public void onActivityResumed(@NonNull Activity activity) {
        Log.i("MyAppTag", "onActivityResumed");
    }

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle bundle) {
        Log.i("MyAppTag", "onActivitySaveInstanceState");
    }

    @Override
    public void onActivityStarted(@NonNull Activity activity) {
        Log.i("MyAppTag", "onActivityStarted");
    }

    @Override
    public void onActivityStopped(@NonNull Activity activity) {
        Log.i("MyAppTag", "onActivityStopped");
    }
}
