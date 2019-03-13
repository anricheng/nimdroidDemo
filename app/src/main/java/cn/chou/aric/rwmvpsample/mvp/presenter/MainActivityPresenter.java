package cn.chou.aric.rwmvpsample.mvp.presenter;


import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import javax.inject.Inject;

import cn.chou.aric.baselibrary.model.AppService;
import cn.chou.aric.baselibrary.model.Peotry;
import cn.chou.aric.baselibrary.mvp.BasePresenter;
import cn.chou.aric.baselibrary.rx.RxUtils;
import cn.chou.aric.rwmvpsample.mvp.view.MainActivityView;


public class MainActivityPresenter extends BasePresenter<MainActivityView> {

    AppService mAppService;
    RxAppCompatActivity mRxActivity;

    @Inject
    public MainActivityPresenter(AppService appService, RxAppCompatActivity rxActivity) {
        mAppService = appService;
        mRxActivity = rxActivity;
    }

    public void getPoetry() {

        if (mView != null) {
            mView.showLoading();
        }

        mAppService.getPoetry(1, 20)
                .compose(mRxActivity.<Peotry>bindToLifecycle())
                .compose(RxUtils.<Peotry>switchIOAndMainThreadTransformer())
                .subscribe(new RxUtils.SimpleSubscriber<Peotry>() {
                    @Override
                    public void onError(Throwable e) {
                        if (mView != null) {
                            mView.hideLoading();
                            mView.onError();

                        }
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(Peotry peotry) {
                        if (mView != null) {
                            mView.hideLoading();
                            mView.showResult(peotry.toString());
                        }
                    }
                });
    }
}
