package cn.chou.aric.rwmvpsample.di.module;

import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import cn.chou.aric.baselibrary.model.AppService;
import cn.chou.aric.baselibrary.di.scope.BusinessScope;
import cn.chou.aric.rwmvpsample.mvp.presenter.MainActivityPresenter;
import dagger.Module;
import dagger.Provides;

@Module
public class MainActivityModule {

    @BusinessScope
    @Provides
    MainActivityPresenter provideMainActivityPresenter(AppService appService, RxAppCompatActivity mActivity){
     return new MainActivityPresenter(appService,mActivity);
    }
}
