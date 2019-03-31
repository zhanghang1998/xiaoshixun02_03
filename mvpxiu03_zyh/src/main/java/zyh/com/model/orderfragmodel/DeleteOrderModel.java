package zyh.com.model.orderfragmodel;

import io.reactivex.Observable;
import zyh.com.base.BaseContract;
import zyh.com.core.ApiService;
import zyh.com.util.NetWorkHttp;

//删除订单model层
public class DeleteOrderModel implements BaseContract.BaseModel {


    @Override
    public Observable requestData(Object...args) {

        ApiService apiService = NetWorkHttp.instance().create(ApiService.class);
        return apiService.deleteOrder((long) args[0], (String) args[1],(String)args[2]);
    }
}
