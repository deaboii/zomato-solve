package com.deeaboi.zomatosolve;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ZomatoCityID
{
    String BASE_URL="https://developers.zomato.com";

    @Headers("user-key: 1b3c8b37ea96785391fa55c288ac385c")

    //@GET("/api/v2.1/cities?lat=20.2843611100&lon=85.7695611100")
    //Call<CityFeed>getCity();

    @GET("/api/v2.1/cities")
    Call<CityFeed>getCity(@Query("lat") double lat,@Query("lon") double lon);

}
