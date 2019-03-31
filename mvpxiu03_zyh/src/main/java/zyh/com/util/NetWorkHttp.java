package zyh.com.util;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

//网络工具类
public class NetWorkHttp {

    private String USERWIKI = "http://172.17.8.100/small/";
    private Retrofit retrofit;
    private static NetWorkHttp netWorkHttp;

    private NetWorkHttp() {
        init();
    }

    public static NetWorkHttp instance(){

        if (netWorkHttp==null) {
            synchronized (NetWorkHttp.class){
                if (netWorkHttp==null) {
                    netWorkHttp = new NetWorkHttp();
                }
            }
        }

        return netWorkHttp;
    }

    public void init(){

        //打印log日志
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .build();

        retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(USERWIKI)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

    }

    public <T> T create(Class<T> service){
        return retrofit.create(service);
    }
}
