package zyh.com.model.myfragmodel;

import java.util.Map;

import io.reactivex.Observable;
import zyh.com.base.BaseContract;
import zyh.com.core.ApiService;
import zyh.com.util.NetWorkHttp;

//添加收货地址model层
public class MyAddAddressModel implements BaseContract.BaseModel {

    @Override
    public Observable requestData(Object...args) {

        ApiService apiService = NetWorkHttp.instance().create(ApiService.class);
        return apiService.newAddress((long) args[0], (String) args[1],(Map<String, String>) args[2]);
    }
}
