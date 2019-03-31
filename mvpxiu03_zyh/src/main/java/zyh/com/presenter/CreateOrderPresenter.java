package zyh.com.presenter;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import zyh.com.base.BaseContract;
import zyh.com.base.BasePresenter;
import zyh.com.bean.Result;
import zyh.com.model.CreateOrderModel;
import zyh.com.model.myfragmodel.MyAddAddressModel;

//添加收货地址presenter层
public class CreateOrderPresenter extends BasePresenter<BaseContract.BaseView> implements BaseContract.BasePresenter {

    private CreateOrderModel createOrderModel;

    public CreateOrderPresenter(BaseContract.BaseView view) {
        super(view);
    }

    @Override
    protected void initModel() {
        createOrderModel = new CreateOrderModel();
    }

    @Override
    public void requestData(Object... args) {

        createOrderModel.requestData(args)
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
