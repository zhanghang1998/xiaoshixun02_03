package zyh.com.adater.orderfragadapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import zyh.com.activity.R;
import zyh.com.bean.orderfragbean.OrderListDatabean;

public class OrderFragItemAdapter extends RecyclerView.Adapter<OrderFragItemAdapter.ViewHodler> {

    private List<OrderListDatabean> list = new ArrayList<>();
    private Context context;
    private int pageType;

    public OrderFragItemAdapter(Context context, int pageType) {
        this.context = context;
        this.pageType = pageType;
    }

    public void setList(List<OrderListDatabean> mlist) {
        if (mlist != null) {
            list.addAll(mlist);
        }
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ViewHodler onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        if (pageType==1) {//==付款==
            View payView = LayoutInflater.from(context).inflate(R.layout.fragment_order_fukuan_item, viewGroup, false);
            return new ViewHodler(payView);
        } else if (pageType == 2) {//==收货==
            View payView = LayoutInflater.from(context).inflate(R.layout.fragment_order_shouhuo_item, viewGroup, false);
            return new ViewHodler(payView);
        } else if (pageType == 3) {//==评价==
            View payView = LayoutInflater.from(context).inflate(R.layout.fragment_order_pingjia_item, viewGroup, false);
            return new ViewHodler(payView);
        } else {//==完成==
            View payView = LayoutInflater.from(context).inflate(R.layout.fragment_order_wancheng_item, viewGroup, false);
            return new ViewHodler(payView);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHodler viewHodler, final int i) {

        OrderListDatabean orderListDatabean = list.get(i);

        String[] split = orderListDatabean.getCommodityPic().split("\\,");
        Glide.with(context).load(split[0]).into(viewHodler.imageView);
        viewHodler.text_name.setText(orderListDatabean.getCommodityName());
        viewHodler.text_price.setText("￥" + orderListDatabean.getCommodityPrice());
        if (viewHodler.text_num != null) {
            viewHodler.text_num.setText("* " + orderListDatabean.getCommodityCount() + " *");
        }
        if (viewHodler.button_evaluate != null) {
            viewHodler.button_evaluate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mClickEvaluate != null) {
                        mClickEvaluate.setEvaluat(list,i);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHodler extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView text_name;
        private TextView text_price;
        private TextView text_num;
        private Button button_evaluate;

        public ViewHodler(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.recy_item_bill_image);
            text_name = itemView.findViewById(R.id.recy_item_bill_text_name);
            text_price = itemView.findViewById(R.id.recy_item_bill_text_price);
            text_num = itemView.findViewById(R.id.recy_item_bill_text_num);
            button_evaluate=itemView.findViewById(R.id.recy_item_bill_button_evaluate);
        }
    }

    public ClickEvaluate mClickEvaluate;

    public void setEva(ClickEvaluate mClickEvaluate) {
        this.mClickEvaluate = mClickEvaluate;
    }

    public interface ClickEvaluate {
        void setEvaluat(List<OrderListDatabean> list, int position);
    }
}
