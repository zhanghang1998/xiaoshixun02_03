package zyh.com.base;

import zyh.com.core.IBaseView;

//
public abstract class BasePresenter<V>{

    protected V view;

    public BasePresenter(V view) {
        this.view = view;
        initModel();
    }

    protected abstract void initModel();

    //释放activity的引用
    public void onBind(){
        view = null;
    }

}
