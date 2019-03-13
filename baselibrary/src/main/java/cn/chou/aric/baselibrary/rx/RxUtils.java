package cn.chou.aric.baselibrary.rx;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Looper;
import android.view.WindowManager;
import android.widget.Toast;
import java.util.List;
import cn.chou.aric.baselibrary.BaseApplication;
import cn.chou.aric.baselibrary.model.BaseResponse;
import cn.chou.aric.baselibrary.R;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;


public class RxUtils {

    private static ProgressDialog mProgressDialog;
    private static Handler mMainThreadHandler = new Handler(Looper.getMainLooper());

    public static abstract class ListTransformer<F, T> implements Observable.Transformer<List<F>, List<T>> {
        protected abstract Observable<T> transform(F f);

        @Override
        public Observable<List<T>> call(Observable<List<F>> listObservable) {
            return listObservable.flatMap(new Func1<List<F>, Observable<List<T>>>() {
                @Override
                public Observable<List<T>> call(List<F> xes) {
                    return Observable.from(xes)
                            .flatMap(new Func1<F, Observable<T>>() {
                                @Override
                                public Observable<T> call(F f) {
                                    return transform(f);
                                }
                            }).toList();
                }
            });
        }
    }

    public static class SimpleSubscriber<T> extends Subscriber<T> {
        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable e) {
        }

        @Override
        public void onNext(T o) {
        }
    }

    /**
     * @param <T> 在这个类中我们将http请求，请求成功后存储数据库的整个过程中产生的所有错误都传递给onResponseError方法
     */

    public static abstract class ResponseSubscriber <T>extends Subscriber<BaseResponse<T>> {
        private Context context;
        private boolean showProgress;

        public ResponseSubscriber(Context context) {
            this.context = context;
        }

        public ResponseSubscriber(Context context, boolean showProgress) {
            this.context = context;
            this.showProgress = showProgress;
        }

        @Override
        public void onStart() {
            super.onStart();

            //1.开始订阅时检查网络状况，无网络连接时Toast提示
            if (!isNetworkAvailable(context == null ? BaseApplication.getApp() : context)) {
                mMainThreadHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (context == null) context = BaseApplication.getApp();
                        Toast.makeText(context, "没有网络", Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                //2.有网络时显示进度条
                if (showProgress) {
                    mMainThreadHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (context == null) context = BaseApplication.getApp();
                            mProgressDialog = showProgressDialog(context, R.layout.progressbar, null);
                        }
                    });
                }
            }
        }

        @Override
        public void onCompleted() {
            //3.网络访问正常结束时关闭进度条
            if (mProgressDialog != null && mProgressDialog.isShowing()) {
                mMainThreadHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mProgressDialog.dismiss();
                    }
                });
            }
        }

        @Override
        public void onError(Throwable throwable) {
            //4.网络访问异常结束时关闭进度条
            if (mProgressDialog != null && mProgressDialog.isShowing()) {
                mMainThreadHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mProgressDialog.dismiss();
                    }
                });
            }
            onResponseError(throwable);
        }

        @Override
        public void onNext(BaseResponse<T> baseResponse) {
            if (baseResponse.getStatusCode()==1){
                onResponseNext(baseResponse.getPayload());
            }else {
                onResponseError(new Throwable("网络请求异常"));
            }
        }


        public void onResponseError(Throwable throwable) {
            //5.添加这个判断避免没有网络时与1.的提示重复
            if (isNetworkAvailable(context == null ? BaseApplication.getApp() : context)) {
                mMainThreadHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (context == null) context = BaseApplication.getApp();
                        Toast.makeText(context, "网络请求异常", Toast.LENGTH_LONG).show();
                    }
                });
            }
        }

        public abstract void onResponseNext(T t);
    }

    public static <T> Observable.Transformer<T, T> switchIOAndMainThreadTransformer() {
        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> observable) {
                return observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
            }
        };
    }


    final static public boolean isNetworkAvailable(Context context) {
        int status = getConnectivityStatus(context);
        return (status == ConnectivityManager.TYPE_MOBILE
                || status == ConnectivityManager.TYPE_WIFI);
    }

    final static public int getConnectivityStatus(Context context) {
        if (context == null
                || context.getSystemService(Context.CONNECTIVITY_SERVICE) == null) {
            return -1;
        }

        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (null != activeNetwork && activeNetwork.isConnected()) {
            return activeNetwork.getType();
        }
        return -1;
    }

    final static public ProgressDialog showProgressDialog(Context context
            , int customLayoutId, DialogInterface.OnCancelListener listener) {
        if(context != null) {
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
