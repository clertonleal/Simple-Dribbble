package com.clertonleal.dribbble.network;

import com.clertonleal.dribbble.entity.Shot;
import com.clertonleal.dribbble.entity.Page;

import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;
import rx.Observable;

public interface DribbbleNetwork {

    @GET("/shots/popular")
    Observable<Page> retrievePage(@Query("page") int page);


    @GET("/shots/{dribbleId}")
    Observable<Shot> retrieveShotById(@Path("dribbleId") Long dribbleId);

}
