package zyh.com.activity.myfragactivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;
import butterknife.ButterKnife;
import zyh.com.activity.R;
import zyh.com.bean.myfragbean.MyDataBean;

public class MyMassageActivity extends AppCompatActivity {

    @BindView(R.id.textView5)
    TextView textView5;
    @BindView(R.id.imageView_myMessage_head)
    SimpleDraweeView imageViewMyMessageHead;
    @BindView(R.id.textView_myMessage_name)
    TextView textViewMyMessageName;
    @BindView(R.id.textView_myMessage_phone)
    TextView textViewMyMessagePhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_massage);
        ButterKnife.bind(this);

        //获取值
        Intent intent = this.getIntent();
        MyDataBean myBean = (MyDataBean) intent.getSerializableExtra("myBean");

        //赋值
        textViewMyMessagePhone.setText(myBean.getPhone()+"");
        textViewMyMessageName.setText(myBean.getNickName()+"");
        imageViewMyMessageHead.setImageURI(Uri.parse(myBean.getHeadPic()));

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }
}
