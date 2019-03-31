package zyh.com.activity;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import zyh.com.app.MyAppliction;
import zyh.com.base.BaseContract;
import zyh.com.bean.LoginBean;
import zyh.com.bean.Result;
import zyh.com.presenter.LoginPresenter;
import zyh.com.util.JudgeUtil;

//登录页面
public class LoginActivity extends AppCompatActivity implements BaseContract.BaseView<Result<LoginBean>> {

    @BindView(R.id.login_edit_phone)
    EditText loginEditPhone;
    @BindView(R.id.login_edit_pass)
    EditText loginEditPass;
    @BindView(R.id.login_image_pass_eye)
    ImageView loginImagePassEye;
    @BindView(R.id.login_button_login)
    Button loginButtonLogin;
    @BindView(R.id.login_box_remember)
    CheckBox loginBoxRemember;
    @BindView(R.id.login_text_reg)
    TextView loginTextReg;
    private LoginPresenter loginPresenter;
    private SharedPreferences myUserSp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        //使用SharedPreferences
        myUserSp = MyAppliction.getShare();
        //记住密码
        getEdit();
        //调用p层
        loginPresenter = new LoginPresenter(this);
    }

    //记住密码方法
    private void getEdit() {
        //获取记住密码的状态值
        boolean box_ischeck = myUserSp.getBoolean("box_ischeck", false);
        if (box_ischeck) {
            String phone = myUserSp.getString("phone", null);
            String pass = myUserSp.getString("pwd", null);
            loginEditPhone.setText(phone);
            loginEditPass.setText(pass);
            loginBoxRemember.setChecked(true);
        }
    }

    //点击进行登录
    @OnClick(R.id.login_button_login)
    public void onViewClicked() {
        //获取手机号和密码
        String phone = loginEditPhone.getText().toString().trim();
        String pwd = loginEditPass.getText().toString().trim();

        if (JudgeUtil.isNull(phone)) {
            Toast.makeText(this, "输入框不能为空", Toast.LENGTH_SHORT).show();
            return;//judge
        }
        if (JudgeUtil.isNull(pwd)) {
            Toast.makeText(this, "输入框不能为空", Toast.LENGTH_SHORT).show();
            return;//judge
        }
        if (!(JudgeUtil.isPhone(phone))) {
            Toast.makeText(this, "手机格式错误", Toast.LENGTH_SHORT).show();
            return;
        }
        if (JudgeUtil.isPass(pwd)) {
            Toast.makeText(this, "密码不能少于6位", Toast.LENGTH_SHORT).show();
            return;
        }

        if (loginBoxRemember.isChecked()) {
            SharedPreferences.Editor edit = myUserSp.edit();
            edit.putString("phone", phone);
            edit.putString("pwd", pwd);
            edit.putBoolean("box_ischeck", true);
            edit.commit();//提交
        } else {
            SharedPreferences.Editor edit = myUserSp.edit();
            //清除所有的状态
            edit.clear();
            edit.commit();
        }
        //登录请求
        loginPresenter.requestData(phone, pwd);

    }

    //密码保密
    @OnClick(R.id.login_image_pass_eye)
    public void onImageEye() {

        loginImagePassEye.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //判断事件的动作，按下，抬起
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    //从密码不可见模式变为密码可见模式
                    loginEditPass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    //从密码可见模式变为密码不可见模式
                    loginEditPass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                return true;
            }
        });
    }

    @Override
    public void onCompleted(Result<LoginBean> data) {

        //成功吐司
        Toast.makeText(this, data.getMessage() + "", Toast.LENGTH_SHORT).show();
        if (data.getStatus().equals("0000")) {
            LoginBean result = data.getResult();
            SharedPreferences.Editor edit = myUserSp.edit();
            edit.putString("userId", result.getUserId()+"");
            edit.putString("sessionId", result.getSessionId());
            edit.commit();//提交
            //跳转到商品首页
            Intent intent = new Intent(this, ShowActivity.class);
            startActivity(intent);
            finish();
        }

    }

    @Override
    public void onError(Throwable throwable) {
        Log.d("张雨航login", ">>throwable.getMessage:" + throwable.getMessage() + ";throwable.toString:" + throwable.toString());
    }

    //跳转到注册页面
    @OnClick(R.id.login_text_reg)
    public void onIntant() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    //消除和解除p层绑定
    @Override
    protected void onDestroy() {
        super.onDestroy();
        loginPresenter.onBind();
        finish();
    }
}
