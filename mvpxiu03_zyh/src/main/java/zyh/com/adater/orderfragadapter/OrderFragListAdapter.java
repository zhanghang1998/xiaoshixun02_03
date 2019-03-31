package zyh.com.adater.orderfragadapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import zyh.com.activity.R;
import zyh.com.bean.orderfragbean.OrderListBean;
import zyh.com.bean.orderfragbean.OrderListDatabean;

//订单适配器
public class OrderFragListAdapter extends RecyclerView.Adapter<OrderFragListAdapter.MyLoadler> {

    private List<OrderListBean> list = new ArrayList<>();
    private Context context;
    public static final int QUANBU = 0;
    public static final int FUKUAN = 1;
    public static final int SHOUHUO = 2;
    public static final int PINGJIA = 3;
    public static final int WANCHENG = 9;
    private int types;
    private int itemTypes;

    public OrderFragListAdapter(Context context, int types) {
        this.context = context;
        this.types = types;
    }

    @NonNull
    @Override
    public OrderFragListAdapter.MyLoadler onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        if (types == FUKUAN) {
            View view = LayoutInflater.from(context).inflate(R.layout.fragment_order_fukuan, viewGroup, false);
            return new MyLoadler(view);
        } else if (types == SHOUHUO) {
            View view = LayoutInflater.from(context).inflate(R.layout.fragment_order_shouhuo, viewGroup, false);
            return new MyLoadler(view);
        } else if (types == PINGJIA) {
            View view = LayoutInflater.from(context).inflate(R.layout.fragment_order_pingjia, viewGroup, false);
            return new MyLoadler(view);
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.fragment_order_wancheng, viewGroup, false);
            return new MyLoadler(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull OrderFragListAdapter.MyLoadler myLoadler, final int i) {

        final OrderListBean orderListBean = list.get(i);

        if (types == FUKUAN) {//==付款==
            myLoadler.pay_text_orderId.setText(orderListBean.getOrderId());
            //设置时间
            String times = new SimpleDateFormat("yyyy-MM-dd").format(
                    new Date(orderListBean.getOrderTime()));
            myLoadler.pay_text_time.setText(times);
            //价格
            myLoadler.pay_text_allprice.setText(orderListBean.getPayAmount() + "");
            //设置数量
            int num = 0;
            for (int y = 0; y < orderListBean.getDetailList().size(); y++) {
                num += orderListBean.getDetailList().get(y).getCommodityCount();
            }
            myLoadler.pay_text_allnum.setText("" + num);
            //删除订单
            myLoadler.pay_button_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mClickDelete != null) {
                        mClickDelete.delete(orderListBean.getOrderId(), i);
                    }
                }
            });
            //支付
            myLoadler.pay_button_go.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mClickGo != null) {
                        mClickGo.go(orderListBean.getOrderId(), orderListBean.getPayAmount() + "");
                    }
                }
            });

            //类型赋值
            itemTypes=1;

        } else if (types == SHOUHUO) {//==收货==

            myLoadler.pay_text_orderId.setText(orderListBean.getOrderId());
            String times = new SimpleDateFormat("yyyy-MM-dd").format(
                    new Date(orderListBean.getOrderTime()));
            myLoadler.pay_text_time.setText(times);
            myLoadler.task_text_CompName.setText(orderListBean.getExpressCompName());
            myLoadler.task_text_expressSn.setText(orderListBean.getExpressSn());
            //设置收货条目接口回调
            myLoadler.task_button_affirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mClickNext!=null){
                        mClickNext.next(orderListBean.getOrderId());
                    }
                }
            });//设置收货条目接口回调

            //类型赋值
            itemTypes=2;
        }else if (types == PINGJIA) {//==评价==

            myLoadler.pay_text_orderId.setText(orderListBean.getOrderId());
            //类型赋值
            itemTypes=3;

        }

        //条目的adapter
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        myLoadler.pay_recy.setLayoutManager(linearLayoutManager);
        OrderFragItemAdapter billItemRecyAdapter = new OrderFragItemAdapter(context,itemTypes);
        billItemRecyAdapter.setList(orderListBean.getDetailList());
        myLoadler.pay_recy.setAdapter(billItemRecyAdapter);
        //去评价接口回调
        billItemRecyAdapter.setEva(new OrderFragItemAdapter.ClickEvaluate() {
            @Override
            public void setEvaluat(List<OrderListDatabean> list, int position) {
                if (mClickEvaluate!=null){
                    mClickEvaluate.setEvaluat(list,position,orderListBean,i);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    //添加集合
    public void addAll(List<OrderListBean> orderListBeans) {
        if (orderListBeans != null) {
            list.addAll(orderListBeans);
        }
    }

    //清除集合
    public void clearAll() {
        list.clear();
    }

    //删除一条集合数据
    public void deleteOrder(int position) {
        list.remove(position);
        notifyDataSetChanged();
    }

    /**
     * 内部类
     */
    public class MyLoadler extends RecyclerView.ViewHolder {

        private final TextView pay_text_orderId;
        private final TextView pay_text_time;
        private final RecyclerView pay_recy;
        private final TextView pay_text_allprice;
        private final TextView pay_text_allnum;
        private final Button pay_button_go;
        private final Button pay_button_cancel;
        //快递公司
        private final TextView task_text_CompName;
        //快递单号
        private final TextView task_text_expressSn;
        //确认收货按钮
        private final Button task_button_affirm;
        //评价页三个小黑点 , 删除
        private ImageView appraise_image_more;

        public MyLoadler(@NonNull View itemView) {
            super(itemView);

            pay_text_orderId = itemView.findViewById(R.id.xrecy_pay_item_text_orderId);//订单号
            pay_text_time = itemView.findViewById(R.id.xrecy_pay_item_text_orderTime);//创建时间
            pay_recy = itemView.findViewById(R.id.xrecy_pay_item_recy);//条目recyclerview
            //支付
            pay_text_allprice = itemView.findViewById(R.id.xrecy_pay_item_text_allprice);
            pay_text_allnum = itemView.findViewById(R.id.xrecy_pay_item_text_allnum);
            pay_button_go = itemView.findViewById(R.id.xrecy_pay_item_button_go);
            pay_button_cancel = itemView.findViewById(R.id.xrecy_pay_item_button_cancel);
            //收货
            task_text_CompName=itemView.findViewById(R.id.xrecy_task_item_text_CompName);
            task_text_expressSn=itemView.findViewById(R.id.xrecy_task_item_text_expressSn);
            task_button_affirm=itemView.findViewById(R.id.xrecy_task_item_button_affirm);

        }
    }


    //支付
    private ClickGo mClickGo;

    public void setGo(ClickGo mClickGo) {
        this.mClickGo = mClickGo;
    }

    public interface ClickGo {
        void go(String orderId, String all_price);
    }

    private ClickDelete mClickDelete;

    public void setDelete(ClickDelete mClickDelete) {
        this.mClickDelete = mClickDelete;
    }

    //删除时候的接口回调
    public interface ClickDelete {
        void delete(String orderId, int position);
    }

    private ClickNext mClickNext;
    public void setNext(ClickNext mClickNext){
        this.mClickNext=mClickNext;
    }
    //确认收货的接口回调
    public interface ClickNext{
        void next(String orderId);
    }

    public ClickEvaluate mClickEvaluate;
    public void setEva(ClickEvaluate mClickEvaluate){
        this.mClickEvaluate=mClickEvaluate;
    }
    //去评价接口回调
    public interface ClickEvaluate{
        void setEvaluat(List<OrderListDatabean> orderListDatabeans, int position, OrderListBean orderListBean, int i);
    }

}
