package com.clertonleal.dribbble.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.clertonleal.dribbble.R;
import com.clertonleal.dribbble.adapter.ShotAdapter;
import com.clertonleal.dribbble.network.DribbbleNetwork;
import com.clertonleal.dribbble.service.ConnectionService;
import com.clertonleal.dribbble.util.BundleKeys;

import javax.inject.Inject;

import butterknife.InjectView;
import rx.android.schedulers.AndroidSchedulers;

public class MainActivity extends BaseActivity {

    @InjectView(R.id.list)
    RecyclerView recyclerView;

    @InjectView(R.id.toolbar)
    Toolbar toolbar;

    @InjectView(R.id.swipe_container)
    SwipeRefreshLayout refreshLayout;

    @InjectView(R.id.layout_empty_view)
    LinearLayout emptyView;

    @InjectView(R.id.image_refresh)
    ImageView imageRefresh;

    @Inject
    DribbbleNetwork dribbbleNetwork;

    @Inject
    ConnectionService connectionService;

    @Inject
    ShotAdapter shotAdapter;

    LinearLayoutManager linearLayoutManager;

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        configureRecycleView();
        configureToolbar();
        setListeners();
        if (connectionService.hasConnection()) {
            showProgressDialog(R.string.loading_shots);
            loadNewPage(1);
        } else {
            showEmptyView(true);
        }
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

    private void configureToolbar() {
        toolbar.inflateMenu(R.menu.home_menu);
        toolbar.setTitle(R.string.app_name);
    }

    private void setListeners() {
        shotAdapter.setPageLoadListener(this::loadNewPage);
        refreshLayout.setOnRefreshListener(this::resetShots);
        toolbar.setOnMenuItemClickListener(this::onOptionsItemSelected);
        imageRefresh.setOnClickListener(v -> resetShots());

        shotAdapter.setOnShotClickListener(shot -> {
            final Intent intent = new Intent(this, ShotActivity.class);
            intent.putExtra(BundleKeys.SHOT, shot);
            startActivity(intent);
        });
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                refreshLayout.setEnabled(linearLayoutManager.findFirstCompletelyVisibleItemPosition() == 0);
            }
        });
    }

    private void loadNewPage(int pageNumber) {
        compositeSubscription.add(dribbbleNetwork.retrievePage(pageNumber).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(page -> {
                    showEmptyView(false);
                    shotAdapter.addPageShots(page);
                    cancelProgressDialog();
                    if (refreshLayout.isEnabled()) {
                        refreshLayout.setRefreshing(false);
                    }
                }, throwable -> {
                    log(throwable);
                    cancelProgressDialog();
                }));
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_refresh) {
            resetShots();
        }
        return super.onOptionsItemSelected(item);
    }

    private void resetShots() {
        showProgressDialog(R.string.loading_shots);
        shotAdapter.cleanShots();
        loadNewPage(1);
    }

    private void configureRecycleView() {
        recyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(shotAdapter);
    }

    @Override
    protected void injectMembers() {
        dribbleComponent().inject(this);
    }
}
