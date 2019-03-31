package zyh.com.adater;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import zyh.com.activity.R;
import zyh.com.bean.CircleFragBean;
import zyh.com.util.DateUtils;
import zyh.com.util.JudgeUtil;
import zyh.com.util.SpacingItemDecoration;

//圈子列表数据适配器adapter
public class CircleFragAdapter extends RecyclerView.Adapter<CircleFragAdapter.MyHoader> {

    List<CircleFragBean> list = new ArrayList<>();
    private Context context;

    public CircleFragAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public CircleFragAdapter.MyHoader onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_circle_xrecyclerview, viewGroup, false);
        return new MyHoader(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull CircleFragAdapter.MyHoader myHoader, final int i) {

        final CircleFragBean comListBean = list.get(i);
        //用户名字
        myHoader.text_name.setText(comListBean.getNickName());
        //圈子内容
        myHoader.text_count.setText(comListBean.getContent() + "");
        //点赞数量
        myHoader.text_num.setText("" + comListBean.getGreatNum());
        //时间格式设置
        try {
            myHoader.text_time.setText(DateUtils.dateFormat(new Date(comListBean.getCreateTime()),DateUtils.MINUTE_PATTERN));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //头像加载
        myHoader.sDV_image.setImageURI(Uri.parse(comListBean.getHeadPic()));

        //内容图片加载
        if (JudgeUtil.isEmpty(comListBean.getImage())) {
            myHoader.recyclerview_num.setVisibility(View.GONE);
        } else {
            myHoader.recyclerview_num.setVisibility(View.VISIBLE);
            String[] images = comListBean.getImage().split(",");
            int imageCount = images.length;
            int colNum;//列数
            if (imageCount == 1) {
                colNum = 1;
            } else if (imageCount == 2 || imageCount == 4) {
                colNum = 2;
            } else {
                colNum = 3;
            }
            myHoader.imageAdapter.clear();//清空
            myHoader.imageAdapter.addAll(Arrays.asList(images));
            myHoader.gridLayoutManager.setSpanCount(colNum);//设置列数

            myHoader.imageAdapter.notifyDataSetChanged();
        }

        //点赞按钮颜色更变
        if (comListBean.getWhetherGreat() == 1){
            myHoader.image_like.setImageResource(R.mipmap.common_btn_prise_s);
        }else{
            myHoader.image_like.setImageResource(R.mipmap.common_btn_prise_n);
        }

        //点赞按钮设置点击按钮
        myHoader.image_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onIitmClick!=null){
                    onIitmClick.initComeID(comListBean.getId(),comListBean.getWhetherGreat(),i);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    //点赞改变
    public void getlike(int position){
        list.get(position).setWhetherGreat(1);
        list.get(position).setGreatNum(list.get(position).getGreatNum()+1);
        notifyDataSetChanged();
    }
    //取消点赞改变
    public void getcancel(int position){
        list.get(position).setWhetherGreat(2);
        list.get(position).setGreatNum(list.get(position).getGreatNum()-1);
        notifyDataSetChanged();
    }

    public void addAll(List<CircleFragBean> comListBeans) {
        if (comListBeans != null) {
            list.addAll(comListBeans);
        }
    }

    public void clearAll() {
        list.clear();
    }

    public class MyHoader extends RecyclerView.ViewHolder {

        private final SimpleDraweeView sDV_image;
        private final TextView text_count;
        private final TextView text_num;
        private final TextView text_name;
        private final TextView text_time;
        private final RecyclerView recyclerview_num;
        private final ImageView image_like;
        private final ImageAdapter imageAdapter;
        private final GridLayoutManager gridLayoutManager;


        public MyHoader(@NonNull View itemView) {
            super(itemView);

            sDV_image = itemView.findViewById(R.id.item_circle_image_header);
            text_count = itemView.findViewById(R.id.item_circle_text_content);
            text_name = itemView.findViewById(R.id.item_circle_text_name);
            text_time = itemView.findViewById(R.id.item_circle_text_time);
            text_num = itemView.findViewById(R.id.item_circle_text_num);//
            recyclerview_num = itemView.findViewById(R.id.item_circle_image_content);
            image_like = itemView.findViewById(R.id.item_circle_image_like);

            imageAdapter = new ImageAdapter();
            int space = context.getResources().getDimensionPixelSize(R.dimen.dp_10);//图片间距
            gridLayoutManager = new GridLayoutManager(context,3);
            recyclerview_num.addItemDecoration(new SpacingItemDecoration(space));
            recyclerview_num.setLayoutManager(gridLayoutManager);
            recyclerview_num.setAdapter(imageAdapter);

        }
    }

    private OnIitmClick onIitmClick;

    public interface OnIitmClick {
        void initComeID(int id,int great,int position);
    }

    public void setOnIitmClick(OnIitmClick onIitmClick) {
        this.onIitmClick = onIitmClick;
    }
}
