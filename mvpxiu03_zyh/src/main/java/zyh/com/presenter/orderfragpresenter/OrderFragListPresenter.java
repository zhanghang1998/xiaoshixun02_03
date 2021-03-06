package zyh.com.presenter.orderfragpresenter;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import zyh.com.base.BaseContract;
import zyh.com.base.BasePresenter;
import zyh.com.bean.Result;
import zyh.com.model.circlefragmodel.CircleAddGreatModel;
import zyh.com.model.orderfragmodel.OrderFragListModel;

//订单列表presenter层
public class OrderFragListPresenter extends BasePresenter<BaseContract.BaseView> implements BaseContract.BasePresenter {

    private boolean running;
    private OrderFragListModel orderFragListModel;

    public OrderFragListPresenter(BaseContract.BaseView view) {
        super(view);
    }

    @Override
    protected void initModel() {
        orderFragListModel = new OrderFragListModel();
    }

    @Override
    public void requestData(Object... args) {
        if (running) {
            return;
        }
        running=true;
        orderFragListModel.requestData(args)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Result>() {
                    @Override
                    public void accept(Result homes) throws Exception {
                        running=false;
                        view.onCompleted(homes);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        running=false;
                        view.onError(throwable);
                    }
                });
    }

    public boolean isRunning() {
        return running;
    }
}
