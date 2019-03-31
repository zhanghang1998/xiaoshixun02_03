package zyh.com.activity.orderfragactivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import zyh.com.activity.R;
import zyh.com.app.MyAppliction;
import zyh.com.base.BaseContract;
import zyh.com.bean.Result;
import zyh.com.presenter.orderfragpresenter.PayOrderPresenter;

//余额支付页面
public class PayOrderActivity extends AppCompatActivity {

    @BindView(R.id.pay_group)
    RadioGroup payGroup;
    @BindView(R.id.pay_button_go)
    Button payButtonGo;
    @BindView(R.id.pay_con)
    ConstraintLayout payCon;
    @BindView(R.id.pay_text_finish)
    TextView payTextFinish;
    @BindView(R.id.pay_text_success)
    TextView payTextSuccess;
    @BindView(R.id.pay_rela_success)
    RelativeLayout payRelaSuccess;
    @BindView(R.id.pay_text_falied)
    TextView payTextFalied;
    @BindView(R.id.pay_rela_falied)
    RelativeLayout payRelaFalied;
    private long usersNum;
    private String sessionId;
    private SharedPreferences myUserSp;
    private String orderId;
    private int payType=0;
    private PayOrderPresenter payOrderPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_order);
        ButterKnife.bind(this);
        //用SharedPreferences获取userId和sessionId
        myUserSp = MyAppliction.getShare();
        String userId = myUserSp.getString("userId", "");
        usersNum = Integer.valueOf(userId);
        sessionId = myUserSp.getString("sessionId", "");
        //调用pceng
        payOrderPresenter = new PayOrderPresenter(new initPay());
        //获取传送的数据
        Intent intent = getIntent();
        orderId = intent.getStringExtra("orderId");
        String all_prices = intent.getStringExtra("all_price");
        payButtonGo.setText("余额支付" + all_prices + "元");
        //布局设置
        payCon.setVisibility(View.VISIBLE);
        payRelaFalied.setVisibility(View.GONE);
        payRelaSuccess.setVisibility(View.GONE);

        //支付按钮监听方法
        payGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.pay_money:
                        payType=1;
                        break;
                    case R.id.pay_weixin:
                        payType=2;
                        break;
                    case R.id.pay_zhifubao:
                        payType=3;
                        break;
                }
            }
        });//支付按钮监听方法

        //支付按钮
        payButtonGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                payOrderPresenter.requestData(usersNum,sessionId,orderId,payType);
            }
        });

    }

    //支付回调方法
    private class initPay implements BaseContract.BaseView<Result> {
        @Override
        public void onCompleted(Result data) {
            Toast.makeText(PayOrderActivity.this, ""+data.getMessage(),Toast.LENGTH_SHORT).show();
            if (data.getStatus().equals("0000")) {
                payRelaFalied.setVisibility(View.GONE);
                payRelaSuccess.setVisibility(View.VISIBLE);
                payTextFinish.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });
                payTextSuccess.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        payCon.setVisibility(View.VISIBLE);
                        payRelaFalied.setVisibility(View.GONE);
                        payRelaSuccess.setVisibility(View.GONE);
                        finish();
                    }
                });
            } else {

                payRelaFalied.setVisibility(View.VISIBLE);
                payRelaSuccess.setVisibility(View.GONE);
                payTextFalied.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        payCon.setVisibility(View.VISIBLE);
                        payRelaFalied.setVisibility(View.GONE);
                        payRelaSuccess.setVisibility(View.GONE);
                    }
                });

            }
        }

        @Override
        public void onError(Throwable throwable) {
            Log.d("payorder", "" + throwable);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //解绑
        payOrderPresenter.onBind();
        finish();//销毁页面
    }
}
