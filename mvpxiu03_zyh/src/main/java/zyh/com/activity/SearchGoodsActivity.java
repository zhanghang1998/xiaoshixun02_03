package zyh.com.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import zyh.com.adater.SearchGoodsAdapter;
import zyh.com.base.BaseContract;
import zyh.com.bean.Result;
import zyh.com.bean.SearchGoodsBean;
import zyh.com.presenter.homefragpresenter.SearchGoodsPresenter;
import zyh.com.util.SpacingItemDecoration;

//搜索页面
public class SearchGoodsActivity extends AppCompatActivity implements BaseContract.BaseView<Result<List<SearchGoodsBean>>>, XRecyclerView.LoadingListener {

    @BindView(R.id.search_goods_imageView)
    ImageView searchGoodsImageView;
    @BindView(R.id.searche_goods_ditText)
    EditText searcheGoodsDitText;
    @BindView(R.id.search_goods_text)
    TextView searchGoodsText;
    @BindView(R.id.linearLayout_home_head)
    LinearLayout linearLayoutHomeHead;
    @BindView(R.id.search_goods_xRecyclerView)
    XRecyclerView searchGoodsXRecyclerView;
    @BindView(R.id.searche_goods_meiyou)
    LinearLayout searcheGoodsMeiyou;
    private SearchGoodsPresenter searchGoodsPresenter;
    private SearchGoodsAdapter searchGoodsAdapter;
    private String keyString;//获取输入框信息

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_goods);
        ButterKnife.bind(this);
        //调用搜索p层
        searchGoodsPresenter = new SearchGoodsPresenter(this);
        //布局管理器
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2, LinearLayoutManager.VERTICAL, false);
        searchGoodsXRecyclerView.setLayoutManager(gridLayoutManager);
        searchGoodsXRecyclerView.setLoadingListener(this);
        searchGoodsXRecyclerView.setLoadingMoreEnabled(true);
        searchGoodsXRecyclerView.setPullRefreshEnabled(true);
        //页面简单分配
        int space = getResources().getDimensionPixelSize(R.dimen.dp_10);
        searchGoodsXRecyclerView.addItemDecoration(new SpacingItemDecoration(space));
        //适配器
        searchGoodsAdapter = new SearchGoodsAdapter(this);
        searchGoodsXRecyclerView.setAdapter(searchGoodsAdapter);
        //搜索页面点击条目展示详情
        searchGoodsAdapter.setOnIitmClick(new SearchGoodsAdapter.OnIitmClick() {
            @Override
            public void initComeID(int cid) {
                Intent intent = new Intent(SearchGoodsActivity.this, DescribeActivity.class);
                Toast.makeText(SearchGoodsActivity.this, cid + "", Toast.LENGTH_SHORT).show();
                intent.putExtra("comID", cid);
                startActivity(intent);
            }
        });
        //默认刷新
        searchGoodsXRecyclerView.refresh();
    }

    //关键字搜索成功回调
    @Override
    public void onCompleted(Result<List<SearchGoodsBean>> data) {
        searchGoodsXRecyclerView.loadMoreComplete();
        searchGoodsXRecyclerView.refreshComplete();
        if (data.getResult().size()!= 0 ) {
            searchGoodsXRecyclerView.setVisibility(View.VISIBLE);
            searcheGoodsMeiyou.setVisibility(View.GONE);
            if (data.getStatus().equals("0000")) {
                List<SearchGoodsBean> result = data.getResult();
                searchGoodsAdapter.clearAll();
                searchGoodsAdapter.addAll(result);
                searchGoodsAdapter.notifyDataSetChanged();//刷新适配器
            }
        } else {
            Toast.makeText(this, "没有搜索到数据!",Toast.LENGTH_SHORT).show();
            searcheGoodsMeiyou.setVisibility(View.VISIBLE);//INVISIBLE
            searchGoodsXRecyclerView.setVisibility(View.GONE);
        }

    }

    //关键字搜索失败回调
    @Override
    public void onError(Throwable throwable) {
        Log.d("张雨航searchGoods", ">>throwable.getMessage:" + throwable.getMessage() + ";throwable.toString:" + throwable.toString());
    }

    //点击搜索
    @OnClick(R.id.search_goods_text)
    public void onViewText() {
        //获取输入框信息
        keyString = searcheGoodsDitText.getText().toString().trim();
        //请求
        searchGoodsPresenter.requestData(true, keyString);
    }

    //下拉刷新方法
    @Override
    public void onRefresh() {
        if (searchGoodsPresenter.isRunning()) {
            searchGoodsXRecyclerView.refreshComplete();
        }
        //获取输入框信息
        keyString = searcheGoodsDitText.getText().toString().trim();
        searchGoodsPresenter.requestData(true, keyString);
    }

    //上拉加载方法
    @Override
    public void onLoadMore() {
        if (searchGoodsPresenter.isRunning()) {
            searchGoodsXRecyclerView.loadMoreComplete();
        }
        //获取输入框信息
        keyString = searcheGoodsDitText.getText().toString().trim();
        searchGoodsPresenter.requestData(false, keyString);
    }

    //解绑
    @Override
    protected void onDestroy() {
        super.onDestroy();
        searchGoodsPresenter.onBind();
        finish();
    }
}
