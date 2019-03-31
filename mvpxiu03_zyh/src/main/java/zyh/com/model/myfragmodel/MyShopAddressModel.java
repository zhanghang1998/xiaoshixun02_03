package zyh.com.model.myfragmodel;

import io.reactivex.Observable;
import zyh.com.base.BaseContract;
import zyh.com.core.ApiService;
import zyh.com.util.NetWorkHttp;

//收货地址model层
public class MyShopAddressModel implements BaseContract.BaseModel {

    @Override
    public Observable requestData(Object...args) {

        ApiService apiService = NetWorkHttp.instance().create(ApiService.class);
        return apiService.queryAddress((long) args[0], (String) args[1]);
    }
}
