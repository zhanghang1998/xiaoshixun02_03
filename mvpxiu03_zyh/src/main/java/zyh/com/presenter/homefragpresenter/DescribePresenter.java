package zyh.com.presenter.homefragpresenter;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import zyh.com.base.BaseContract;
import zyh.com.base.BasePresenter;
import zyh.com.bean.Result;
import zyh.com.model.homefragmodel.DescribeModel;

//商品详情presenter层
public class DescribePresenter extends BasePresenter<BaseContract.BaseView> implements BaseContract.BasePresenter {

    private DescribeModel describeModel;

    public DescribePresenter(BaseContract.BaseView view) {
        super(view);
    }

    @Override
    protected void initModel() {
        describeModel = new DescribeModel();
    }

    @Override
    public void requestData(Object... args) {

        describeModel.requestData(args)
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
