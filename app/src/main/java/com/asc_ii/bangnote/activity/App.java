/*
 * The MIT License (MIT)
 * Copyright (c) 2016 baoyongzhang <baoyz94@gmail.com>
 */
package com.asc_ii.bangnote.activity;

import android.app.Application;
import android.content.SharedPreferences;

import com.asc_ii.bangnote.Config;
import com.asc_ii.bangnote.SearchEngine;
import com.asc_ii.bangnote.SegmentEngine;
import com.asc_ii.bangnote.Config;
import com.asc_ii.bangnote.action.CopyAction;
import com.asc_ii.bangnote.action.SaveAction;
import com.asc_ii.bangnote.action.ShareAction;
import com.asc_ii.bangnote.bigbang.BigBang;
import com.asc_ii.bangnote.service.ListenClipboardService;
import com.baoyz.treasure.Treasure;

/**
 * Created by baoyongzhang on 2016/10/26.
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        BigBang.registerAction(BigBang.ACTION_SEARCH, SearchEngine.getSearchAction(this));
        BigBang.registerAction(BigBang.ACTION_COPY, CopyAction.create());
        BigBang.registerAction(BigBang.ACTION_SHARE, ShareAction.create());
        BigBang.registerAction(BigBang.ACTION_SAVE, SaveAction.create());
        Config config = Treasure.get(this, Config.class);
        BigBang.registerAction(BigBang.ACTION_BACK, config.isAutoCopy() ? CopyAction.create() : null);
        SharedPreferences preferences = getSharedPreferences(NoteSettingsActivity.PREF_NOTE, MODE_PRIVATE);
        Boolean isAutoSave = preferences.getBoolean(NoteSettingsActivity.AUTO_SAVE, false);
        BigBang.registerAction(BigBang.ACTION_SAVE, isAutoSave ? SaveAction.create() : null);

        SegmentEngine.setup(this);

        BigBang.setStyle(config.getItemSpace(), config.getLineSpace(), config.getItemTextSize());

        if (config.isListenClipboard()) {
            ListenClipboardService.start(this);
        }
    }
}
