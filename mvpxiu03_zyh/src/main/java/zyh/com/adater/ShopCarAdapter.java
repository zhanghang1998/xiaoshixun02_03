package zyh.com.adater;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import zyh.com.activity.R;
import zyh.com.bean.SearchGoodsBean;
import zyh.com.bean.ShopCarBean;
import zyh.com.util.view.MyUtilView;

//购物车列表数据适配器adapter
public class ShopCarAdapter extends RecyclerView.Adapter<ShopCarAdapter.MyHoader> {

    List<ShopCarBean> list = new ArrayList<>();
    private Context context;

    public ShopCarAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ShopCarAdapter.MyHoader onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.fragment_shopcar_xrecyclerview, null);
        return new MyHoader(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShopCarAdapter.MyHoader myHoader, final int i) {

        final ShopCarBean comListBean = list.get(i);

        myHoader.text_count.setText(comListBean.getCommodityName()+ "");
        myHoader.text_price.setText("￥" + comListBean.getPrice());
        //图片加载
        myHoader.sDV_image.setImageURI(Uri.parse(comListBean.getPic()));
        myHoader.checked.setChecked(comListBean.isIscheck());
        //改变复选框按钮
        myHoader.checked.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                comListBean.setIscheck(isChecked);
                if (onIitmClick!=null) {
                    onIitmClick.initComeID(list);
                }
            }
        });
        //加减器赋值
        myHoader.addSub.setCount(comListBean.getCount());
        //加减器设置接口回调
        myHoader.addSub.setAddSubListener(new MyUtilView.AddSubListener() {
            @Override
            public void addSub(int count) {
                if (onIitmClick!=null) {
                    onIitmClick.initComeID(list);
                }
            }
        });
        //删除条目
        myHoader.text_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.remove(i);
                notifyDataSetChanged();
                if (onIitmDelete!=null) {
                    onIitmDelete.initDeleteId(list,i);
                }
            }
        });//删除条目

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addAll(List<ShopCarBean> comListBeans) {
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
        private final TextView text_delete;
        private final TextView text_count;
        private final MyUtilView addSub;
        private final CheckBox checked;

        public MyHoader(@NonNull View itemView) {
            super(itemView);

            sDV_image = itemView.findViewById(R.id.shopCar_item_img);
            text_price = itemView.findViewById(R.id.shopCar_item_price);
            text_count = itemView.findViewById(R.id.shopCar_item_title);
            addSub = itemView.findViewById(R.id.shopCar_item_add_sub_layout);
            checked = itemView.findViewById(R.id.shopCar_item_check_single);
            text_delete = itemView.findViewById(R.id.shopCar_item_text_delete);

        }
    }

    private OnIitmClick onIitmClick;
    public interface OnIitmClick {
        void initComeID(List<ShopCarBean> listes);
    }
    public void setOnIitmClick(OnIitmClick onIitmClick) {
        this.onIitmClick = onIitmClick;
    }

    private OnIitmDelete onIitmDelete;
    public interface OnIitmDelete {
        void initDeleteId(List<ShopCarBean> listes,int cid);
    }
    public void setOnIitmDelete(OnIitmDelete onIitmDelete) {
        this.onIitmDelete = onIitmDelete;
    }
}
