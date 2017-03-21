/*
 * The MIT License (MIT)
 * Copyright (c) 2016 baoyongzhang <baoyz94@gmail.com>
 */
package com.asc_ii.bangnote.bangnote.action;

import android.net.Uri;

/**
 * Created by baoyongzhang on 2016/10/26.
 */
public class BaiduSearchAction extends SearchAction {

    public static BaiduSearchAction create() {
        return new BaiduSearchAction();
    }

    @Override
    public Uri createSearchUriWithEncodedText(String encodedText) {
        return Uri.parse("https://www.baidu.com/s?wd=" + encodedText);
    }
}
