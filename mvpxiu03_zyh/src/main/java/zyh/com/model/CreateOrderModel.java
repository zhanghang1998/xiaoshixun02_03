package zyh.com.model;

import java.util.Map;

import io.reactivex.Observable;
import zyh.com.base.BaseContract;
import zyh.com.core.ApiService;
import zyh.com.util.NetWorkHttp;

//收货地址model层
public class CreateOrderModel implements BaseContract.BaseModel {

    @Override
    public Observable requestData(Object...args) {

        ApiService apiService = NetWorkHttp.instance().create(ApiService.class);
        return apiService.createOrder((long) args[0], (String) args[1],(Map<String, String>) args[2]);
    }
}
