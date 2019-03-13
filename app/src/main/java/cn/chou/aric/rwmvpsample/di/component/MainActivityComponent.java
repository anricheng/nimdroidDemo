package cn.chou.aric.rwmvpsample.di.component;

import cn.chou.aric.baselibrary.di.component.ActivityComponent;
import cn.chou.aric.baselibrary.di.scope.BusinessScope;
import cn.chou.aric.rwmvpsample.activity.MainActivity;
import cn.chou.aric.rwmvpsample.di.module.MainActivityModule;
import dagger.Component;

@BusinessScope
@Component(modules = MainActivityModule.class, dependencies = ActivityComponent.class)
public interface MainActivityComponent {
    void inject(MainActivity mainActivity);
}
