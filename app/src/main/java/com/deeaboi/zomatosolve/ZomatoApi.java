package com.deeaboi.zomatosolve;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface ZomatoApi
{

    String BASE_URL="https://developers.zomato.com";
    @Headers("user-key: 1b3c8b37ea96785391fa55c288ac385c")

//    @GET("/api/v2.1/search?entity_id=29&entity_type=city&q=Waffle%20Bites&start=1&count=5")
//    Call<Feed> getData();

    @GET("/api/v2.1/search")
    Call<Feed> getData(@Query("entity_id") int city_id,
                       @Query("entity_type") String city_type,
                       @Query("q") String res_name,
                       @Query("start") int start,
                       @Query("count") int count
                       );





}
