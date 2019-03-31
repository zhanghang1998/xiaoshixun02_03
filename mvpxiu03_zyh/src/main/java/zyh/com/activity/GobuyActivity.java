package zyh.com.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import zyh.com.adater.CreatOrderAdapter;
import zyh.com.app.MyAppliction;
import zyh.com.base.BaseContract;
import zyh.com.bean.Result;
import zyh.com.bean.ShopCarBean;
import zyh.com.bean.myfragbean.MyShopAddressBean;
import zyh.com.bean.orderfragbean.CreateBean;
import zyh.com.presenter.CreateOrderPresenter;
import zyh.com.presenter.myfragpresenter.MyShopAddressPresenter;

//创建订单页面
public class GobuyActivity extends AppCompatActivity {

    @BindView(R.id.creation_name)
    TextView gobuyName;
    @BindView(R.id.creation_phone)
    TextView gobuyPhone;
    @BindView(R.id.creation_address)
    TextView gobuyAddress;
    @BindView(R.id.creation_shop_recy)
    RecyclerView gobuyShopRecy;
    @BindView(R.id.creation_image_pop)
    ImageView creationImagePop;
    @BindView(R.id.cretion_text_allnum)
    TextView cretionTextAllnum;
    @BindView(R.id.text_text)
    TextView textText;
    @BindView(R.id.cretion_text_allprice)
    TextView cretionTextAllprice;
    @BindView(R.id.creation_rmb)
    TextView creationRmb;
    private List<ShopCarBean> creation_bill;
    private SharedPreferences myUserSp;
    private String sessionId;
    private long usersNum;
    private MyShopAddressPresenter myShopAddressPresenter;
    private CreateOrderPresenter createOrderPresenter;
    private CreatOrderAdapter creatOrderAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gobuy);
        ButterKnife.bind(this);

        //用SharedPreferences获取userId和sessionId
        myUserSp = MyAppliction.getShare();
        String userId = myUserSp.getString("userId", "");
        usersNum = Integer.valueOf(userId);
        sessionId = myUserSp.getString("sessionId", "");
        //获取传过来的值
        Intent intent = getIntent();
        creation_bill = (List<ShopCarBean>) intent.getSerializableExtra("creation_bill");
        //调用p层
        myShopAddressPresenter = new MyShopAddressPresenter(new getQueryAddress());//收货地址p层
        createOrderPresenter = new CreateOrderPresenter(new getGoBuy());//创建订单p层
        //布局管理器
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        gobuyShopRecy.setLayoutManager(manager);
        //收货地址数据请求
        myShopAddressPresenter.requestData(usersNum, sessionId);
        //适配器
        creatOrderAdapter = new CreatOrderAdapter(this);
        gobuyShopRecy.setAdapter(creatOrderAdapter);
        creatOrderAdapter.ClearAll();//清空
        creatOrderAdapter.addAll(creation_bill);
        creatOrderAdapter.notifyDataSetChanged();//刷新适配器

        //计算价格
        getcount();
    }

    @OnClick(R.id.creation_text_go)
    public void onViewClicked() {//创建订单
        //提交的字符串
        List<CreateBean> addlist = new ArrayList<>();

        for (int i = 0; i < creation_bill.size(); i++) {

            ShopCarBean shopCarBean = creation_bill.get(i);
            addlist.add(new CreateBean(shopCarBean.getCommodityId(), shopCarBean.getCount()));

        }
        String orderInfo = new Gson().toJson(addlist);
        //支付的总金额
        String totalPrice = cretionTextAllprice.getText().toString();
        Map<String, String> params = new HashMap<>();
        params.put("orderInfo", orderInfo);
        params.put("totalPrice", totalPrice);
        params.put("addressId", String.valueOf(addressBeanId));
        createOrderPresenter.requestData(usersNum, sessionId, params);

    }

    //计算价格
    public void getcount() {
        //价格
        double prices = 0;
        //数量
        int counts = 0;
        for (int i = 0; i < creation_bill.size(); i++) {
            //获取一条数据
            ShopCarBean shopCarBean = creation_bill.get(i);
            //计算价格
            prices = prices + shopCarBean.getCount() * shopCarBean.getPrice();
            //计算数量
            counts = counts + shopCarBean.getCount();
        }
        cretionTextAllnum.setText("" + counts);
        cretionTextAllprice.setText("" + prices);
    }

    //创建订单回调方法
    private class getGoBuy implements BaseContract.BaseView<Result> {
        @Override
        public void onCompleted(Result data) {
            Toast.makeText(GobuyActivity.this, data.getMessage()+"",Toast.LENGTH_SHORT).show();
            if (data.getStatus().equals("0000")) {
                //创建成功销毁此页面
                finish();
            }
        }

        @Override
        public void onError(Throwable throwable) {

        }
    }

    private List<MyShopAddressBean> myShopAddressBeans;
    //地址id
    private int addressBeanId;

    //查询收货地址回调方法
    private class getQueryAddress implements BaseContract.BaseView<Result<List<MyShopAddressBean>>> {


        @Override
        public void onCompleted(Result<List<MyShopAddressBean>> data) {
            if (data.getStatus().equals("0000")) {
                myShopAddressBeans = data.getResult();
                MyShopAddressBean myShopAddressBean = myShopAddressBeans.get(0);
                addressBeanId = myShopAddressBean.getId();
                gobuyName.setText(myShopAddressBean.getRealName());
                gobuyPhone.setText(myShopAddressBean.getPhone());
                gobuyAddress.setText(myShopAddressBean.getAddress());
            }
        }

        @Override
        public void onError(Throwable throwable) {
            Log.d("张雨航address", ">>throwable.getMessage:" + throwable.getMessage() + ";throwable.toString:" + throwable.toString());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        myShopAddressPresenter.onBind();
        createOrderPresenter.onBind();
        finish();
    }

}
