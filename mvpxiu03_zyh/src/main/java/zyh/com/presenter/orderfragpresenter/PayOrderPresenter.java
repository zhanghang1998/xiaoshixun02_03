package zyh.com.presenter.orderfragpresenter;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import zyh.com.base.BaseContract;
import zyh.com.base.BasePresenter;
import zyh.com.bean.Result;
import zyh.com.model.orderfragmodel.DeleteOrderModel;
import zyh.com.model.orderfragmodel.PayOrderModel;

//删除订单presenter层
public class PayOrderPresenter extends BasePresenter<BaseContract.BaseView> implements BaseContract.BasePresenter {

    private PayOrderModel payOrderModel;

    public PayOrderPresenter(BaseContract.BaseView view) {
        super(view);
    }

    @Override
    protected void initModel() {
        payOrderModel = new PayOrderModel();
    }

    @Override
    public void requestData(Object... args) {

        payOrderModel.requestData(args)
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
