package com.clertonleal.dribbble.activity;


import android.os.Bundle;
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

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_shot_view);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Shot shot = (Shot) getBundle().getSerializable(BundleKeys.SHOT);
        imageView.setDisplayType(ImageViewTouchBase.DisplayType.FIT_TO_SCREEN);
        DribbblePicasso.with(this, shot.getImageUrl()).into(imageView);
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
