package zyh.com.activity.myfragactivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import zyh.com.activity.R;
import zyh.com.adater.myfragadapter.MyFootmarkAdapter;
import zyh.com.app.MyAppliction;
import zyh.com.base.BaseContract;
import zyh.com.bean.Result;
import zyh.com.bean.myfragbean.MyDataBean;
import zyh.com.bean.myfragbean.MyFootmarkBean;
import zyh.com.presenter.myfragpresenter.MyFootmarkPresenter;
import zyh.com.util.SpacingItemDecoration;

public class MyFootmarkActivity extends AppCompatActivity implements XRecyclerView.LoadingListener{

    @BindView(R.id.textView_myMessage_perfect)
    TextView textViewMyMessagePerfect;
    @BindView(R.id.recyclerView_Footmark)
    XRecyclerView recyclerViewFootmark;
    private MyFootmarkAdapter myFootmarkAdapter;
    private MyFootmarkPresenter myFootmarkPresenter;
    private SharedPreferences myUserSp;
    private long usersNum;
    private String sessionId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_footmark);
        ButterKnife.bind(this);
        //用SharedPreferences获取userId和sessionId
        myUserSp = MyAppliction.getShare();
        String userId = myUserSp.getString("userId", "");
        usersNum = Integer.valueOf(userId);
        sessionId = myUserSp.getString("sessionId", "");
        myFootmarkPresenter = new MyFootmarkPresenter(new queryFootmark());
        //足迹页面列表显示样式
        //2.声名为瀑布流的布局方式: 3列,垂直方向
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        recyclerViewFootmark.setLayoutManager(staggeredGridLayoutManager);
        //recyclerview条目应对屏幕适配
        int space = getResources().getDimensionPixelSize(R.dimen.dp_10);
        recyclerViewFootmark.addItemDecoration(new SpacingItemDecoration(space));
        //刷新监听
        recyclerViewFootmark.setLoadingListener(this);
        recyclerViewFootmark.setLoadingMoreEnabled(true);
        recyclerViewFootmark.setPullRefreshEnabled(true);
        //适配器
        myFootmarkAdapter = new MyFootmarkAdapter(this);
        recyclerViewFootmark.setAdapter(myFootmarkAdapter);
        //足迹数据请求
        //myFootmarkPresenter.requestData(usersNum,sessionId,true);
        recyclerViewFootmark.refresh();
    }

    //圈子列表下拉刷新
    @Override
    public void onRefresh() {
        if (myFootmarkPresenter.isRunning()) {
            recyclerViewFootmark.refreshComplete();
        }
        myFootmarkAdapter.callClear();//这里是清空适配器集合 , 因为页面刷新 , page重新等于1了,
        myFootmarkPresenter.requestData(usersNum,sessionId,true);
    }
    //圈子列表上拉加载
    @Override
    public void onLoadMore() {
        if (myFootmarkPresenter.isRunning()) {
            recyclerViewFootmark.loadMoreComplete();
        }
        myFootmarkPresenter.requestData(usersNum,sessionId,false);
    }

    //我的足迹列表数据回调
    public class queryFootmark implements BaseContract.BaseView<Result<List<MyFootmarkBean>>> {

        @Override
        public void onCompleted(Result<List<MyFootmarkBean>> data) {
            recyclerViewFootmark.refreshComplete();
            recyclerViewFootmark.loadMoreComplete();
            if (data.getStatus().equals("0000")) {
                List<MyFootmarkBean> result = data.getResult();
                myFootmarkAdapter.addAll(result);
                myFootmarkAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onError(Throwable throwable) {
            Log.d("张雨航footmark", ">>throwable.getMessage:" + throwable.getMessage() + ";throwable.toString:" + throwable.toString());
        }
    }

    @OnClick(R.id.textView_myMessage_perfect)
    public void onViewClicked() {//点击返回按钮 , 销毁此页面
        this.finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        myFootmarkPresenter.onBind();
        finish();
    }
}
