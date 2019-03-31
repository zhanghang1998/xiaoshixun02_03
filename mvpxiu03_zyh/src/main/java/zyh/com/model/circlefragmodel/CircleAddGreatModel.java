package zyh.com.model.circlefragmodel;

import io.reactivex.Observable;
import zyh.com.base.BaseContract;
import zyh.com.core.ApiService;
import zyh.com.util.NetWorkHttp;

//点赞model层
public class CircleAddGreatModel implements BaseContract.BaseModel {


    @Override
    public Observable requestData(Object...args) {

        ApiService apiService = NetWorkHttp.instance().create(ApiService.class);
        return apiService.likeCircle((long) args[0], (String) args[1],(int)args[2]);
    }
}
