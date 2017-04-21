package com.anew.note.network;

import android.util.Log;

import com.anew.note.app.MyApplycation;
import com.anew.note.network.Constants;
import com.zhy.http.okhttp.cookie.CookieJarImpl;
import com.zhy.http.okhttp.cookie.store.PersistentCookieStore;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by pig on 2017/2/27.
 */

public class RetrofitManager {
    private OkHttpClient okHttpClient;
    //长缓存有效期为1天
    public static final int CACHE_STALE_LONG = 60 * 60 * 24;
    private ApiService apiService;
    private static RetrofitManager manager;

    public RetrofitManager() {
        //直接调用
        okHttpClient = initOkHttpClient();
        Retrofit  retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASEURL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = retrofit.create(ApiService.class);
    }
    public static RetrofitManager getInstance(){
        if (manager == null){
            manager = new RetrofitManager();
        }
        return manager;
    }

    public static ApiService build(){
        return getInstance().apiService;
    }

    private OkHttpClient initOkHttpClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.e("---------网络内容：",message);
            }
        });
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        CookieJarImpl cookie = new CookieJarImpl(new PersistentCookieStore(MyApplycation.getContext()));
        File file = new File(MyApplycation.getInstance().getCacheDir(),"缓存目录");
        Cache cache = new Cache(file,1024*1024*20);
        if (okHttpClient == null){
            okHttpClient = new OkHttpClient.Builder()
                    .cache(cache)
                    .addInterceptor(interceptor)
                    .cookieJar(cookie)
                    .retryOnConnectionFailure(true)
                    .writeTimeout(60, TimeUnit.SECONDS)
                    .readTimeout(60, TimeUnit.SECONDS)
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .build();

        }
        return okHttpClient;
    }
}
