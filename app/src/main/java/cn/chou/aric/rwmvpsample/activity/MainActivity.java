package cn.chou.aric.rwmvpsample.activity;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import javax.inject.Inject;

import cn.chou.aric.baselibrary.mvp.BaseMVPActivity;
import cn.chou.aric.rwmvpsample.R;
import cn.chou.aric.rwmvpsample.di.component.DaggerMainActivityComponent;
import cn.chou.aric.rwmvpsample.mvp.presenter.MainActivityPresenter;
import cn.chou.aric.rwmvpsample.mvp.view.MainActivityView;

public class MainActivity extends BaseMVPActivity implements MainActivityView {

    @Inject
    MainActivityPresenter mPresenter;

    TextView mTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextView = (TextView) findViewById(R.id.tv_result);

        findViewById(R.id.fetch).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.getPoetry();
            }
        });
    }

    @Override
    protected void InjectComponent() {
        DaggerMainActivityComponent.builder().activityComponent(getActivityComponent()).build().inject(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.attachView(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    @Override
    public void showResult(String result) {
        mTextView.setText(result);
    }
}
