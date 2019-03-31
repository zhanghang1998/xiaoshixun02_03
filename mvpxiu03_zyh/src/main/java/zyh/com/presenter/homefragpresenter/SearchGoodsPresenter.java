package zyh.com.presenter.homefragpresenter;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import zyh.com.base.BaseContract;
import zyh.com.base.BasePresenter;
import zyh.com.bean.Result;
import zyh.com.model.homefragmodel.SearchGoodsModel;

//搜索商品presenter层
public class SearchGoodsPresenter extends BasePresenter<BaseContract.BaseView> implements BaseContract.BasePresenter {

    private boolean running;
    private SearchGoodsModel searchGoodsModel;

    public SearchGoodsPresenter(BaseContract.BaseView view) {
        super(view);
    }

    @Override
    protected void initModel() {
        searchGoodsModel = new SearchGoodsModel();
    }

    @Override
    public void requestData(Object... args) {

        if (running) {
            return;
        }
        running=true;
        searchGoodsModel.requestData(args)
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
