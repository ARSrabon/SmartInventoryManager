package io.github.arsrabon.m.smartinventorymanager;

import android.app.Application;

import com.orm.SugarContext;

/**
 * Created by msrabon on 5/20/17.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        SugarContext.init(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        SugarContext.terminate();
    }
}
