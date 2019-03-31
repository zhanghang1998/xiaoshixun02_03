package zyh.com.model;

import io.reactivex.Observable;
import zyh.com.base.BaseContract;
import zyh.com.bean.DescribeBean;
import zyh.com.bean.Result;
import zyh.com.core.ApiService;
import zyh.com.util.NetWorkHttp;

//同步购物车model层
public class JoinModel implements BaseContract.BaseModel {

    @Override
    public Observable requestData(Object...args) {

        ApiService apiService = NetWorkHttp.instance().create(ApiService.class);

        return apiService.myShopCar((long) args[0], (String) args[1],(String) args[2]);
    }
}
