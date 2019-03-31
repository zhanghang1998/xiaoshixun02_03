package zyh.com.model;

import io.reactivex.Observable;
import zyh.com.base.BaseContract;
import zyh.com.bean.Result;
import zyh.com.core.ApiService;
import zyh.com.util.NetWorkHttp;

//登录model层
public class RegisterModel implements BaseContract.BaseModel {

    @Override
    public Observable requestData(Object...args) {

        ApiService apiService = NetWorkHttp.instance().create(ApiService.class);
        Observable<Result> resultObservable = apiService.registerPost((String) args[0], (String) args[1]);

        return resultObservable;
    }
}
