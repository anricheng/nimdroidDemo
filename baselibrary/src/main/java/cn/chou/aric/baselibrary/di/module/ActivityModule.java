package cn.chou.aric.baselibrary.di.module;

import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import cn.chou.aric.baselibrary.di.scope.ActivityScope;
import dagger.Module;
import dagger.Provides;

/**
 * 拥有Activity生命周期的单例从这里提供
 */
@Module
public class ActivityModule {
    RxAppCompatActivity mActivity;

    public ActivityModule(RxAppCompatActivity activity) {
        mActivity = activity;
    }

    @ActivityScope
    @Provides
    RxAppCompatActivity provideActivity(){
        return mActivity;
    }
}
