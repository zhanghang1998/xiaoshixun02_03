package zyh.com.model.homefragmodel;

import io.reactivex.Observable;
import zyh.com.base.BaseContract;
import zyh.com.bean.LoginBean;
import zyh.com.bean.Result;
import zyh.com.bean.homefragbean.HomeBean;
import zyh.com.core.ApiService;
import zyh.com.util.NetWorkHttp;

//首页数据model层
public class HomeModel implements BaseContract.BaseModel {

    @Override
    public Observable requestData(Object... args) {

        ApiService apiService = NetWorkHttp.instance().create(ApiService.class);
        Observable<Result<HomeBean>> resultObservable = apiService.homeList();

        return resultObservable;
    }
}
