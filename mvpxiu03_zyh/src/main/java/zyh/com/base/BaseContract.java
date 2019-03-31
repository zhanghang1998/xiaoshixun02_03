package zyh.com.base;

import java.io.File;
import java.util.HashMap;

import io.reactivex.Observable;
import zyh.com.bean.Result;
import zyh.com.core.IBaseView;

public interface BaseContract {
    //顶层v层接口
    public interface BaseView<T>{
        //展示加载成功完毕的结果
        public void onCompleted(T data);
        //展示加载失败的结果
        public void onError(Throwable throwable);
    }

    //顶层P层接口
    public interface BasePresenter {
        //响应数据
        void requestData(Object...args);
    }

    //顶层M层对象
    public interface BaseModel<T> {
        Observable<T> requestData(Object...args);
    }

}
