
package cn.chou.aric.baselibrary.mvp;

import java.lang.ref.WeakReference;

public class BasePresenter<T extends BaseView> {
   //use WeakReference to prevent memory leak risk
   public WeakReference<T> mView;

   public void attachView(T view){
       mView = new WeakReference<>(view);
    }

    public void detachView(){
        mView = null;
    }
}
