package com.creapple.myhelper.data;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MaskstockService {
    @GET("storesByGeo/json?")
    Call<Result> getResult(@Query("lat") double lat , @Query("lng") double lng, @Query("m") int m);
}
