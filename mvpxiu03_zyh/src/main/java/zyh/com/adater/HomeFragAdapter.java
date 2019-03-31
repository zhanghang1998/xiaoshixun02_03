package zyh.com.adater;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import zyh.com.activity.R;
import zyh.com.bean.homefragbean.ComListBean;

//首页商品数据适配器adapter
public class HomeFragAdapter extends RecyclerView.Adapter<HomeFragAdapter.MyHoader> {

    List<ComListBean> list = new ArrayList<>();
    private Context context;
    public static final int HOME01=1;
    public static final int HOME02=2;
    public static final int HOME03=3;
    private int types;

    public HomeFragAdapter(Context context, int types) {
        this.context = context;
        this.types = types;
    }

    @NonNull
    @Override
    public HomeFragAdapter.MyHoader onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (types==HOME01) {
            View view = View.inflate(context, R.layout.fragment_home_recyclerview01, null);
            return new MyHoader(view);
        } else if (types == HOME02) {
            View view = View.inflate(context, R.layout.fragment_home_recyclerview02, null);
            return new MyHoader(view);
        } else {
            View view = View.inflate(context, R.layout.fragment_home_recyclerview03, null);
            return new MyHoader(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull HomeFragAdapter.MyHoader myHoader, int i) {

        final ComListBean comListBean = list.get(i);
        myHoader.text_count.setText(comListBean.getCommodityName()+"");
        myHoader.text_price.setText("￥"+comListBean.getPrice());
        //图片加载
        myHoader.sDV_image.setImageURI(Uri.parse(comListBean.getMasterPic()));
        //设置条目点击
        myHoader.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onIitmClick.initComeID(comListBean.getCommodityId());
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addAll(List<ComListBean> comListBeans) {
        if (comListBeans != null) {
            list.addAll(comListBeans);
        }
    }

    public void clearAll() {
        list.clear();
    }

    public class MyHoader extends RecyclerView.ViewHolder {

        private final SimpleDraweeView sDV_image;
        private final TextView text_price;
        private final TextView text_count;

        public MyHoader(@NonNull View itemView) {
            super(itemView);

            sDV_image = itemView.findViewById(R.id.homeFrag_SDV_image);
            text_price = itemView.findViewById(R.id.homeFrag_price);
            text_count = itemView.findViewById(R.id.homeFrag_text);

        }
    }

    private OnIitmClick onIitmClick;

    public interface OnIitmClick{
        void initComeID(int cid);
    }

    public void setOnIitmClick(OnIitmClick onIitmClick) {
        this.onIitmClick = onIitmClick;
    }
}
