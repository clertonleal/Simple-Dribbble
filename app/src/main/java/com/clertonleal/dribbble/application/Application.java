package com.clertonleal.dribbble.application;

import com.clertonleal.dribbble.dagger.DaggerDribbbleComponent;
import com.clertonleal.dribbble.dagger.DribbbleComponent;
import com.clertonleal.dribbble.dagger.DribbbleModule;

public class Application extends android.app.Application {


    private static DribbbleComponent dribbbleComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        dribbbleComponent = DaggerDribbbleComponent.
                builder().
                dribbbleModule(new DribbbleModule(this)).
                build();
    }

    public static DribbbleComponent getDribbbleComponent(){
        return dribbbleComponent;
    }
}
