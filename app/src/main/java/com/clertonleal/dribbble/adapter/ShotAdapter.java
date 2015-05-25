package com.clertonleal.dribbble.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.clertonleal.dribbble.R;
import com.clertonleal.dribbble.adapter.holder.ShotHolder;
import com.clertonleal.dribbble.entity.Page;
import com.clertonleal.dribbble.entity.Shot;
import com.clertonleal.dribbble.listener.OnPageLoadListener;
import com.clertonleal.dribbble.listener.OnShotClickListener;
import com.clertonleal.dribbble.util.DribbblePicasso;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class ShotAdapter extends android.support.v7.widget.RecyclerView.Adapter<ShotHolder> {

    @Inject
    Resources resources;

    @Inject
    Context context;

    @Inject
    LayoutInflater layoutInflater;

    private final List<Shot> shots = new ArrayList<>();
    private Page lastPage;
    private OnPageLoadListener pageLoadListener;
    private OnShotClickListener onShotClickListener;

    @Inject
    public ShotAdapter() {}

    @Override
    public ShotHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ShotHolder(layoutInflater.inflate(R.layout.card_dribble, parent, false));
    }

    @Override
    public void onBindViewHolder(ShotHolder holder, int position) {
        if (position < shots.size()) {
            Shot shot = shots.get(position);
            holder.onShotClickListener = onShotClickListener;
            holder.shot = shot;
            DribbblePicasso.with(context, shot.getImage400Url()).into(holder.dribbbleImage);
            holder.dribbbleTittle.setText(shot.getTitle());
            holder.viewCount.setText(shot.getViewsCount().toString());
            holder.layoutProgress.setVisibility(View.GONE);
        } else {
            holder.onShotClickListener = null;
            holder.dribbbleImage.setImageResource(R.drawable.dribbble_loading);
            holder.dribbbleTittle.setText(resources.getString(R.string.loading));
            holder.viewCount.setText("0");
            holder.layoutProgress.setVisibility(View.VISIBLE);
            if (pageLoadListener != null && lastPage != null) {
                pageLoadListener.loadPage(Integer.valueOf(lastPage.getPage()) + 1);
            }
        }
    }

    @Override
    public int getItemCount() {
        if (shots.isEmpty()) {
            return 0;
        } else {
            return shots.size() + 1;
        }
    }

    public void addPageShots(Page newPage) {
        this.shots.addAll(newPage.getShots());
        this.lastPage = newPage;
        notifyDataSetChanged();
    }

    public void setPageLoadListener(OnPageLoadListener pageLoadListener) {
        this.pageLoadListener = pageLoadListener;
    }

    public void cleanShots() {
        this.lastPage = null;
        this.shots.clear();
    }

    public void setOnShotClickListener(OnShotClickListener onShotClickListener) {
        this.onShotClickListener = onShotClickListener;
    }
}
