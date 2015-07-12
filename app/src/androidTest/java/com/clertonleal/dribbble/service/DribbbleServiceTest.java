package com.clertonleal.dribbble.service;

import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.clertonleal.dribbble.network.DribbbleNetwork;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import retrofit.RestAdapter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class DribbbleServiceTest {

    private static final Integer PHOTOS_PER_PAGE = 15;
    private DribbbleNetwork dribbbleNetwork;
    private Integer actualPage = 1;

    @Before
    public void setUp() {
        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint("http://api.dribbble.com/").build();
        dribbbleNetwork = restAdapter.create(DribbbleNetwork.class);
    }

    @Test
    public void retrievePopularShots() {
        DribbbleService dribbbleService = new DribbbleService(dribbbleNetwork);
        dribbbleService.retrieveDebutPage(actualPage).subscribe(page -> {
            assertNotNull(page);
            assertEquals(page.getPage(), actualPage);
            assertEquals(page.getPerPage(), PHOTOS_PER_PAGE);
            assertNotNull(page.getShots());
            assertEquals(page.getShots().size(), PHOTOS_PER_PAGE.intValue());
        });
    }

    @Test
    public void retrieveDebutShots() {
        DribbbleService dribbbleService = new DribbbleService(dribbbleNetwork);
        dribbbleService.retrievePopularPage(actualPage).subscribe(page -> {
            assertNotNull(page);
            assertEquals(page.getPage(), actualPage);
            assertEquals(page.getPerPage(), PHOTOS_PER_PAGE);
            assertNotNull(page.getShots());
            assertEquals(page.getShots().size(), PHOTOS_PER_PAGE.intValue());
        });
    }
}
