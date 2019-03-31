package zyh.com.model.homefragmodel;

import java.util.List;

import io.reactivex.Observable;
import zyh.com.base.BaseContract;
import zyh.com.bean.Result;
import zyh.com.bean.SearchGoodsBean;
import zyh.com.core.ApiService;
import zyh.com.util.NetWorkHttp;

//搜索商品model层
public class SearchGoodsModel implements BaseContract.BaseModel {

    private int page;

    @Override
    public Observable requestData(Object...args) {

        boolean isflog = (boolean) args[0];
        if (isflog) {
            page = 1;
        } else {
            page++;
        }
        ApiService apiService = NetWorkHttp.instance().create(ApiService.class);
        Observable<Result<List<SearchGoodsBean>>> resultObservable = apiService.queryShopKey((String) args[1], page, 10);

        return resultObservable;
    }
}
