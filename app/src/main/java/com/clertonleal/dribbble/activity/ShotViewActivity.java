package com.clertonleal.dribbble.activity;


import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.clertonleal.dribbble.R;
import com.clertonleal.dribbble.entity.Shot;
import com.clertonleal.dribbble.util.BundleKeys;
import com.clertonleal.dribbble.util.DribbblePicasso;

import butterknife.InjectView;
import it.sephiroth.android.library.imagezoom.ImageViewTouch;
import it.sephiroth.android.library.imagezoom.ImageViewTouchBase;

public class ShotViewActivity extends BaseActivity {

    @InjectView(R.id.image_shot_zoom)
    ImageViewTouch imageView;

    @InjectView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_shot_view);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Shot shot = (Shot) getBundle().getSerializable(BundleKeys.SHOT);
        configureToolbar(shot);
        imageView.setDisplayType(ImageViewTouchBase.DisplayType.FIT_TO_SCREEN);
        DribbblePicasso.with(this, shot.getImageUrl()).into(imageView);
    }

    private void configureToolbar(Shot shot) {
        setSupportActionBar(toolbar);
        setTitle(shot.getTitle());
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void injectMembers() {}
}
