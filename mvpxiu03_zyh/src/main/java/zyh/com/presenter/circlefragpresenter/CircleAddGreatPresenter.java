package zyh.com.presenter.circlefragpresenter;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import zyh.com.base.BaseContract;
import zyh.com.base.BasePresenter;
import zyh.com.bean.Result;
import zyh.com.model.circlefragmodel.CircleAddGreatModel;

//点赞presenter层
public class CircleAddGreatPresenter extends BasePresenter<BaseContract.BaseView> implements BaseContract.BasePresenter {

    private CircleAddGreatModel circleAddGreatModel;

    public CircleAddGreatPresenter(BaseContract.BaseView view) {
        super(view);
    }

    @Override
    protected void initModel() {
        circleAddGreatModel = new CircleAddGreatModel();
    }

    @Override
    public void requestData(Object... args) {

        circleAddGreatModel.requestData(args)
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
