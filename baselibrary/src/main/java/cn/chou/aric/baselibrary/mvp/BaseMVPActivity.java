package cn.chou.aric.baselibrary.mvp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.WindowManager;
import android.widget.Toast;

import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import cn.chou.aric.baselibrary.BaseApplication;
import cn.chou.aric.baselibrary.R;
import cn.chou.aric.baselibrary.di.component.ActivityComponent;
import cn.chou.aric.baselibrary.di.component.DaggerActivityComponent;
import cn.chou.aric.baselibrary.di.module.ActivityModule;

public abstract class BaseMVPActivity extends RxAppCompatActivity implements BaseView {

    ProgressDialog mProgressDialog;
    ActivityComponent mActivityComponent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityComponent = DaggerActivityComponent.builder()
                .appComponent(((BaseApplication) getApplication()).getAppComponent())
                .activityModule(new ActivityModule(this))
                .build();
        InjectComponent();

    }

    public ActivityComponent getActivityComponent() {
        return mActivityComponent;
    }

    protected abstract void InjectComponent();

    @Override
    public void showLoading() {
        if (mProgressDialog == null) {
            mProgressDialog = showProgressDialog(this, R.layout.progressbar, null);
        }
        if (mProgressDialog != null) {
            mProgressDialog.show();
        }
    }

    @Override
    public void hideLoading() {
        if (mProgressDialog != null) {
            mProgressDialog.hide();
        }
    }

    @Override
    public void onError() {
        Toast.makeText(this, "Wow, something go wrong,please try again later", Toast.LENGTH_SHORT).show();
    }

    //short time solution
    public ProgressDialog showProgressDialog(Context context
            , int customLayoutId, DialogInterface.OnCancelListener listener) {
        if (context != null) {
            // Check if the context activity is finishing to avoid NPE.
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
