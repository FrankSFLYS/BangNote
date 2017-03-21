package com.asc_ii.bangnote.bangnote;/*
 * The MIT License (MIT)
 * Copyright (c) 2016 baoyongzhang <baoyz94@gmail.com>
 */

import android.app.Application;
import android.content.Context;

import com.asc_ii.bangnote.bangnote.segment.Callback;
import com.asc_ii.bangnote.bangnote.segment.SegmentException;
import com.asc_ii.bangnote.bangnote.segment.SimpleParser;
import com.asc_ii.bangnote.bangnote.segment_ik.IKSegmenterParser;


/**
 * Created by baoyongzhang on 2016/10/28.
 */
public class ThirdPartyParser extends SimpleParser {

    private IKSegmenterParser mParser;

    public ThirdPartyParser(Context context) {
        mParser = new IKSegmenterParser((Application) context.getApplicationContext());
    }

    @Override
    public String[] parseSync(String text) throws SegmentException {
        return mParser.parseSync(text);
    }

    @Override
    public void parse(String text, Callback<String[]> callback) {
        mParser.parse(text, callback);
    }
}
