package zyh.com.presenter;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import zyh.com.base.BaseContract;
import zyh.com.base.BasePresenter;
import zyh.com.bean.Result;
import zyh.com.model.JoinModel;

//同步购物车presenter层
public class JoinPresenter extends BasePresenter<BaseContract.BaseView> implements BaseContract.BasePresenter {

    private JoinModel joinModel;

    public JoinPresenter(BaseContract.BaseView view) {
        super(view);
    }

    @Override
    protected void initModel() {
        joinModel = new JoinModel();
    }

    @Override
    public void requestData(Object... args) {

        joinModel.requestData(args)
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
