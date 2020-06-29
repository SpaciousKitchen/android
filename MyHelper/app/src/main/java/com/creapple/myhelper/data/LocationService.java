package com.creapple.myhelper.data;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface LocationService {
    @Headers("Authorization:  KakaoAK 1b55a62fe04f8bb0bb1ba6c43c4a92c8")
    @GET("/v2/local/search/address.json?" )
    Call<Location> getLocation(@Query("query") String query );

}
