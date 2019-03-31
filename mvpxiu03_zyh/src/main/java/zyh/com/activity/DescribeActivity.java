package zyh.com.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.stx.xhb.xbanner.XBanner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import zyh.com.app.MyAppliction;
import zyh.com.base.BaseContract;
import zyh.com.bean.DescribeBean;
import zyh.com.bean.Result;
import zyh.com.bean.ShopCarBean;
import zyh.com.bean.ShopCarCommitBean;
import zyh.com.presenter.homefragpresenter.DescribePresenter;
import zyh.com.presenter.JoinPresenter;
import zyh.com.presenter.ShopCarPresenter;

//商品详情页面activity
public class DescribeActivity extends AppCompatActivity implements BaseContract.BaseView<Result<DescribeBean>> {

    @BindView(R.id.par_image_back)
    ImageView parImageBack;
    @BindView(R.id.par_goods)
    TextView parGoods;
    @BindView(R.id.par_par)
    TextView parPar;
    @BindView(R.id.comment)
    TextView comment;
    @BindView(R.id.layour1)
    LinearLayout parLin;
    @BindView(R.id.par_xbanner)
    XBanner parXbanner;
    @BindView(R.id.par_text_price)
    TextView parTextPrice;
    @BindView(R.id.par_text_num)
    TextView parTextNum;
    @BindView(R.id.par_text_name)
    TextView parTextName;
    @BindView(R.id.par_text_content)
    TextView parTextContent;
    @BindView(R.id.par_text_weight)
    TextView parTextWeight;
    @BindView(R.id.par_text_kg)
    TextView parTextKg;
    @BindView(R.id.par_text_qing)
    TextView parTextQing;
    @BindView(R.id.par_webview)
    WebView parWebview;
    @BindView(R.id.par_image_addshop)
    ImageView parImageAddshop;
    @BindView(R.id.par_image_buy)
    ImageView parImageBuy;
    private int numID;
    private int commodityIds;
    private DescribePresenter describePresenter;
    private SharedPreferences myUserSp;
    private ShopCarPresenter shopCarPresenter;
    private String sessionId;
    private long usersNum;
    private List<ShopCarBean> shopCarBeans;
    private List<ShopCarCommitBean> carList = new ArrayList<ShopCarCommitBean>();
    private JoinPresenter joinPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_describe);
        ButterKnife.bind(this);
        //用SharedPreferences获取userId和sessionId
        myUserSp = MyAppliction.getShare();
        String userId = myUserSp.getString("userId", "");
        usersNum = Integer.valueOf(userId);
        sessionId = myUserSp.getString("sessionId", "");
        //获取传送过来的id
        Intent intent = getIntent();
        commodityIds = intent.getIntExtra("comID", numID);
        //实现商品详情p层
        describePresenter = new DescribePresenter(this);
        shopCarPresenter = new ShopCarPresenter(new getshopcar());
        joinPresenter = new JoinPresenter(new joinCar());
        //详情请求数据
        describePresenter.requestData(usersNum, sessionId,commodityIds);
    }

    //添加购物车
    @OnClick(R.id.par_image_addshop)
    public void addShop(){
        //同步购物车 , 得先查询一下购物车
        shopCarPresenter.requestData(usersNum,sessionId);
    }

    //同步购物车回调方法
    public class joinCar implements BaseContract.BaseView<Result>{
        @Override
        public void onCompleted(Result data) {
            if (data.getStatus().equals("0000")) {
                Toast.makeText(DescribeActivity.this, data.getMessage() + "", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onError(Throwable throwable) {
            Toast.makeText(DescribeActivity.this, throwable.getMessage() + "", Toast.LENGTH_SHORT).show();
            Log.d("张雨航joinCar", ">>throwable.getMessage:" + throwable.getMessage() + ";throwable.toString:" + throwable.toString());
        }
    }

    //查询购物车数据回调
    public class getshopcar implements BaseContract.BaseView<Result<List<ShopCarBean>>>{

        @Override
        public void onCompleted(Result<List<ShopCarBean>> data) {

            if (data.getStatus().equals("0000")) {
                shopCarBeans = data.getResult();
                //同步购物车步骤
                // 1 . for循环把查询购物车的数据添加到bean类集合里面
                for (int i = 0; i < shopCarBeans.size(); i++) {
                    ShopCarBean carBean = shopCarBeans.get(i);
                    carList.add(new ShopCarCommitBean(carBean.getCommodityId(),carBean.getCount()));
                }
                //同步购物车方法
                addCar(carList);
            }//
        }

        @Override
        public void onError(Throwable throwable) {
            Log.d("张雨航shopCar", ">>throwable.getMessage:" + throwable.getMessage() + ";throwable.toString:" + throwable.toString());
        }
    }
    //同步购物车方法
    public void addCar(List<ShopCarCommitBean> listCar){
        // 2 .
        for (int i = 0; i< listCar.size(); i++){
            //判断如果加入商品的id和集合里有相同的就count+1
            if (commodityIds == listCar.get(i).getCommodityId()){
                int count = listCar.get(i).getCount();
                count++;
                listCar.get(i).setCount(count);
                break;
                //如果遍历完毕没有相同的商品，就把当前的商品加入到购物车
            }else if (i== listCar.size()-1){
                carList.add(new ShopCarCommitBean(commodityIds,1));
                break;
            }
        }//for

        //当购物车一条都没有的话 , 执行这个判断
        if (0== listCar.size()){
            carList.add(new ShopCarCommitBean(commodityIds,1));
        }

        // 3 . 把集合转换成String类型 , 然后进行同步
        Gson gson = new Gson();
        String json = gson.toJson(carList);
        Log.d("张雨航toJson", ""+json);
        joinPresenter.requestData(usersNum,sessionId,json);
    }

    //详情成功方法
    @Override
    public void onCompleted(Result<DescribeBean> data) {
        if (data.getStatus().equals("0000")) {
            DescribeBean result = data.getResult();
            parTextPrice.setText("￥"+result.getPrice());
            parTextNum.setText("已售"+result.getCommentNum()+"件");
            parTextName.setText(result.getCommodityName());
            parTextContent.setText(result.getDescribe());
            parTextKg.setText(result.getWeight()+"kg");
            //webView展示
            parWebview.loadDataWithBaseURL(null,result.getDetails(),"text/html","utf-8",null);
            final List<String> imageurl = new ArrayList<>();
            //先把图片分割 , 然后把图片Url放入list集合中
            String[] images= result.getPicture().split("\\,");
            for (int i=0;i<images.length;i++){
                imageurl.add(images[i]);
            }
            parXbanner.setData(imageurl,null);
            //Xbanner加载图片方法
            parXbanner.loadImage(new XBanner.XBannerAdapter() {
                @Override
                public void loadBanner(XBanner banner, Object model, View view, int position) {
                    Glide.with(DescribeActivity.this).load(imageurl.get(position)).into((ImageView) view);
                }
            });
        }
    }
    //详情失败方法
    @Override
    public void onError(Throwable throwable) {
        Log.d("张雨航describe", ">>throwable.getMessage:" + throwable.getMessage() + ";throwable.toString:" + throwable.toString());
    }

    //返回按钮
    @OnClick(R.id.par_image_back)
    public void finishBacks(){
        this.finish();
    }

    @Override
    public void onResume() {
        super.onResume();
        parXbanner.startAutoPlay();
    }

    @Override
    public void onStop() {
        super.onStop();
        parXbanner.stopAutoPlay();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        describePresenter.onBind();
        shopCarPresenter.onBind();
        joinPresenter.onBind();
        if (parWebview != null) {
            parWebview.setVisibility(View.GONE);
            parWebview.removeAllViews();
            parWebview.destroy();
        }
        finish();
    }
}
