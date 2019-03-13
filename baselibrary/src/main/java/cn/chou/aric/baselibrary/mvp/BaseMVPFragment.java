package cn.chou.aric.baselibrary.mvp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.trello.rxlifecycle.components.support.RxFragment;

import cn.chou.aric.baselibrary.BaseApplication;
import cn.chou.aric.baselibrary.R;
import cn.chou.aric.baselibrary.di.component.ActivityComponent;
import cn.chou.aric.baselibrary.di.component.DaggerActivityComponent;
import cn.chou.aric.baselibrary.di.module.ActivityModule;

public abstract class BaseMVPFragment extends RxFragment implements BaseView {

    ProgressDialog mProgressDialog;

    ActivityComponent mActivityComponent;

    RxAppCompatActivity mActivity;

    @Override
    public void onAttach(Activity activity) {
        mActivity = (RxAppCompatActivity) activity;
        super.onAttach(activity);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        injectComponent();
        mActivityComponent = DaggerActivityComponent.builder()
                .appComponent(((BaseApplication) mActivity.getApplication()).getAppComponent())
                .activityModule(new ActivityModule(mActivity))
                .build();
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public ActivityComponent getActivityComponent() {
        return mActivityComponent;
    }

    protected abstract void injectComponent();

    @Override
    public void showLoading() {
        mProgressDialog = showProgressDialog(mActivity, R.layout.progressbar, null);
        mProgressDialog.show();

    }

    @Override
    public void hideLoading() {
        mProgressDialog.hide();
    }

    @Override
    public void onError() {

    }
    //short time solution
    public ProgressDialog showProgressDialog(Context context, int customLayoutId, DialogInterface.OnCancelListener listener) {
        if (context != null) {
            if (context instanceof Activity && ((Activity) context).isFinishing()) {
                return null;
            }
            ProgressDialog progress = ProgressDialog.show(context, null, null);
            progress.setCancelable(true);
            progress.setCanceledOnTouchOutside(false);
            progress.setContentView(customLayoutId);
            progress.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            progress.getWindow().setFlags(
                    WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);
            progress.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            progress.setOnCancelListener(listener);
            return progress;
        }
        return null;
    }
}
