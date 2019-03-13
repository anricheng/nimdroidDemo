package cn.chou.aric.baselibrary.di.component;

import android.content.Context;

import javax.inject.Singleton;

import cn.chou.aric.baselibrary.model.AppService;
import cn.chou.aric.baselibrary.di.module.AppModule;
import dagger.Component;

@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {
    Context appContext();

    AppService appService();
}
