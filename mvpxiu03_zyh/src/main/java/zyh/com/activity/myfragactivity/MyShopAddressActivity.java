package zyh.com.activity.myfragactivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import zyh.com.activity.R;
import zyh.com.adater.myfragadapter.MyAddressAdapter;
import zyh.com.app.MyAppliction;
import zyh.com.base.BaseContract;
import zyh.com.bean.Result;
import zyh.com.bean.myfragbean.MyShopAddressBean;
import zyh.com.presenter.myfragpresenter.MyShopAddressPresenter;

public class MyShopAddressActivity extends AppCompatActivity {

    @BindView(R.id.textView2)
    TextView textView2;
    @BindView(R.id.textView_myMessage_perfect)
    TextView textViewMyMessagePerfect;
    @BindView(R.id.recyclerView_address)
    RecyclerView recyclerViewAddress;
    @BindView(R.id.button_address_add)
    Button buttonAddressAdd;
    private MyShopAddressPresenter myShopAddressPresenter;
    private MyAddressAdapter myAddressAdapter;
    private SharedPreferences myUserSp;
    private String sessionId;
    private long usersNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_shop_address);
        ButterKnife.bind(this);
        //用SharedPreferences获取userId和sessionId
        myUserSp = MyAppliction.getShare();
        String userId = myUserSp.getString("userId", "");
        usersNum = Integer.valueOf(userId);
        sessionId = myUserSp.getString("sessionId", "");
        //调用presenter层
        myShopAddressPresenter = new MyShopAddressPresenter(new queryMyaddress());
        //列表样式
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewAddress.setLayoutManager(layoutManager);
        //适配器
        myAddressAdapter = new MyAddressAdapter(this);
        recyclerViewAddress.setAdapter(myAddressAdapter);
        //收货地址数据请求方法
        myShopAddressPresenter.requestData(usersNum,sessionId);

    }

    //点击方法
    @OnClick({R.id.textView_myMessage_perfect, R.id.button_address_add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.textView_myMessage_perfect://完成按钮
                this.finish();
                break;
            case R.id.button_address_add://添加收货地址
                Intent intent = new Intent(this,MyAddAddressActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }

    private List<MyShopAddressBean> shopAddressBeans;
    //收货地址数据回调
    private class queryMyaddress implements BaseContract.BaseView<Result<List<MyShopAddressBean>>> {

        @Override
        public void onCompleted(Result<List<MyShopAddressBean>> data) {
            if (data.getStatus().equals("0000")) {
                shopAddressBeans = data.getResult();
                myAddressAdapter.allClear();//清空集合
                myAddressAdapter.addAll(shopAddressBeans);
                myAddressAdapter.notifyDataSetChanged();
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
        finish();
    }

}
