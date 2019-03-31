package zyh.com.model.circlefragmodel;

import java.util.List;

import io.reactivex.Observable;
import zyh.com.base.BaseContract;
import zyh.com.bean.CircleFragBean;
import zyh.com.bean.DescribeBean;
import zyh.com.bean.Result;
import zyh.com.core.ApiService;
import zyh.com.util.NetWorkHttp;

//圈子列表数据model层
public class CircleFragModel implements BaseContract.BaseModel {

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
        return apiService.circleList((long) args[0], (String) args[1], page, 10);
    }
}
