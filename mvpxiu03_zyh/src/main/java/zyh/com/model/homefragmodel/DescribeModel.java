package zyh.com.model.homefragmodel;

import io.reactivex.Observable;
import zyh.com.base.BaseContract;
import zyh.com.bean.DescribeBean;
import zyh.com.bean.Result;
import zyh.com.core.ApiService;
import zyh.com.util.NetWorkHttp;

//商品详情model层
public class DescribeModel implements BaseContract.BaseModel {

    @Override
    public Observable requestData(Object...args) {

        ApiService apiService = NetWorkHttp.instance().create(ApiService.class);
        Observable<Result<DescribeBean>> resultObservable = apiService.shopCount((long) args[0], (String) args[1], (int) args[2]);

        return resultObservable;
    }
}
