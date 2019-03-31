package zyh.com.presenter.myfragpresenter;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import zyh.com.base.BaseContract;
import zyh.com.base.BasePresenter;
import zyh.com.bean.Result;
import zyh.com.model.circlefragmodel.CircleFragModel;
import zyh.com.model.myfragmodel.MyFootmarkModel;

//圈子列表消息presenter层
public class MyFootmarkPresenter extends BasePresenter<BaseContract.BaseView> implements BaseContract.BasePresenter {

    private boolean running;
    private MyFootmarkModel myFootmarkModel;

    public MyFootmarkPresenter(BaseContract.BaseView view) {
        super(view);
    }

    @Override
    protected void initModel() {
        myFootmarkModel = new MyFootmarkModel();
    }

    @Override
    public void requestData(Object... args) {
        if (running) {
            return;
        }
        running=true;
        myFootmarkModel.requestData(args)
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
