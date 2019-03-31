package zyh.com.model.orderfragmodel;

import io.reactivex.Observable;
import zyh.com.base.BaseContract;
import zyh.com.core.ApiService;
import zyh.com.util.NetWorkHttp;

//确认收货model层
public class TakeOrderModel implements BaseContract.BaseModel {


    @Override
    public Observable requestData(Object...args) {

        ApiService apiService = NetWorkHttp.instance().create(ApiService.class);
        return apiService.myTake((long) args[0], (String) args[1],(String) args[2]);
    }
}
