package zyh.com.adater;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import zyh.com.activity.R;

/**
 * @author dingtao
 * @date 2019/1/3 23:24
 * qq:1940870847
 */
//圈子条目图片加载器
public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.MyHodler> {

    private List<String> mList = new ArrayList<>();

    public void addAll(List<String> list) {
        mList.addAll(list);
    }

    @NonNull
    @Override
    public MyHodler onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(viewGroup.getContext(), R.layout.fragment_circle_image, null);
        return new MyHodler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHodler myHodler, int position) {

        String imageId = mList.get(position);
        myHodler.image.setImageURI(Uri.parse(imageId));

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void clear() {
        mList.clear();
    }

    public class MyHodler extends RecyclerView.ViewHolder {

        private final SimpleDraweeView image;

        public MyHodler(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.circleFrag_image);
        }
    }
}
