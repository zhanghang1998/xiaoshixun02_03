package zyh.com.presenter;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import zyh.com.base.BaseContract;
import zyh.com.base.BasePresenter;
import zyh.com.bean.Result;
import zyh.com.model.LoginModel;
import zyh.com.model.RegisterModel;

//登录presenter层
public class RegisterPresenter extends BasePresenter<BaseContract.BaseView> implements BaseContract.BasePresenter {


    private RegisterModel registerModel;

    public RegisterPresenter(BaseContract.BaseView view) {
        super(view);
    }

    @Override
    protected void initModel() {
        registerModel = new RegisterModel();
    }

    @Override
    public void requestData(Object... args) {

        registerModel.requestData(args)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Result>() {
                    @Override
                    public void accept(Result homes) throws Exception {
                        view.onCompleted(homes);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        view.onError(throwable);
                    }
                });

    }


}
