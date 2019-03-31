package zyh.com.presenter.homefragpresenter;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import zyh.com.base.BaseContract;
import zyh.com.base.BasePresenter;
import zyh.com.bean.Result;
import zyh.com.model.homefragmodel.HomeBannerModel;

//首页banner轮播图数据presenter层
public class HomeBannerPresenter extends BasePresenter<BaseContract.BaseView> implements BaseContract.BasePresenter {


    private HomeBannerModel homeBannerModel;

    public HomeBannerPresenter(BaseContract.BaseView view) {
        super(view);
    }

    @Override
    protected void initModel() {
        homeBannerModel = new HomeBannerModel();
    }

    @Override
    public void requestData(Object... args) {

        homeBannerModel.requestData(args)
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
