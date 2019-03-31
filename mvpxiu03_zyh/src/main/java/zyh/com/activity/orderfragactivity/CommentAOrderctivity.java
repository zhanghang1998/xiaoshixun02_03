package zyh.com.activity.orderfragactivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import zyh.com.activity.R;
import zyh.com.app.MyAppliction;

//评论板块
public class CommentAOrderctivity extends AppCompatActivity {

    @BindView(R.id.eva_image)
    SimpleDraweeView evaImage;
    @BindView(R.id.eva_text_name)
    TextView evaTextName;
    @BindView(R.id.eva_text_price)
    TextView evaTextPrice;
    @BindView(R.id.eva_con)
    ConstraintLayout evaCon;
    @BindView(R.id.eva_text_content)
    EditText evaTextContent;
    @BindView(R.id.image_recy)
    RecyclerView imageRecy;
    @BindView(R.id.en_tongbu)
    CheckBox enTongbu;

    private long usersNum;
    private String sessionId;
    private SharedPreferences myUserSp;
    Button evaButtonNext;
    private String orderId;
    private String id;
    private Dialog first_dialog;
    private File first_file;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_aorderctivity);
        ButterKnife.bind(this);

        //用SharedPreferences获取userId和sessionId
        myUserSp = MyAppliction.getShare();
        String userId = myUserSp.getString("userId", "");
        usersNum = Integer.valueOf(userId);
        sessionId = myUserSp.getString("sessionId", "");
        //获取传送过来的值
        Intent intent = getIntent();
        String image = intent.getStringExtra("image");
        String name = intent.getStringExtra("name");
        String price = intent.getStringExtra("price");
        orderId = intent.getStringExtra("orderId");
        id = intent.getStringExtra("id");
        evaImage.setImageURI(Uri.parse(image));
        evaTextName.setText(name);
        evaTextPrice.setText(price);


    }




}
