package zyh.com.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import zyh.com.activity.R;
import zyh.com.adater.CircleFragAdapter;
import zyh.com.app.MyAppliction;
import zyh.com.base.BaseContract;
import zyh.com.bean.CircleFragBean;
import zyh.com.bean.Result;
import zyh.com.presenter.circlefragpresenter.CircleAddGreatPresenter;
import zyh.com.presenter.circlefragpresenter.CircleCancelGreatPresenter;
import zyh.com.presenter.circlefragpresenter.CircleFragPresenter;

//圈子模块
public class CircleFragment extends Fragment implements BaseContract.BaseView<Result<List<CircleFragBean>>>,XRecyclerView.LoadingListener {

    @BindView(R.id.circleFrag_XRList)
    XRecyclerView circleFragXRList;
    Unbinder unbinder;
    private CircleFragPresenter circleFragPresenter;
    private SharedPreferences myUserSp;
    private CircleFragAdapter circleFragAdapter;
    private long usersNum;
    private String sessionId;
    private CircleAddGreatPresenter circleAddGreatPresenter;
    private CircleCancelGreatPresenter circleCancelGreatPresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_circle, container, false);
        unbinder = ButterKnife.bind(this, view);
        //用SharedPreferences获取userId和sessionId
        myUserSp = MyAppliction.getShare();
        String userId = myUserSp.getString("userId", "");
        usersNum = Integer.valueOf(userId);
        sessionId = myUserSp.getString("sessionId", "");
        //调用圈子列表信息p层
        circleFragPresenter = new CircleFragPresenter(this);
        circleAddGreatPresenter = new CircleAddGreatPresenter(new likecancel());//点赞
        circleCancelGreatPresenter = new CircleCancelGreatPresenter(new likecancel());
        //布局管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        circleFragXRList.setLayoutManager(layoutManager);//
        //刷新监听
        circleFragXRList.setLoadingListener(this);
        circleFragXRList.setLoadingMoreEnabled(true);
        circleFragXRList.setPullRefreshEnabled(true);
        //适配器
        circleFragAdapter = new CircleFragAdapter(getContext());
        circleFragXRList.setAdapter(circleFragAdapter);
        //默认列表刷新
        circleFragXRList.refresh();

        //圈子点赞
        circleFragAdapter.setOnIitmClick(new CircleFragAdapter.OnIitmClick() {
            @Override
            public void initComeID(int id, int great, int position) {
                if (great==1){
                    //已点赞，需取消
                    cancelCircleUrl(id);
                    circleFragAdapter.getcancel(position);
                }else{
                    //未点赞，需点赞
                    needlCircleUrl(id);
                    circleFragAdapter.getlike(position);
                }
            }
        });

        return view;
    }

    //取消点赞
    private void cancelCircleUrl(int id){
        circleCancelGreatPresenter.requestData(usersNum,sessionId,id);
    }
    //进行点赞
    private void needlCircleUrl(int id){
        circleAddGreatPresenter.requestData(usersNum,sessionId,id);
    }

    //点赞and取消赞 , 回调结果方法
    public class likecancel implements BaseContract.BaseView<Result>{

        @Override
        public void onCompleted(Result data) {
            if (data.getStatus().equals("0000")) {
                Toast.makeText(getContext(), data.getMessage()+"",Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onError(Throwable throwable) {
            Log.d("张雨航circle", ">>throwable.getMessage:" + throwable.getMessage() + ";throwable.toString:" + throwable.toString());
        }
    }

    //圈子列表成功方法
    @Override
    public void onCompleted(Result<List<CircleFragBean>> data) {
        circleFragXRList.refreshComplete();
        circleFragXRList.loadMoreComplete();
        if (data.getStatus().equals("0000")) {
            List<CircleFragBean> circleFragBeans = data.getResult();
            circleFragAdapter.addAll(circleFragBeans);
            circleFragAdapter.notifyDataSetChanged();
        }
    }
    //圈子列表失败方法
    @Override
    public void onError(Throwable throwable) {
        Log.d("张雨航circle", ">>throwable.getMessage:" + throwable.getMessage() + ";throwable.toString:" + throwable.toString());
    }
    //圈子列表下拉刷新
    @Override
    public void onRefresh() {
        if (circleFragPresenter.isRunning()) {
            circleFragXRList.refreshComplete();
        }
        circleFragAdapter.clearAll();//这里是清空适配器集合 , 因为页面刷新 , page重新等于1了,
        circleFragPresenter.requestData(usersNum,sessionId,true);
    }
    //圈子列表上拉加载
    @Override
    public void onLoadMore() {
        if (circleFragPresenter.isRunning()) {
            circleFragXRList.loadMoreComplete();
        }
        circleFragPresenter.requestData(usersNum,sessionId,false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        circleFragPresenter.onBind();
        circleAddGreatPresenter.onBind();
        circleCancelGreatPresenter.onBind();
    }
}
