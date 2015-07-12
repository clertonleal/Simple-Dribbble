package com.clertonleal.dribbble.service;

import com.clertonleal.dribbble.entity.Page;
import com.clertonleal.dribbble.network.DribbbleNetwork;
import com.clertonleal.dribbble.util.PageType;

import javax.inject.Inject;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class DribbbleService {

    DribbbleNetwork dribbbleNetwork;

    @Inject
    public DribbbleService(DribbbleNetwork dribbbleNetwork) {
        this.dribbbleNetwork = dribbbleNetwork;
    }

    public Observable<Page> retrievePopularPage(int page) {
        return dribbbleNetwork.retrievePopularPage(page).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<Page> retrieveDebutPage(int page) {
        return dribbbleNetwork.retrieveDebutPage(page).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<Page> retrievePageByType(int page, PageType pageType) {
        if (pageType == PageType.POPULAR) {
            return retrievePopularPage(page);
        } else if (pageType == PageType.DEBUT) {
            return retrieveDebutPage(page);
        } else {
            return null;
        }
    }

}
