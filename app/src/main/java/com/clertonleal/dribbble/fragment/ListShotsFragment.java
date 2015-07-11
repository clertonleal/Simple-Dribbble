package com.clertonleal.dribbble.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.clertonleal.dribbble.R;
import com.clertonleal.dribbble.activity.ShotActivity;
import com.clertonleal.dribbble.adapter.ShotAdapter;
import com.clertonleal.dribbble.entity.Shot;
import com.clertonleal.dribbble.service.DribbbleService;
import com.clertonleal.dribbble.util.BundleKeys;
import com.clertonleal.dribbble.util.Dribbble;
import com.clertonleal.dribbble.util.PageType;
import com.malinskiy.superrecyclerview.SuperRecyclerView;

import javax.inject.Inject;

import butterknife.InjectView;

public class ListShotsFragment extends BaseFragment {

    @InjectView(R.id.list)
    SuperRecyclerView recyclerView;

    @Inject
    DribbbleService dribbbleService;

    @Inject
    ShotAdapter shotAdapter;

    LinearLayoutManager linearLayoutManager;

    int actualPage = 1;

    private PageType pageType;

    public static ListShotsFragment newInstance(PageType pageType) {
        ListShotsFragment listShotsFragment = new ListShotsFragment();
        listShotsFragment.setPageType(pageType);
        return listShotsFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.shot_list, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        configureRecycleView();
        setListeners();
        loadInitialPage();
    }

    private void setListeners() {
        recyclerView.setupMoreListener(this::onLoadMore, Dribbble.LOAD_MORE_PHOTOS);
        recyclerView.setRefreshListener(this::loadInitialPage);
        shotAdapter.setOnShotClickListener(this::openPhotoDetail);
    }

    private void openPhotoDetail(Shot shot) {
        final Intent intent = new Intent(getActivity(), ShotActivity.class);
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
        compositeSubscription.add(dribbbleService.retrievePageByType(actualPage, pageType).
                subscribe(page -> {
                    shotAdapter.addPageShots(page);
                    recyclerView.setAdapter(shotAdapter);
                }, this::log));
    }

    private void loadNewPage(int pageNumber) {
        compositeSubscription.add(dribbbleService.retrievePageByType(pageNumber, pageType).
                subscribe(page -> {
                    recyclerView.hideMoreProgress();
                    shotAdapter.addPageShots(page);
                }, this::log));
    }

    private void configureRecycleView() {
        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setRefreshingColorResources(R.color.primary_color, R.color.primary_color,
                R.color.primary_color, R.color.primary_color);
    }

    @Override
    protected void injectMembers() {
        dribbleComponent().inject(this);
    }

    public int getPageTitle() {
        return this.pageType.getResource();
    }

    public void setPageType(PageType pageType) {
        this.pageType = pageType;
    }
}
