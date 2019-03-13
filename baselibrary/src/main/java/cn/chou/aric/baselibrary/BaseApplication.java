package cn.chou.aric.baselibrary;

import android.app.Application;

import cn.chou.aric.baselibrary.di.component.AppComponent;
import cn.chou.aric.baselibrary.di.component.DaggerAppComponent;
import cn.chou.aric.baselibrary.di.module.AppModule;

public class BaseApplication extends Application {
    AppComponent mAppComponent;

    private static Application mApplication;

    public static Application getApp() {
        return mApplication;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
        mAppComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).build();
    }

    public AppComponent getAppComponent(){
        return mAppComponent;
    }
}
