package cn.chou.aric.baselibrary.di.module;

import android.content.Context;

import com.google.gson.Gson;
import com.rw.http.NetworkUtils;

import javax.inject.Singleton;

import cn.chou.aric.baselibrary.model.AppService;
import dagger.Module;
import dagger.Provides;

/**
 * 拥有全局生命周期的单例都在这个AppModule中提供
 */
@Module
public class AppModule {

   private Context mApplicationContext;

    public AppModule(Context applicationContext) {
        mApplicationContext = applicationContext;
    }

    @Singleton
    @Provides
    Context provideApplicationContext() {
        return mApplicationContext;
    }

    @Singleton
    @Provides
    NetworkUtils provideNetWorkUtils() {
        return new NetworkUtils();
    }

    @Singleton
    @Provides
    AppService provideAppService(NetworkUtils networkUtils) {
        return networkUtils.getClientUtil().buildRestAdapter("https://api.apiopen.top", 5, new Gson(), true, mApplicationContext)
                .timeoutInSeconds(20)
                .build()
                .create(AppService.class);
    }
}
