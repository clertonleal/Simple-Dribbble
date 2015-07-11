package com.clertonleal.dribbble.network;

import com.clertonleal.dribbble.entity.Page;

import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

public interface DribbbleNetwork {

    @GET("/shots/popular")
    Observable<Page> retrievePopularPage(@Query("page") int page);


    @GET("/shots/debuts")
    Observable<Page> retrieveDebutPage(@Query("page") int page);

}
