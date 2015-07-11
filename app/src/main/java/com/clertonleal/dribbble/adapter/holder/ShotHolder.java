package com.clertonleal.dribbble.adapter.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.clertonleal.dribbble.R;
import com.clertonleal.dribbble.entity.Shot;
import com.clertonleal.dribbble.listener.OnShotClickListener;

public class ShotHolder extends RecyclerView.ViewHolder {

    public ImageView dribbbleImage;
    public TextView dribbbleTittle;
    public TextView viewCount;
    public OnShotClickListener onShotClickListener;
    public Shot shot;

    public ShotHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(v -> {
            if (onShotClickListener != null) {
                onShotClickListener.click(shot);
            }
        });
        dribbbleImage = (ImageView) itemView.findViewById(R.id.image_dribble);
        dribbbleTittle = (TextView) itemView.findViewById(R.id.text_tittle_dribble);
        viewCount = (TextView) itemView.findViewById(R.id.text_views_count);
    }

}
