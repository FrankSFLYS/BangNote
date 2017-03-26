/*
 * The MIT License (MIT)
 * Copyright (c) 2016 baoyongzhang <baoyz94@gmail.com>
 */
package com.asc_ii.bangnote;

import android.content.Context;

import com.asc_ii.bangnote.action.BaiduSearchAction;
import com.asc_ii.bangnote.action.GoogleSearchAction;
import com.asc_ii.bangnote.action.SearchAction;
import com.baoyz.treasure.Treasure;

/**
 * Created by baoyongzhang on 2016/10/26.
 */
public class SearchEngine {

    public static final String BAIDU = "百度";
    public static final String GOOGLE = "Google";

    public static SearchAction getSearchAction(Context context) {
        Config config = Treasure.get(context, Config.class);
        switch (config.getSearchEngine()) {
            case BAIDU:
                return BaiduSearchAction.create();
            case GOOGLE:
                return GoogleSearchAction.create();
        }
        return null;
    }

    public static String[] getSupportSearchEngineList() {
        return new String[]{BAIDU, GOOGLE};
    }
}
