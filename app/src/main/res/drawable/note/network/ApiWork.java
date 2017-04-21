package com.anew.note.network;

import android.content.Context;
import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by pig on 2017/2/20.
 */

public class ApiWork {
    public  Context context;

    public static ApiWork instance;
    public ApiWork(Context context){
        this.context = context;
    }
    public static ApiWork getInstance(Context context){
        if (instance == null){
            instance = new ApiWork(context);
        }
        return instance;
    }
    public void startRequest(final Call call ,final Netlistioner listioner){
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                Log.i("netWork", "success code = 200");
                successReturn(call,response,listioner,this);
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Log.i("netWork", "fail code != 200");

            }
        });
    }

    private void successReturn(Call call, Response response, Netlistioner listner, final Callback callback) {
        if (response.isSuccessful()){
            listner.onSuccess(response.body());
        }
        else {
            String errMsg = "";
            try {
                errMsg = response.errorBody().string();
            } catch (Exception e) {
                e.printStackTrace();
            }
            Log.e("========网络错误","ApiError=" + errMsg);
            listner.onFailure(errMsg);
        }



    }

    public void getWeather(String city ,String key , Netlistioner netlistioner){
        Call call = RetrofitManager.build().getWeather(city,key);
        startRequest(call,netlistioner);
    }

}
