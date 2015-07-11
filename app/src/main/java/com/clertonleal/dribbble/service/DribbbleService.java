package com.clertonleal.dribbble.service;

import com.clertonleal.dribbble.entity.Page;
import com.clertonleal.dribbble.network.DribbbleNetwork;

import javax.inject.Inject;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class DribbbleService {

    @Inject
    public DribbbleService() {}

    @Inject
    DribbbleNetwork dribbbleNetwork;

    public Observable<Page> retrievePage(int page) {
        return dribbbleNetwork.retrievePage(page).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

}
