package zyh.com.core;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import zyh.com.app.MyAppliction;
import zyh.com.bean.CircleFragBean;
import zyh.com.bean.DescribeBean;
import zyh.com.bean.HomeFragBannerBean;
import zyh.com.bean.SearchGoodsBean;
import zyh.com.bean.ShopCarBean;
import zyh.com.bean.homefragbean.HomeBean;
import zyh.com.bean.LoginBean;
import zyh.com.bean.Result;
import zyh.com.bean.myfragbean.MyCircleBean;
import zyh.com.bean.myfragbean.MyDataBean;
import zyh.com.bean.myfragbean.MyFootmarkBean;
import zyh.com.bean.myfragbean.MyShopAddressBean;
import zyh.com.bean.orderfragbean.OrderListBean;

public interface ApiService {

    //登录接口
    @FormUrlEncoded
    @POST("user/v1/login")
    Observable<Result<LoginBean>> loginPost(
            @Field("phone") String phone,
            @Field("pwd") String pwds);

    //注册接口
    @FormUrlEncoded
    @POST("user/v1/register")
    Observable<Result> registerPost(
            @Field("phone") String phone,
            @Field("pwd") String pwds);

    //首页列表信息
    @GET("commodity/v1/commodityList")
    public Observable<Result<HomeBean>> homeList();

    //轮播图
    @GET("commodity/v1/bannerShow")
    public Observable<Result<List<HomeFragBannerBean>>> homeBanners();

    //商品详情
    @GET("commodity/v1/findCommodityDetailsById")
    public Observable<Result<DescribeBean>> shopCount(
            @Header("userId") long user,
            @Header("sessionId") String session,
            @Query("commodityId") int commodityId);

    //首页搜索信息
    @GET("commodity/v1/findCommodityByKeyword")
    public Observable<Result<List<SearchGoodsBean>>> queryShopKey(
            @Query("keyword") String keywords,
            @Query("page") int pages,
            @Query("count") int counts);

    //圈子列表查询
    @GET("circle/v1/findCircleList")
    public Observable<Result<List<CircleFragBean>>> circleList(
            @Header("userId") long userid,
            @Header("sessionId") String sessionid,
            @Query("page") int pages,
            @Query("count") int counts);

    //圈子点赞
    @FormUrlEncoded
    @POST("circle/verify/v1/addCircleGreat")
    public Observable<Result> likeCircle(
            @Header("userId") long userid,
            @Header("sessionId") String sessionid,
            @Field("circleId") int pageid);

    //圈子取消点赞
    @DELETE("circle/verify/v1/cancelCircleGreat")
    public Observable<Result> cancelCircle(
            @Header("userId") long userid,
            @Header("sessionId") String sessionid,
            @Query("circleId") int ids);

    //查询购物车
    @GET("order/verify/v1/findShoppingCart")
    public Observable<Result<List<ShopCarBean>>> queryShopping(
            @Header("userId") long users,
            @Header("sessionId") String sessions);

    //同步购物车数据
    @FormUrlEncoded
    @PUT("order/verify/v1/syncShoppingCart")
    public Observable<Result> myShopCar(
            @Header("userId") long userId,
            @Header("sessionId") String sessionId,
            @Field("data") String datas);

    //我的信息
    @GET("user/verify/v1/getUserById")
    public Observable<Result<MyDataBean>> queryMy(
            @Header("userId") long user,
            @Header("sessionId") String session);

    //查询收货地址
    @GET("user/verify/v1/receiveAddressList")
    public Observable<Result<List<MyShopAddressBean>>> queryAddress(
            @Header("userId") long users,
            @Header("sessionId") String sessions);

    //新增收货地址
    @POST("user/verify/v1/addReceiveAddress")
    Observable<Result> newAddress(
            @Header("userId") long userId,
            @Header("sessionId") String sessionId,
            @QueryMap Map<String, String> parmas);

    /**
     * 我的足迹
     */
    @GET("commodity/verify/v1/browseList")
    Observable<Result<List<MyFootmarkBean>>> footmarkList(
            @Header("userId") long userId,
            @Header("sessionId") String sessionId,
            @Query("page") int page,
            @Query("count") int count);

    //我的圈子
    @GET("circle/verify/v1/findMyCircleById")
    Observable<Result<List<MyCircleBean>>> getMyCircle(
            @Header("userId") long userId,
            @Header("sessionId") String sessionId,
            @Query("page") int parma,
            @Query("count") int count);

    //创建订单
    @POST("order/verify/v1/createOrder")
    Observable<Result> createOrder(
            @Header("userId") long userId,
            @Header("sessionId") String sessionId,
            @QueryMap Map<String, String> parmas);

    //查询订单列表
    @GET("order/verify/v1/findOrderListByStatus")
    Observable<Result<List<OrderListBean>>> queryOrderFrag(
            @Header("userId") long userId,
            @Header("sessionId") String sessionId,
            @Query("status") int status,
            @Query("page") int page,
            @Query("count") int count);

    //删除订单
    @DELETE("order/verify/v1/deleteOrder")
    public Observable<Result> deleteOrder(
            @Header("userId") long userid,
            @Header("sessionId") String sessionid,
            @Query("orderId") String ids);

    //支付
    @FormUrlEncoded
    @POST("order/verify/v1/pay")
    public Observable<Result> orderPay(
            @Header("userId") long userid,
            @Header("sessionId") String sessionid,
            @Field("orderId") String orderid,
            @Field("payType") int payid);

    //确认收货
    @FormUrlEncoded
    @PUT("order/verify/v1/confirmReceipt")
    public Observable<Result> myTake(
            @Header("userId") long userId,
            @Header("sessionId") String sessionId,
            @Field("orderId") String datas);

    //上传图片
    @Multipart
    @POST("user/verify/v1/modifyHeadPic")
    Observable<Result> upLoad(
            @Header("userId")String userid,
            @Header("sessionId")String sessionid ,
            @Part MultipartBody.Part part);
}
