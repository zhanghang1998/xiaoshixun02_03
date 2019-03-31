package zyh.com.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import zyh.com.activity.LoginActivity;
import zyh.com.activity.R;
import zyh.com.activity.myfragactivity.MyCircleActivity;
import zyh.com.activity.myfragactivity.MyFootmarkActivity;
import zyh.com.activity.myfragactivity.MyMassageActivity;
import zyh.com.activity.myfragactivity.MyShopAddressActivity;
import zyh.com.activity.myfragactivity.MyWalletActivity;
import zyh.com.app.MyAppliction;
import zyh.com.base.BaseContract;
import zyh.com.bean.Result;
import zyh.com.bean.myfragbean.MyDataBean;
import zyh.com.bean.myfragbean.MyFootmarkBean;
import zyh.com.presenter.myfragpresenter.MyDataPresenter;

//我的页面
public class MyFragment extends Fragment {

    private static SharedPreferences myUserSp;
    @BindView(R.id.me_nickname)
    TextView meNickname;
    @BindView(R.id.text_frag05_myMessage)
    TextView textFrag05MyMessage;
    @BindView(R.id.text_frag05_myCircle)
    TextView textFrag05MyCircle;
    @BindView(R.id.text_frag05_myFootprint)
    TextView textFrag05MyFootprint;
    @BindView(R.id.text_frag05_myWallet)
    TextView textFrag05MyWallet;
    @BindView(R.id.text_frag05_myAddress)
    TextView textFrag05MyAddress;
    @BindView(R.id.button_frag05_my_finish)
    Button buttonFrag05MyFinish;
    @BindView(R.id.me_avatar)
    SimpleDraweeView meAvatar;
    Unbinder unbinder;
    private long usersNum;
    private String sessionId;
    private MyDataPresenter myDataPresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my, container, false);
        unbinder = ButterKnife.bind(this, view);
        //用SharedPreferences获取userId和sessionId
        myUserSp = MyAppliction.getShare();
        String userId = myUserSp.getString("userId", "");
        usersNum = Integer.valueOf(userId);
        sessionId = myUserSp.getString("sessionId", "");
        //调用p层
        myDataPresenter = new MyDataPresenter(new queryData());//个人信息p层
        //个人信息数据请求
        myDataPresenter.requestData(usersNum,sessionId);

        return view;
    }


    @OnClick({R.id.text_frag05_myMessage, R.id.text_frag05_myCircle, R.id.text_frag05_myFootprint, R.id.text_frag05_myWallet, R.id.text_frag05_myAddress, R.id.button_frag05_my_finish})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.text_frag05_myMessage://点击进入个人信息页面

                //把个人信息传过去
                Intent intentMy = new Intent(getActivity(), MyMassageActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("myBean",myDataBean);
                intentMy.putExtras(bundle);
                startActivity(intentMy);

                break;
            case R.id.text_frag05_myCircle://跳转到我的圈子页面
                startActivity(new Intent(getActivity(),MyCircleActivity.class));
                break;
            case R.id.text_frag05_myFootprint://进入我的足迹
                startActivity(new Intent(getActivity(),MyFootmarkActivity.class));
                break;
            case R.id.text_frag05_myWallet://跳转到我的钱包页面
                startActivity(new Intent(getActivity(),MyWalletActivity.class));
                break;
            case R.id.text_frag05_myAddress://进入收货地址页面
                startActivity(new Intent(getActivity(),MyShopAddressActivity.class));
                break;
            case R.id.button_frag05_my_finish://点击退出登录
                SharedPreferences.Editor edit = MyAppliction.getShare().edit();
                edit.clear().commit();
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);//跳转到登录页
                getActivity().finish();//本页面关闭
                break;
        }
    }

    private MyDataBean myDataBean;

    //我的信息回调
    private class queryData implements BaseContract.BaseView<Result<MyDataBean>> {

        @Override
        public void onCompleted(Result<MyDataBean> data) {
            if (data.getStatus().equals("0000")) {
                myDataBean = data.getResult();
                meAvatar.setImageURI(Uri.parse(myDataBean.getHeadPic()));
                meNickname.setText(myDataBean.getNickName());
            }
        }

        @Override
        public void onError(Throwable throwable) {
            Log.d("张雨航myData", ">>throwable.getMessage:" + throwable.getMessage() + ";throwable.toString:" + throwable.toString());
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
        myDataPresenter.onBind();
    }
}
