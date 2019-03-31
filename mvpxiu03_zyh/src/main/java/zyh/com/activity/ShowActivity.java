package zyh.com.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import zyh.com.fragment.CircleFragment;
import zyh.com.fragment.HomeFragment;
import zyh.com.fragment.MyFragment;
import zyh.com.fragment.OrderFragment;
import zyh.com.fragment.ShopCarFragment;

//切换按钮布局activity
public class ShowActivity extends AppCompatActivity {

    @BindView(R.id.radioGroup_showActivity)
    RadioGroup radioGroupShowActivity;
    private HomeFragment fragment01_home;
    private CircleFragment fragment02_circle;
    private ShopCarFragment fragment03_shop;
    private OrderFragment fragment04_list;
    private MyFragment fragment05_my;
    private FragmentManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        ButterKnife.bind(this);

        //获取管理器
        manager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = manager.beginTransaction();
        fragment01_home = new HomeFragment();
        fragment02_circle = new CircleFragment();
        fragment03_shop = new ShopCarFragment();
        fragment04_list = new OrderFragment();
        fragment05_my = new MyFragment();
        fragmentTransaction.add(R.id.showActivity_fragLayout,fragment01_home);
        fragmentTransaction.add(R.id.showActivity_fragLayout,fragment02_circle);
        fragmentTransaction.add(R.id.showActivity_fragLayout,fragment03_shop);
        fragmentTransaction.add(R.id.showActivity_fragLayout,fragment04_list);
        fragmentTransaction.add(R.id.showActivity_fragLayout,fragment05_my);
        fragmentTransaction.show(fragment01_home).hide(fragment02_circle).hide(fragment03_shop).hide(fragment04_list).hide(fragment05_my);
        fragmentTransaction.commit();

        //radioGroup按钮监听
        radioGroupShowActivity.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                FragmentTransaction transaction = manager.beginTransaction();
                switch (checkedId) {
                    case R.id.radioButton01_showActivity:
                        transaction.show(fragment01_home).hide(fragment02_circle).hide(fragment03_shop).hide(fragment04_list).hide(fragment05_my);
                        break;
                    case R.id.radioButton02_showActivity:
                        transaction.show(fragment02_circle).hide(fragment01_home).hide(fragment03_shop).hide(fragment04_list).hide(fragment05_my);
                        break;
                    case R.id.radioButton03_show:
                        transaction.show(fragment03_shop).hide(fragment02_circle).hide(fragment01_home).hide(fragment04_list).hide(fragment05_my);
                        break;
                    case R.id.radioButton04_showActivity:
                        transaction.show(fragment04_list).hide(fragment02_circle).hide(fragment03_shop).hide(fragment01_home).hide(fragment05_my);
                        break;
                    case R.id.radioButton05_showActivity:
                        transaction.show(fragment05_my).hide(fragment02_circle).hide(fragment03_shop).hide(fragment04_list).hide(fragment01_home);
                        break;
                }
                transaction.commit();
            }
        });//radioGroup按钮监听
    }
}
