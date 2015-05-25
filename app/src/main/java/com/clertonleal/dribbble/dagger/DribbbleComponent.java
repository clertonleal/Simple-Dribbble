package com.clertonleal.dribbble.dagger;

import com.clertonleal.dribbble.activity.MainActivity;
import com.clertonleal.dribbble.activity.ShotActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = DribbbleModule.class)
public interface DribbbleComponent {

    void inject(MainActivity mainActivity);
    void inject(ShotActivity shotActivity);

}
