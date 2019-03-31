package zyh.com.model.orderfragmodel;

import io.reactivex.Observable;
import zyh.com.base.BaseContract;
import zyh.com.core.ApiService;
import zyh.com.util.NetWorkHttp;

//订单列表数据model层
public class OrderFragListModel implements BaseContract.BaseModel {

    private int page;

    @Override
    public Observable requestData(Object...args) {
        boolean isflog = (boolean) args[2];
        if (isflog) {
            page = 1;
        } else {
            page++;
        }
        ApiService apiService = NetWorkHttp.instance().create(ApiService.class);
        return apiService.queryOrderFrag((long) args[0], (String) args[1], (int)args[3],page, 5);
    }
}
