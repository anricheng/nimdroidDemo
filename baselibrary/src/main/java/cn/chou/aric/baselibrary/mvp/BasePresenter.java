
package cn.chou.aric.baselibrary.mvp;

public class BasePresenter<T extends BaseView> {

    public T mView;

    public void attachView(T view) {
        mView = view;
    }

    public void detachView() {
        mView = null;
    }
}
