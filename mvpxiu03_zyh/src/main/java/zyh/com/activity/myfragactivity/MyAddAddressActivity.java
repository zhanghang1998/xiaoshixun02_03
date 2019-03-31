package zyh.com.activity.myfragactivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lljjcoder.citypickerview.widget.CityPicker;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import zyh.com.activity.R;
import zyh.com.app.MyAppliction;
import zyh.com.base.BaseContract;
import zyh.com.bean.Result;
import zyh.com.presenter.myfragpresenter.MyAddAddressPresenter;

//新增收货地址
public class MyAddAddressActivity extends AppCompatActivity {

    @BindView(R.id.new_address_edit_name)
    EditText newAddressEditName;
    @BindView(R.id.new_address_edit_phone)
    EditText newAddressEditPhone;
    @BindView(R.id.new_address_edit_address)
    EditText newAddressEditAddress;
    @BindView(R.id.new_address_edit_zipCode)
    EditText newAddressEditZipCode;
    @BindView(R.id.new_address_text_city)
    TextView newAddressTextCity;
    private MyAddAddressPresenter myAddAddressPresenter;
    private SharedPreferences myUserSp;
    private String sessionId;
    private long usersNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_add_address);
        ButterKnife.bind(this);
        //用SharedPreferences获取userId和sessionId
        myUserSp = MyAppliction.getShare();
        String userId = myUserSp.getString("userId", "");
        usersNum = Integer.valueOf(userId);
        sessionId = myUserSp.getString("sessionId", "");
        //调用p层
        myAddAddressPresenter = new MyAddAddressPresenter(new newAddress());
    }

    //点击新增按钮 , 地址选项按钮
    @OnClick({R.id.new_address_image_open, R.id.new_address_text_save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.new_address_image_open://联动地址按钮

                CityPicker cityPicker = new CityPicker.Builder(this)
                        .textSize(14)
                        .title("请选择所在地区")
                        .titleBackgroundColor("#FFFFFF")
                        .confirTextColor("#ff0000")
                        .cancelTextColor("#696969")
                        .province("北京市")
                        .city("北京市")
                        .district("昌平区")
                        .textColor(Color.parseColor("#000000"))
                        .provinceCyclic(false)
                        .cityCyclic(false)
                        .districtCyclic(false)
                        .visibleItemsCount(7)
                        .itemPadding(10)
                        .onlyShowProvinceAndCity(false)
                        .build();
                cityPicker.show();
                //监听方法，获取选择结果
                cityPicker.setOnCityItemClickListener(new CityPicker.OnCityItemClickListener() {
                    @Override
                    public void onSelected(String... citySelected) {
                        //省份
                        String province = citySelected[0];
                        //城市
                        String city = citySelected[1];
                        //区县（如果设定了两级联动，那么该项返回空）
                        String district = citySelected[2];
                        //邮编
                        //String code = citySelected[3];
                        //为TextView赋值
                        newAddressTextCity.setText(province + " " + city + " " + district + " ");
                    }
                });
                break;
            case R.id.new_address_text_save://输入框数据 , 然后执行创建

                String save_name = newAddressEditName.getText().toString().trim();
                String save_phone = newAddressEditPhone.getText().toString().trim();
                String save_city = newAddressTextCity.getText().toString().trim();
                String save_add = newAddressEditAddress.getText().toString().trim();
                String save_zipCode = newAddressEditZipCode.getText().toString().trim();
                String city_add = save_city+","+save_add;
                //MyNewAddress myNewAddress = new MyNewAddress(save_city, save_add);
                //String save_address = new Gson().toJson(myNewAddress);
                Map<String,String> params=new HashMap<>();
                params.put("realName",save_name);
                params.put("phone",save_phone);
                params.put("address",city_add);
                params.put("zipCode",save_zipCode);

                //进行数据请求
                myAddAddressPresenter.requestData(usersNum,sessionId,params);

                break;
        }
    }

    //添加收货地址回调方法
    private class newAddress implements BaseContract.BaseView<Result> {
        @Override
        public void onCompleted(Result data) {

            Toast.makeText(MyAddAddressActivity.this, data.getMessage() + "", Toast.LENGTH_SHORT).show();
            if (data.getStatus().equals("0000")) {
                //成功销毁此页面
                Intent intent = new Intent(MyAddAddressActivity.this, MyShopAddressActivity.class);
                startActivity(intent);
                finish();
            }
        }

        @Override
        public void onError(Throwable throwable) {
            Log.d("张雨航addAddress", ">>throwable.getMessage:" + throwable.getMessage() + ";throwable.toString:" + throwable.toString());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        myAddAddressPresenter.onBind();
        finish();
    }
}
