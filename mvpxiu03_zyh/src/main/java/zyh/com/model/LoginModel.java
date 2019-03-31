package zyh.com.model;

import java.io.File;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import zyh.com.base.BaseContract;
import zyh.com.bean.LoginBean;
import zyh.com.bean.Result;
import zyh.com.core.ApiService;
import zyh.com.util.NetWorkHttp;

//登录model层
public class LoginModel implements BaseContract.BaseModel {

    @Override
    public Observable requestData(Object... args) {

        ApiService apiService = NetWorkHttp.instance().create(ApiService.class);
        Observable<Result<LoginBean>> loginPost = apiService.loginPost((String) args[0], (String) args[1]);

        return loginPost;
    }
}
