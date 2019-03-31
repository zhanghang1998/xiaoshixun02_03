package zyh.com.adater;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import zyh.com.activity.R;
import zyh.com.bean.ShopCarBean;

public class CreatOrderAdapter extends RecyclerView.Adapter<CreatOrderAdapter.MyLoadler> {

    private List<ShopCarBean> list = new ArrayList<>();
    private Context context;

    public CreatOrderAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public CreatOrderAdapter.MyLoadler onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_gobuy_recyclerview_createorder, viewGroup, false);
        MyLoadler myLoadler = new MyLoadler(view);

        return myLoadler;
    }

    @Override
    public void onBindViewHolder(@NonNull final CreatOrderAdapter.MyLoadler myLoadler, final int i) {

        final ShopCarBean circleBean = list.get(i);

        myLoadler.textTitle.setText(circleBean.getCommodityName() + "");
        myLoadler.textPrice.setText(circleBean.getPrice() + "");
        myLoadler.simple.setImageURI(Uri.parse(circleBean.getPic()+""));
        //数量
        myLoadler.textNum.setText("*"+circleBean.getCount()+"*");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    //添加集合
    public void addAll(List<ShopCarBean> beanList) {
        if (beanList != null) {
            list.addAll(beanList);
        }
    }

    //清空集合
    public void ClearAll() {
        list.clear();
    }

    /**
     * 内部类
     */
    public class MyLoadler extends RecyclerView.ViewHolder {

        private final SimpleDraweeView simple;
        private final TextView textPrice;
        private final TextView textTitle;
        private final TextView textNum;

        public MyLoadler(@NonNull View itemView) {
            super(itemView);

            simple = itemView.findViewById(R.id.shopCar_item_img);
            textPrice = itemView.findViewById(R.id.shopCar_item_price);
            textTitle = itemView.findViewById(R.id.shopCar_item_title);
            textNum = itemView.findViewById(R.id.shopCar_item_add_sub_layout);

        }
    }
}
