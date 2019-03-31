package zyh.com.model.homefragmodel;

import java.util.List;

import io.reactivex.Observable;
import zyh.com.base.BaseContract;
import zyh.com.bean.HomeFragBannerBean;
import zyh.com.bean.Result;
import zyh.com.bean.homefragbean.HomeBean;
import zyh.com.core.ApiService;
import zyh.com.util.NetWorkHttp;

//首页banner数据model层
public class HomeBannerModel implements BaseContract.BaseModel {

    @Override
    public Observable requestData(Object... args) {

        ApiService apiService = NetWorkHttp.instance().create(ApiService.class);
        Observable<Result<List<HomeFragBannerBean>>> resultObservable = apiService.homeBanners();

        return resultObservable;
    }
}
