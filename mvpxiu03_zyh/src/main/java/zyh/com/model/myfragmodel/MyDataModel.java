package zyh.com.model.myfragmodel;

import io.reactivex.Observable;
import zyh.com.base.BaseContract;
import zyh.com.bean.DescribeBean;
import zyh.com.bean.Result;
import zyh.com.core.ApiService;
import zyh.com.util.NetWorkHttp;

//我的信息model层
public class MyDataModel implements BaseContract.BaseModel {

    @Override
    public Observable requestData(Object...args) {

        ApiService apiService = NetWorkHttp.instance().create(ApiService.class);
        return apiService.queryMy((long) args[0], (String) args[1]);
    }
}
