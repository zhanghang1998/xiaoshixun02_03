package zyh.com.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import zyh.com.base.BaseContract;
import zyh.com.bean.Result;
import zyh.com.presenter.RegisterPresenter;
import zyh.com.util.JudgeUtil;

//注册页面
public class RegisterActivity extends AppCompatActivity implements BaseContract.BaseView<Result> {

    @BindView(R.id.reg_edit_phone)
    EditText regEditPhone;
    @BindView(R.id.reg_text_verification)
    TextView regTextVerification;
    @BindView(R.id.reg_edit_pass)
    EditText regEditPass;
    @BindView(R.id.reg_image_pass_eye)
    ImageView regImagePassEye;
    @BindView(R.id.reg_text_login)
    TextView regTextLogin;
    @BindView(R.id.reg_button_login)
    Button regButtonLogin;
    private RegisterPresenter registerPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        //调用p层
        registerPresenter = new RegisterPresenter(this);

    }

    //点击进行注册
    @OnClick(R.id.reg_button_login)
    public void onViewLogin() {
        //获取手机号和密码
        String phone = regEditPhone.getText().toString().trim();
        String pwd = regEditPass.getText().toString().trim();

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
        //注册请求
        registerPresenter.requestData(phone, pwd);
    }

    //点击文字回到登录页
    @OnClick(R.id.reg_text_login)
    public void onViewlogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    //注册页面的把密码保密
    @OnClick(R.id.reg_image_pass_eye)
    public void onClickEye() {
        regImagePassEye.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //判断事件的动作，按下，抬起
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    //从密码不可见模式变为密码可见模式
                    regEditPass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    //从密码可见模式变为密码不可见模式
                    regEditPass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                return true;
            }
        });
    }

    //进行账号注册
    @Override
    public void onCompleted(Result data) {

        //成功吐司
        Toast.makeText(this, data.getMessage() + "", Toast.LENGTH_SHORT).show();
        if (data.getStatus().equals("0000")) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onError(Throwable throwable) {
        Log.d("张雨航register", ">>throwable.getMessage:" + throwable.getMessage() + ";throwable.toString:" + throwable.toString());
    }

    //消除和解除p层绑定
    @Override
    protected void onDestroy() {
        super.onDestroy();
        registerPresenter.onBind();
        finish();
    }
}
