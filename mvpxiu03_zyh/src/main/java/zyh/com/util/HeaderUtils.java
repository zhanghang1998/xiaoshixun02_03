package zyh.com.util;


import android.content.Context;
import android.content.SharedPreferences;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import zyh.com.app.MyAppliction;


public class HeaderUtils  implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {

        //SharedPreferences share = MyAppliction.getShare();
        //String userId = share.getString("userId", "");
        //long usersNum = Integer.valueOf(userId);
        //String sessionId = share.getString("sessionId", "");

        Request request = chain.request();
        Request build = request.newBuilder()
                .addHeader("userId","")
                .addHeader("sessionId","")
                .build();
        Response proceed = chain.proceed(build);
        return proceed;
    }
}
