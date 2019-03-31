package zyh.com.presenter.circlefragpresenter;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import zyh.com.base.BaseContract;
import zyh.com.base.BasePresenter;
import zyh.com.bean.Result;
import zyh.com.model.circlefragmodel.CircleCancelGreatModel;

//取消点赞presenter层
public class CircleCancelGreatPresenter extends BasePresenter<BaseContract.BaseView> implements BaseContract.BasePresenter {

    private CircleCancelGreatModel circleCancelGreatModel;

    public CircleCancelGreatPresenter(BaseContract.BaseView view) {
        super(view);
    }

    @Override
    protected void initModel() {
        circleCancelGreatModel = new CircleCancelGreatModel();
    }

    @Override
    public void requestData(Object... args) {

        circleCancelGreatModel.requestData(args)
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
