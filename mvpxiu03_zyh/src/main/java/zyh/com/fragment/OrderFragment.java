package zyh.com.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import zyh.com.activity.R;
import zyh.com.activity.orderfragactivity.CommentAOrderctivity;
import zyh.com.activity.orderfragactivity.PayOrderActivity;
import zyh.com.adater.orderfragadapter.OrderFragListAdapter;
import zyh.com.app.MyAppliction;
import zyh.com.base.BaseContract;
import zyh.com.bean.Result;
import zyh.com.bean.orderfragbean.OrderListBean;
import zyh.com.bean.orderfragbean.OrderListDatabean;
import zyh.com.presenter.orderfragpresenter.DeleteOrderPresenter;
import zyh.com.presenter.orderfragpresenter.OrderFragListPresenter;
import zyh.com.presenter.orderfragpresenter.TakeOrderPresenter;

//订单模块
public class OrderFragment extends Fragment implements XRecyclerView.LoadingListener{

    @BindView(R.id.bill_xrecy)
    XRecyclerView billXrecy;
    Unbinder unbinder;
    @BindView(R.id.bill_group)
    LinearLayout billGroup;
    private long usersNum;
    private String sessionId;
    private SharedPreferences myUserSp;
    private OrderFragListPresenter orderFragListPresenter;
    private OrderFragListAdapter orderAdapterQuanbu,orderAdapterFukuan,orderAdapterShouhuo,orderAdapterPingjia,orderAdapterWancheng;
    private int status=0,all_staus;
    private int index;
    private DeleteOrderPresenter deleteOrderPresenter;
    private TakeOrderPresenter takeOrderPresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order, container, false);
        unbinder = ButterKnife.bind(this, view);

        //用SharedPreferences获取userId和sessionId
        myUserSp = MyAppliction.getShare();
        String userId = myUserSp.getString("userId", "");
        usersNum = Integer.valueOf(userId);
        sessionId = myUserSp.getString("sessionId", "");
        //订单列表样式设置
        LinearLayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        billXrecy.setLayoutManager(manager);
        billXrecy.setLoadingListener(this);
        billXrecy.setLoadingMoreEnabled(true);
        billXrecy.setPullRefreshEnabled(true);
        //调用presenter 层
        deleteOrderPresenter = new DeleteOrderPresenter(new initMyData());//删除订单p层
        orderFragListPresenter = new OrderFragListPresenter(new queryOrde());//列表数据
        takeOrderPresenter = new TakeOrderPresenter(new initMyTake());//收货p层

        return view;
    }

    //点击方法
    public void initMyClick(){

        //删除订单接口回调
        orderAdapterFukuan.setDelete(new OrderFragListAdapter.ClickDelete() {
            @Override
            public void delete(final String orderId, final int position) {

                final AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
                builder.setTitle("确定删除订单吗？");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteOrderPresenter.requestData(usersNum,sessionId,orderId);
                        index = position;
                    }
                });
                builder.setNeutralButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                builder.show();

            }
        });//删除订单接口回调

        //付款
        orderAdapterFukuan.setGo(new OrderFragListAdapter.ClickGo() {
            @Override
            public void go(String orderId, String all_price) {
                Intent intent=new Intent(getActivity(),PayOrderActivity.class);
                intent.putExtra("orderId",orderId);
                intent.putExtra("all_price",all_price);
                startActivity(intent);
            }
        });//付款

    }

    //订单列表下拉刷新
    @Override
    public void onRefresh() {
        if (orderFragListPresenter.isRunning()) {
            billXrecy.refreshComplete();
        }
        initBilllUrl(all_staus,true);
    }
    //订单列表上拉加载
    @Override
    public void onLoadMore() {
        if (orderFragListPresenter.isRunning()) {
            billXrecy.loadMoreComplete();
        }
        initBilllUrl(all_staus,false);
    }

    //订单五个大按钮点击方法
    @OnClick({R.id.bill_image_allbill, R.id.bill_image_pay, R.id.bill_image_task, R.id.bill_image_appraise, R.id.bill_image_finish})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bill_image_allbill://全部订单
                //适配器
                orderAdapterQuanbu = new OrderFragListAdapter(getContext(),OrderFragListAdapter.QUANBU);//全部
                //添加适配器
                billXrecy.setAdapter(orderAdapterQuanbu);//全部
                //设置全部订单背景边框
                billXrecy.setBackgroundResource(R.drawable.fragment_order_bg_quanbu);
                billGroup.setBackgroundResource(R.drawable.fragment_order_bg_quanbu);
                //请求
                status=0;
                initBilllUrl(status,true);
                orderAdapterQuanbu.clearAll();
                orderAdapterQuanbu.addAll(orderListBeans);
                orderAdapterQuanbu.notifyDataSetChanged();

                break;
            case R.id.bill_image_pay://待付款
                //适配器
                orderAdapterFukuan = new OrderFragListAdapter(getContext(),OrderFragListAdapter.FUKUAN);//付款
                //添加适配器
                billXrecy.setAdapter(orderAdapterFukuan);//付款
                //设置待付款背景边框
                billXrecy.setBackgroundResource(R.drawable.fragment_order_bg_fukaun);
                billGroup.setBackgroundResource(R.drawable.fragment_order_bg_fukaun);
                //请求
                status=1;
                initBilllUrl(status,true);

                orderAdapterFukuan.clearAll();
                orderAdapterFukuan.addAll(orderListBeans);
                orderAdapterFukuan.notifyDataSetChanged();

                //付款点击方法
                initMyClick();
                break;
            case R.id.bill_image_task://待收货
                //适配器
                orderAdapterShouhuo = new OrderFragListAdapter(getContext(),OrderFragListAdapter.SHOUHUO);//收货
                //添加适配器
                billXrecy.setAdapter(orderAdapterShouhuo);//收货
                //设置待收货背景边框
                billXrecy.setBackgroundResource(R.drawable.fragment_order_bg_shouhuo);
                billGroup.setBackgroundResource(R.drawable.fragment_order_bg_shouhuo);
                //请求
                status=2;
                initBilllUrl(status,true);

                orderAdapterShouhuo.clearAll();
                orderAdapterShouhuo.addAll(orderListBeans);
                orderAdapterShouhuo.notifyDataSetChanged();

                //确认收货接口回调方法
                orderAdapterShouhuo.setNext(new OrderFragListAdapter.ClickNext() {
                    @Override
                    public void next(String orderId) {
                        takeOrderPresenter.requestData(usersNum,sessionId,orderId);
                    }
                });

                break;
            case R.id.bill_image_appraise://待评价

                orderAdapterPingjia = new OrderFragListAdapter(getContext(),OrderFragListAdapter.PINGJIA);//评价
                //添加适配器
                billXrecy.setAdapter(orderAdapterPingjia);//收货
                billXrecy.setBackgroundResource(R.drawable.fragment_order_bg_pingjia);
                billGroup.setBackgroundResource(R.drawable.fragment_order_bg_pingjia);
                //请求
                status=3;
                initBilllUrl(status,true);

                orderAdapterPingjia.clearAll();
                orderAdapterPingjia.addAll(orderListBeans);
                orderAdapterPingjia.notifyDataSetChanged();

                //去评价
                orderAdapterPingjia.setEva(new OrderFragListAdapter.ClickEvaluate() {
                    @Override
                    public void setEvaluat(List<OrderListDatabean> orderListDatabeans, int position, OrderListBean orderListBean, int i) {
                        Intent intent = new Intent(getActivity(),CommentAOrderctivity.class);
                        String[] split = orderListDatabeans.get(position).getCommodityPic().split("\\,");
                        intent.putExtra("image",split[0]);
                        intent.putExtra("id",orderListDatabeans.get(position).getCommodityId()+"");
                        intent.putExtra("orderId",orderListBean.getOrderId());
                        intent.putExtra("name",orderListDatabeans.get(position).getCommodityName());
                        intent.putExtra("price","￥"+orderListDatabeans.get(position).getCommodityPrice());
                        startActivity(intent);
                    }
                });

                break;
            case R.id.bill_image_finish://完成
                orderAdapterWancheng = new OrderFragListAdapter(getContext(),OrderFragListAdapter.WANCHENG);//完成
                billXrecy.setBackgroundResource(R.drawable.fragment_order_bg_wancheng);
                billGroup.setBackgroundResource(R.drawable.fragment_order_bg_wancheng);
                break;
        }
    }

    //根据订单的状态查询订单
    private void initBilllUrl(int status, boolean page) {
        all_staus = status;
        //数据请求
        orderFragListPresenter.requestData(usersNum, sessionId, page,all_staus);
    }

    //确认收货回调数据方法
    private class initMyTake implements BaseContract.BaseView<Result> {
        @Override
        public void onCompleted(Result data) {

            Toast.makeText(getContext(), ""+data.getMessage(),Toast.LENGTH_SHORT).show();
            if (data.getStatus().equals("0000")) {
                orderAdapterFukuan.deleteOrder(index);
                initBilllUrl(2,true);
            }
        }

        @Override
        public void onError(Throwable throwable) {
            Log.d("收货", "" + throwable);
        }
    }

    //删除订单回调数据方法
    private class initMyData implements BaseContract.BaseView<Result> {
        @Override
        public void onCompleted(Result data) {

            Toast.makeText(getContext(), ""+data.getMessage(),Toast.LENGTH_SHORT).show();
            if (data.getStatus().equals("0000")) {
                orderAdapterFukuan.deleteOrder(index);
            }
        }

        @Override
        public void onError(Throwable throwable) {
            Log.d("delete", "" + throwable);
        }
    }

    private List<OrderListBean> orderListBeans;
    //订单列表数据回调的方法
    private class queryOrde implements BaseContract.BaseView<Result<List<OrderListBean>>> {

        @Override
        public void onCompleted(Result<List<OrderListBean>> data) {
            billXrecy.refreshComplete();
            billXrecy.loadMoreComplete();//
            if (data.getStatus().equals("0000")) {
                orderListBeans = data.getOrderList();
            }
        }

        @Override
        public void onError(Throwable throwable) {
            Log.d("xx", "" + throwable);
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
        deleteOrderPresenter.onBind();
        orderFragListPresenter.onBind();
        takeOrderPresenter.onBind();
    }
}
