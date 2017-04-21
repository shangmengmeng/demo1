package com.anew.note.network;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by pig on 2017/2/27.
 */

public interface ApiService {

    @FormUrlEncoded
    @POST("now")
    Call<WeatherModel> getWeather(@Field("city") String city, @Field("key") String key);
}
