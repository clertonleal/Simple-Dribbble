package com.clertonleal.dribbble.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.clertonleal.dribbble.R;
import com.clertonleal.dribbble.entity.Shot;
import com.clertonleal.dribbble.util.BundleKeys;
import com.clertonleal.dribbble.util.DribbblePicasso;

import butterknife.InjectView;

public class ShotActivity extends BaseActivity {

    @InjectView(R.id.image_dribble)
    ImageView dribbbleImage;

    @InjectView(R.id.text_tittle_dribble)
    TextView dribbbleTittle;

    @InjectView(R.id.text_views_count)
    TextView textCount;

    @InjectView(R.id.toolbar)
    Toolbar toolbar;

    @InjectView(R.id.image_avatar)
    ImageView imageAuthorAvatar;

    @InjectView(R.id.text_author_name)
    TextView textAuthorName;

    @InjectView(R.id.text_description)
    TextView textDescription;

    Shot shot;

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_shot);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        shot = (Shot) getBundle().getSerializable(BundleKeys.SHOT);
        showShot();
        configureToolbar(shot);
        dribbbleImage.setOnClickListener(v -> showZoomImage());
    }

    private void showZoomImage() {
        final Intent intent = new Intent(this, ShotViewActivity.class);
        intent.putExtra(BundleKeys.SHOT, shot);
        startActivity(intent);
    }

    private void showShot() {
        DribbblePicasso.with(this, shot.getImageUrl()).into(dribbbleImage);
        dribbbleTittle.setText(shot.getTitle());
        textCount.setText(String.valueOf(shot.getViewsCount()));
        DribbblePicasso.with(this, shot.getPlayer().getAvatarUrl()).into(imageAuthorAvatar);
        textAuthorName.setText(shot.getPlayer().getName());

        if (shot.getDescription() != null) {
            textDescription.setText(Html.fromHtml(shot.getDescription()));
            textDescription.setMovementMethod(LinkMovementMethod.getInstance());
        }
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
    protected void injectMembers() {
        dribbleComponent().inject(this);
    }
}
