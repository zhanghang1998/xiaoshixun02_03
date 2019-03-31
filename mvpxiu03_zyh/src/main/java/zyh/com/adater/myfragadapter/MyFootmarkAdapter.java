package zyh.com.adater.myfragadapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.facebook.drawee.view.SimpleDraweeView;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import zyh.com.activity.R;
import zyh.com.bean.myfragbean.MyFootmarkBean;
import zyh.com.util.DateUtils;

public class MyFootmarkAdapter extends RecyclerView.Adapter<MyFootmarkAdapter.MyLoadler> {

    private List<MyFootmarkBean> list = new ArrayList<>();
    private Context context;

    public MyFootmarkAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public MyFootmarkAdapter.MyLoadler onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_my_footmark_rlist_item, viewGroup, false);
        MyLoadler myLoadler = new MyLoadler(view);

        return myLoadler;
    }

    @Override
    public void onBindViewHolder(@NonNull MyFootmarkAdapter.MyLoadler myLoadler, int i) {

        MyFootmarkBean footmarkBean = list.get(i);

        Glide.with(context).load(footmarkBean.getMasterPic()).into(myLoadler.image);
        myLoadler.textCount.setText(footmarkBean.getCommodityName()+"");
        myLoadler.textPrice.setText("￥"+footmarkBean.getPrice());
        myLoadler.textNum.setText("已经浏览:"+footmarkBean.getBrowseNum());

        //时间格式设置
        try {
            myLoadler.textTime.setText(DateUtils.dateFormat(new Date(footmarkBean.getBrowseTime()),DateUtils.MINUTE_PATTERN));
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    //添加集合
    public void addAll(List<MyFootmarkBean> beanList){
        if (beanList!=null) {
            list.addAll(beanList);
        }
    }

    //清楚集合
    public void callClear(){
        list.clear();
    }

    /**
     * 内部类
     */
    public class MyLoadler extends RecyclerView.ViewHolder{

        private final TextView textCount;
        private final TextView textPrice;
        private final ImageView image;
        private final TextView textNum;
        private final TextView textTime;

        public MyLoadler(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.simpleDraweeView_footmark);
            textCount = itemView.findViewById(R.id.textAddress_count_myFootmark_rlist);
            textPrice = itemView.findViewById(R.id.textPrice_myFootmark_rlist);
            textNum = itemView.findViewById(R.id.textNum_myFootmark_rlist);
            textTime = itemView.findViewById(R.id.textTime_myFootmark_rlist);

        }
    }
}
