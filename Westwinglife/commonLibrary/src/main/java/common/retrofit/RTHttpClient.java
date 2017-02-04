package common.retrofit;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.OkHttpClient;

import java.util.concurrent.TimeUnit;

import common.retrofit.converter.FallbackGsonConverter;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.client.OkClient;

/**
 * 简化后的 Retrofit HTTP 客户端调用类.
 * Created by beansoft on 15/5/4.
 */
public class  RTHttpClient {
    /**
     * access-token默认值
     */
    public static final String ACCESS_TOKEN = "not-valid";
    public static final String VERSION_CODE = "1";
    public static RestAdapter restAdapter = null;
    public static String access_token,refresh_token;
    public static String baseURL = null;
    public static String mobile;
    public static void initLogin(){
    }
    public static void init(String _baseURL,Context context) {
        SharedPreferences sp = context.getSharedPreferences("userInfo", Context.MODE_PRIVATE);

        access_token = sp.getString("access_token",ACCESS_TOKEN);
        Log.e("access_token ",access_token);
        refresh_token = sp.getString("refresh_token",ACCESS_TOKEN);
        mobile = sp.getString("mobile",ACCESS_TOKEN);
        baseURL = _baseURL;
        RequestInterceptor requestInterceptor = new RequestInterceptor() {
            @Override
            public void intercept(RequestFacade request) {
                request.addHeader("access-token", access_token);
                request.addHeader("api-version",VERSION_CODE);
                request.addHeader("User-Agent", "android-dealer-1.3.4");
                request.addHeader("Cache-Control", "no-cache");
                request.addHeader("Content-Type","application/json;charset=UTF-8");
                request.addHeader("accept-language","zh-CN,zh;q=0.8");
            }
        };
        final OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setReadTimeout(15, TimeUnit.SECONDS);
        okHttpClient.setConnectTimeout(15, TimeUnit.SECONDS);
        restAdapter = new RestAdapter.Builder()
                .setEndpoint(baseURL)
                .setRequestInterceptor(requestInterceptor)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setConverter(new FallbackGsonConverter(new GsonBuilder().create(), "UTF-8"))
                .setClient(new OkClient(okHttpClient))
                .build();
    }

    public static <T> T create(Class<T> service) {
        return restAdapter.create(service);
    }

    public static boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }
}