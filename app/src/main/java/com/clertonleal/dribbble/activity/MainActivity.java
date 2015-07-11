package com.clertonleal.dribbble.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.clertonleal.dribbble.R;
import com.clertonleal.dribbble.adapter.ShotAdapter;
import com.clertonleal.dribbble.entity.Shot;
import com.clertonleal.dribbble.service.ConnectionService;
import com.clertonleal.dribbble.service.DribbbleService;
import com.clertonleal.dribbble.util.BundleKeys;
import com.clertonleal.dribbble.util.Dribbble;
import com.malinskiy.superrecyclerview.SuperRecyclerView;

import javax.inject.Inject;

import butterknife.InjectView;

public class MainActivity extends BaseActivity {

    @InjectView(R.id.list)
    SuperRecyclerView recyclerView;

    @InjectView(R.id.toolbar)
    Toolbar toolbar;

    @InjectView(R.id.layout_empty_view)
    LinearLayout emptyView;

    @InjectView(R.id.image_refresh)
    ImageView imageRefresh;

    @Inject
    DribbbleService dribbbleService;

    @Inject
    ConnectionService connectionService;

    @Inject
    ShotAdapter shotAdapter;

    LinearLayoutManager linearLayoutManager;

    int actualPage = 1;

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_main);
    }

    private void configureToolbar() {
        toolbar.inflateMenu(R.menu.home_menu);
        toolbar.setTitle(R.string.app_name);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        configureRecycleView();
        configureToolbar();
        setListeners();
        if (connectionService.hasConnection()) {
            loadInitialPage();
        } else {
            showEmptyView(true);
        }
    }

    private void configureRecycleView() {
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setRefreshingColorResources(R.color.primary_color, R.color.primary_color,
                R.color.primary_color, R.color.primary_color);
    }

    private void setListeners() {
        recyclerView.setupMoreListener(this::onLoadMore, Dribbble.LOAD_MORE_PHOTOS);
        recyclerView.setRefreshListener(this::loadInitialPage);
        toolbar.setOnMenuItemClickListener(this::onOptionsItemSelected);
        shotAdapter.setOnShotClickListener(this::openPhotoDetail);
        imageRefresh.setOnClickListener(v -> {
            showEmptyView(false);
            loadInitialPage();
        });
    }

    private void showEmptyView(boolean show) {
        if (show) {
            emptyView.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            emptyView.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }

    private void openPhotoDetail(Shot shot) {
        final Intent intent = new Intent(this, ShotActivity.class);
        intent.putExtra(BundleKeys.SHOT, shot);
        startActivity(intent);
    }

    private void onLoadMore(int numberOfItems, int numberBeforeMore, int currentItemPos) {
        actualPage++;
        loadNewPage(actualPage);
    }

    private void loadInitialPage() {
        actualPage = 1;
        shotAdapter.cleanShots();
        recyclerView.showProgress();
        compositeSubscription.add(dribbbleService.retrievePage(actualPage).
                subscribe(page -> {
                    shotAdapter.addPageShots(page);
                    recyclerView.setAdapter(shotAdapter);
                }, this::log));
    }

    private void loadNewPage(int pageNumber) {
        compositeSubscription.add(dribbbleService.retrievePage(pageNumber).
                subscribe(page -> {
                    recyclerView.hideMoreProgress();
                    shotAdapter.addPageShots(page);
                }, this::log));
    }
    @Override
    protected void injectMembers() {
        dribbleComponent().inject(this);
    }
}
