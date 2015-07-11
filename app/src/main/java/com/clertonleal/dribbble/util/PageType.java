package com.clertonleal.dribbble.util;

import com.clertonleal.dribbble.R;

public enum PageType {

    POPULAR(R.string.populars),
    DEBUT(R.string.debuts);

    private int resource;

    PageType(int resource) {
        this.resource = resource;
    }

    public int getResource() {
        return resource;
    }
}
