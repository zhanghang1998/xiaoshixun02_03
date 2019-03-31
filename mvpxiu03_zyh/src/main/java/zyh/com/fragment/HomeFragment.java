package zyh.com.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.stx.xhb.xbanner.XBanner;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import zyh.com.activity.DescribeActivity;
import zyh.com.activity.R;
import zyh.com.activity.SearchGoodsActivity;
import zyh.com.adater.HomeFragAdapter;
import zyh.com.base.BaseContract;
import zyh.com.bean.HomeFragBannerBean;
import zyh.com.bean.Result;
import zyh.com.bean.homefragbean.ComListBean;
import zyh.com.bean.homefragbean.HomeBean;
import zyh.com.presenter.homefragpresenter.HomeBannerPresenter;
import zyh.com.presenter.homefragpresenter.HomePresenter;
import zyh.com.util.SpacingItemDecoration;

//首页商品展示
public class HomeFragment extends Fragment implements BaseContract.BaseView<Result<HomeBean>>{

    @BindView(R.id.homeFrag_cate_list)
    ImageView homeFragCateList;
    @BindView(R.id.homeFrag_search)
    ImageView homeFragSearch;
    @BindView(R.id.homeFrag_title_bar)
    RelativeLayout homeTitleBar;
    @BindView(R.id.homeFrag_MZBannerView)
    XBanner  homeMZBanner;
    @BindView(R.id.homeFrag_rexiao_list)
    RecyclerView homeFragRexiaoList;
    @BindView(R.id.homeFrag_moli_list)
    RecyclerView homeFragMoliList;
    @BindView(R.id.homeFrag_pinzhi_list)
    RecyclerView homeFragPinzhiList;
    Unbinder unbinder;
    private HomePresenter homePresenter;
    HomeFragAdapter homeFragAdapter01,homeFragAdapter02,homeFragAdapter03;
    private List<ComListBean> rxxpList;
    private List<ComListBean> mlssList;
    private List<ComListBean> pzshList;
    private ArrayList<String> lists;
    private HomeBannerPresenter homeBannerPresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        unbinder = ButterKnife.bind(this, view);
        //实现p层
        homePresenter = new HomePresenter(this);
        homeBannerPresenter = new HomeBannerPresenter(new homeBanner());
        //布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        homeFragRexiaoList.setLayoutManager(linearLayoutManager);//热销新品
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        homeFragMoliList.setLayoutManager(layoutManager);//魔力时尚
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2, LinearLayoutManager.VERTICAL, false);
        homeFragPinzhiList.setLayoutManager(gridLayoutManager);//品质生活
        //recyclerview条目应对屏幕适配
        int space = getResources().getDimensionPixelSize(R.dimen.dp_10);
        homeFragRexiaoList.addItemDecoration(new SpacingItemDecoration(space));
        homeFragMoliList.addItemDecoration(new SpacingItemDecoration(space));
        homeFragPinzhiList.addItemDecoration(new SpacingItemDecoration(space));
        //获取适配器
        homeFragAdapter01 = new HomeFragAdapter(getContext(),HomeFragAdapter.HOME01);
        homeFragAdapter02 = new HomeFragAdapter(getContext(),HomeFragAdapter.HOME02);
        homeFragAdapter03 = new HomeFragAdapter(getContext(),HomeFragAdapter.HOME03);
        //给RecyclerView设置适配器
        homeFragRexiaoList.setAdapter(homeFragAdapter01);
        homeFragMoliList.setAdapter(homeFragAdapter02);
        homeFragPinzhiList.setAdapter(homeFragAdapter03);
        //首页数据请求
        homePresenter.requestData();
        //轮播图请求
        homeBannerPresenter.requestData();
        //条目点击方法
        initOnClick();
        return view;
    }

    //点击放大镜进入搜索页面
    @OnClick(R.id.homeFrag_search)
    public void clickSearch(){
        Intent intent = new Intent(getActivity(), SearchGoodsActivity.class);
        startActivity(intent);
    }

    //条目点击方法
    public void initOnClick(){

        //热销新品条目点击
        homeFragAdapter01.setOnIitmClick(new HomeFragAdapter.OnIitmClick() {
            @Override
            public void initComeID(int cid) {
                Intent intent = new Intent(getActivity(), DescribeActivity.class);
                Toast.makeText(getActivity(),cid+"",Toast.LENGTH_SHORT).show();
                intent.putExtra("comID",cid);
                startActivity(intent);
            }
        });
        //魔力时尚条目点击
        homeFragAdapter02.setOnIitmClick(new HomeFragAdapter.OnIitmClick() {
            @Override
            public void initComeID(int cid) {
                Toast.makeText(getActivity(),cid+"",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), DescribeActivity.class);
                intent.putExtra("comID",cid);
                startActivity(intent);
            }
        });
        //品质生活条目点击
        homeFragAdapter03.setOnIitmClick(new HomeFragAdapter.OnIitmClick() {
            @Override
            public void initComeID(int cid) {
                Toast.makeText(getActivity(),cid+"",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), DescribeActivity.class);
                intent.putExtra("comID",cid);
                startActivity(intent);
            }
        });

    }

    //轮播图加载图片
    public void onBannerView(){
        lists = new ArrayList<>();
        for (int i = 0; i < bannerBeans.size() ; i++) {
            String imageUrl = bannerBeans.get(i).getImageUrl();
            lists.add(imageUrl);
        }
        homeMZBanner.setData(lists,null);
        homeMZBanner.loadImage(new XBanner.XBannerAdapter() {
            @Override
            public void loadBanner(XBanner banner, Object model, View view, int position) {
                Glide.with(getActivity()).load(lists.get(position)).into((ImageView) view);
            }
        });
    }

    private List<HomeFragBannerBean> bannerBeans;
    //轮播请求的数据
    public class homeBanner implements BaseContract.BaseView<Result<List<HomeFragBannerBean>>>{

        @Override
        public void onCompleted(Result<List<HomeFragBannerBean>> data) {
            if (data.getStatus().equals("0000")){
                bannerBeans = data.getResult();
                onBannerView();
            }
        }

        @Override
        public void onError(Throwable throwable) {
            Log.d("张雨航homeBanner", ">>throwable.getMessage:" + throwable.getMessage() + ";throwable.toString:" + throwable.toString());
        }
    }

    //首页商品请求成功方法
    @Override
    public void onCompleted(Result<HomeBean> data) {

        if (data.getStatus().equals("0000")) {
            HomeBean result = data.getResult();
            //热销新品
            rxxpList = result.getRxxp().getCommodityList();
            homeFragAdapter01.addAll(rxxpList);
            homeFragAdapter01.notifyDataSetChanged();
            //魔力时尚
            mlssList = result.getMlss().getCommodityList();
            homeFragAdapter02.addAll(mlssList);
            homeFragAdapter02.notifyDataSetChanged();
            //品质生活
            pzshList = result.getPzsh().getCommodityList();
            homeFragAdapter03.addAll(pzshList);
            homeFragAdapter03.notifyDataSetChanged();

        }
    }

    @Override
    public void onError(Throwable throwable) {
        Log.d("张雨航homeFrag", ">>throwable.getMessage:" + throwable.getMessage() + ";throwable.toString:" + throwable.toString());
    }

    @Override
    public void onResume() {
        super.onResume();
        homeMZBanner.startAutoPlay();
    }

    @Override
    public void onStop() {
        super.onStop();
        homeMZBanner.stopAutoPlay();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        homePresenter.onBind();
        homeBannerPresenter.onBind();
    }
}
