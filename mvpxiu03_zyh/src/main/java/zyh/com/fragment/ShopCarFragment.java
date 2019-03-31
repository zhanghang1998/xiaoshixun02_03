package zyh.com.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import zyh.com.activity.GobuyActivity;
import zyh.com.activity.R;
import zyh.com.adater.ShopCarAdapter;
import zyh.com.app.MyAppliction;
import zyh.com.base.BaseContract;
import zyh.com.bean.Result;
import zyh.com.bean.ShopCarBean;
import zyh.com.bean.ShopCarCommitBean;
import zyh.com.presenter.JoinPresenter;
import zyh.com.presenter.ShopCarPresenter;
import zyh.com.util.SpacingItemDecoration;

//购物车
public class ShopCarFragment extends Fragment implements XRecyclerView.LoadingListener {

    private static SharedPreferences myUserSp;
    @BindView(R.id.shopCar_recyclerView)
    XRecyclerView shopCarXRecyclerView;
    @BindView(R.id.shopCar_box_all)
    CheckBox shopCarBoxAll;
    @BindView(R.id.shopCar_text_allprice)
    TextView shopCarTextAllprice;
    @BindView(R.id.shopCar_text_goBuy)
    TextView shopCarTextGoBuy;
    Unbinder unbinder;
    private long usersNum;
    private String sessionId;
    private ShopCarPresenter shopCarPresenter;
    private ShopCarAdapter shopCarAdapter;
    private JoinPresenter joinPresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shopcar, container, false);
        unbinder = ButterKnife.bind(this, view);
        //用SharedPreferences获取userId和sessionId
        myUserSp = MyAppliction.getShare();
        String userId = myUserSp.getString("userId", "");
        usersNum = Integer.valueOf(userId);
        sessionId = myUserSp.getString("sessionId", "");
        //调用p层
        shopCarPresenter = new ShopCarPresenter(new queryShopCar());
        joinPresenter = new JoinPresenter(new qdeleteItem());
        //布局管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        shopCarXRecyclerView.setLayoutManager(layoutManager);
        shopCarXRecyclerView.setLoadingListener(this);
        shopCarXRecyclerView.setLoadingMoreEnabled(true);
        shopCarXRecyclerView.setPullRefreshEnabled(true);
        //recyclerview条目应对屏幕适配
        int space = getResources().getDimensionPixelSize(R.dimen.dp_10);
        shopCarXRecyclerView.addItemDecoration(new SpacingItemDecoration(space));
        //适配器
        shopCarAdapter = new ShopCarAdapter(getContext());
        shopCarXRecyclerView.setAdapter(shopCarAdapter);
        //购物车列表数据请求方法
        shopCarPresenter.requestData(usersNum, sessionId);
        //默认加载
        shopCarXRecyclerView.refresh();
        //点击方法
        myClick();
        return view;
    }

    // 全选 反选 去结算
    @OnClick({R.id.shopCar_box_all, R.id.shopCar_text_goBuy})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.shopCar_box_all://全选 , 全不选

                boolean allChecked = shopCarBoxAll.isChecked();

                double totalPrice=0;
                int num=0;

                for (int i=0;i<shopCarBeans.size();i++){
                    //遍历商品，改变状态
                    shopCarBeans.get(i).setIscheck(allChecked);
                    totalPrice=totalPrice+(shopCarBeans.get(i).getPrice()*shopCarBeans.get(i).getCount());
                    num=num+shopCarBeans.get(i).getCount();
                }

                if (allChecked){
                    shopCarTextAllprice.setText(""+totalPrice);
                    shopCarTextGoBuy.setText("去结算("+num+")");
                }else{
                    shopCarTextAllprice.setText("0");
                    shopCarTextGoBuy.setText("去结算");
                }
                shopCarAdapter.notifyDataSetChanged();
                break;

            case R.id.shopCar_text_goBuy://====去结算====

                //获取价格
                String toString = shopCarTextAllprice.getText().toString();
                //判断价格
                if (!(toString.equals("0"))&&!(toString.equals("0.0"))){
                    //跳转意图
                    Intent intent=new Intent(getActivity(),GobuyActivity.class);
                    //创建集合存放要传送的数据
                    List<ShopCarBean> creation_bill = new ArrayList<>();
                    //判断商品是否被选中
                    //如果被选中就放到集合里，通过intent传到activity中
                    for (int i=0;i<shopCarBeans.size();i++){
                        //判断是否选中
                        if (shopCarBeans.get(i).isIscheck()) {
                            creation_bill.add(new ShopCarBean(
                                    shopCarBeans.get(i).getCommodityId(),
                                    shopCarBeans.get(i).getCommodityName(),
                                    shopCarBeans.get(i).getCount(),
                                    shopCarBeans.get(i).getPic(),
                                    shopCarBeans.get(i).getPrice()
                            ));
                        }
                    }
                    //Log.v("zyh","closeBean>> bk"+creation_bill.toString());
                    intent.putExtra("creation_bill", (Serializable) creation_bill);
                    startActivity(intent);
                }else {
                    Toast.makeText(getContext(), "没有要购买的商品", Toast.LENGTH_SHORT).show();
                }//判断价格

                break;
        }
    }

    //删除 , 条目复选框状态改变 , 点击方法
    public void myClick() {

        //删除条目方法
        shopCarAdapter.setOnIitmDelete(new ShopCarAdapter.OnIitmDelete() {
            @Override
            public void initDeleteId(List<ShopCarBean> listes, int cid) {
                //在这里重新遍历已经改变状态后的数据
                //这里不能break跳出，因为还有需要计算后面点击商品的价格和数量，所以必须跑完整个循环
                double totalPrice = 0;
                //勾选商品的数量，不是该商品购买的数量
                int num = 0;
                //所有商品总数，和上面的数量做比对，如果两者相等，则说明全选
                int totalNum = 0;
                for (int i = 0; i < listes.size(); i++) {
                    totalNum = totalNum + listes.get(i).getCount();
                    if (listes.get(i).isIscheck()) {
                        totalPrice = totalPrice + listes.get(i).getPrice() * listes.get(i).getCount();
                        num = num + listes.get(i).getCount();
                    }
                }
                if (num < totalNum) {
                    shopCarBoxAll.setChecked(false);
                } else {
                    shopCarBoxAll.setChecked(true);
                }
                shopCarBeans.remove(cid);
                shopCarTextAllprice.setText("" + totalPrice);
                shopCarTextGoBuy.setText("去结算(" + num + ")");
                //添加购物车的集合
                List<ShopCarCommitBean> addlist = new ArrayList<>();
                for (int i = 0; i < listes.size(); i++) {
                    int commodityId = listes.get(i).getCommodityId();
                    int count = listes.get(i).getCount();
                    addlist.add(new ShopCarCommitBean(commodityId,count));
                }
                //变成String类型
                Gson gson = new Gson();
                String s = gson.toJson(shopCarBeans);
                //进行删除方法 , 其实就是重新同步一下购物车
                joinPresenter.requestData(usersNum, sessionId, s);
                if (listes.size() == 0) {
                    shopCarBoxAll.setChecked(false);
                }

            }
        });
        //条目复选框改变 , 方法
        shopCarAdapter.setOnIitmClick(new ShopCarAdapter.OnIitmClick() {
            @Override
            public void initComeID(List<ShopCarBean> listes) {
                //在这里重新遍历已经改变状态后的数据
                //这里不能break跳出，因为还有需要计算后面点击商品的价格和数量，所以必须跑完整个循环
                double totalPrice = 0;
                //勾选商品的数量，不是该商品购买的数量
                int num = 0;
                //所有商品总数，和上面的数量做比对，如果两者相等，则说明全选
                int totalNum = 0;
                for (int i = 0; i < listes.size(); i++) {
                    totalNum = totalNum + listes.get(i).getCount();
                    if (listes.get(i).isIscheck()) {
                        totalPrice = totalPrice + listes.get(i).getPrice() * listes.get(i).getCount();
                        num = num + listes.get(i).getCount();
                    }
                }
                if (num < totalNum) {
                    shopCarBoxAll.setChecked(false);
                } else {
                    shopCarBoxAll.setChecked(true);
                }
                shopCarTextAllprice.setText("" + totalPrice);//总的计算价格
                shopCarTextGoBuy.setText("去结算(" + num + ")");//去结算按钮
            }
        });

    }

    //下拉刷新
    @Override
    public void onRefresh() {
        if (shopCarPresenter.isRunning()) {
            shopCarXRecyclerView.refreshComplete();
        }
        shopCarPresenter.requestData(usersNum, sessionId);
    }

    //上拉加载
    @Override
    public void onLoadMore() {
        if (shopCarPresenter.isRunning()) {
            shopCarXRecyclerView.loadMoreComplete();
        }
        Toast.makeText(getContext(), "没有更多了!", Toast.LENGTH_SHORT).show();
    }

    //删除条目回调方法 , 其实就是重新同步购物车
    private class qdeleteItem implements BaseContract.BaseView<Result> {
        @Override
        public void onCompleted(Result data) {
            if (data.getStatus().equals("0000")) {
                Toast.makeText(getActivity(), "删除成功", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onError(Throwable throwable) {
            Log.d("张雨航deleteCar", ">>throwable.getMessage:" + throwable.getMessage() + ";throwable.toString:" + throwable.toString());
        }
    }

    private List<ShopCarBean> shopCarBeans;

    //查询购物车数据回调  ,  内部类
    private class queryShopCar implements BaseContract.BaseView<Result<List<ShopCarBean>>> {
        @Override
        public void onCompleted(Result<List<ShopCarBean>> data) {
            shopCarXRecyclerView.refreshComplete();
            shopCarXRecyclerView.loadMoreComplete();
            if (data.getStatus().equals("0000")) {
                //Toast.makeText(getContext(), data.getMessage() + "", Toast.LENGTH_SHORT).show();
                shopCarBeans = data.getResult();
                shopCarAdapter.clearAll();
                shopCarAdapter.addAll(shopCarBeans);
                shopCarAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onError(Throwable throwable) {
            Log.d("张雨航shopCar", ">>throwable.getMessage:" + throwable.getMessage() + ";throwable.toString:" + throwable.toString());
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        shopCarPresenter.onBind();
        joinPresenter.onBind();
    }
}
