package cn.chou.aric.baselibrary.di.component;

import android.content.Context;

import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import cn.chou.aric.baselibrary.model.AppService;
import cn.chou.aric.baselibrary.di.module.ActivityModule;
import cn.chou.aric.baselibrary.di.scope.ActivityScope;
import dagger.Component;

@ActivityScope
@Component(modules = {ActivityModule.class} , dependencies = AppComponent.class)
public interface ActivityComponent {

    Context appContext();

    RxAppCompatActivity activity();

    AppService appService();
}
