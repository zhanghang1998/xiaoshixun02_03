package zyh.com.presenter.myfragpresenter;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import zyh.com.base.BaseContract;
import zyh.com.base.BasePresenter;
import zyh.com.bean.Result;
import zyh.com.model.homefragmodel.DescribeModel;
import zyh.com.model.myfragmodel.MyDataModel;

//商品详情presenter层
public class MyDataPresenter extends BasePresenter<BaseContract.BaseView> implements BaseContract.BasePresenter {

    private MyDataModel myDataModel;

    public MyDataPresenter(BaseContract.BaseView view) {
        super(view);
    }

    @Override
    protected void initModel() {
        myDataModel = new MyDataModel();
    }

    @Override
    public void requestData(Object... args) {

        myDataModel.requestData(args)
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
