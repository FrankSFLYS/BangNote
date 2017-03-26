/*
 * The MIT License (MIT)
 * Copyright (c) 2016 baoyongzhang <baoyz94@gmail.com>
 */
package com.asc_ii.bangnote.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.asc_ii.bangnote.Config;
import com.asc_ii.bangnote.SearchEngine;
import com.asc_ii.bangnote.SegmentEngine;
import com.asc_ii.bangnote.Config;
import com.asc_ii.bangnote.R;
import com.asc_ii.bangnote.action.CopyAction;
import com.asc_ii.bangnote.bigbang.BigBang;
import com.asc_ii.bangnote.service.BigBangService;
import com.asc_ii.bangnote.service.ListenClipboardService;
import com.asc_ii.bangnote.xposed.XposedEnable;
import com.asc_ii.bangnote.xposed.setting.XposedAppManagerActivity;
import com.baoyz.treasure.Treasure;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by baoyongzhang on 2016/10/26.
 */
public class BigBangSettingsActivity extends AppCompatActivity {

    private TextView mSearchEngineText;
    private TextView mSegmentEngineText;
    private SwitchCompat mAutoCopySwitch;
    private SwitchCompat mListenClipboardSwitch;
    private Config mConfig;
    private TextView mButton;
    private TextView mweChatSummaryOfDescription;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_bigbang_settings);

        // 默认分词引擎
        findViewById(R.id.segment_engine).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(BigBangSettingsActivity.this).setItems(SegmentEngine.getSupportSegmentEngineNameList(), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mConfig.setSegmentEngine(SegmentEngine.getSupportSegmentEngineList()[which]);
                        SegmentEngine.setup(getApplicationContext());
                        updateUI();
                        if (SegmentEngine.TYPE_THIRD.equals(SegmentEngine.getSegmentParserType(getApplicationContext()))) {
                            new AlertDialog.Builder(BigBangSettingsActivity.this).setMessage("本地分词第一次使用的会比较慢，需" +
                                    "要将字典加载到内存，会额外占用一部分内存空间。")
                                    .setPositiveButton("确定", null).show();
                        }
                    }
                }).show();
            }
        });

        mweChatSummaryOfDescription =(TextView)findViewById(R.id.des);

        mweChatSummaryOfDescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),R.string.bigbang_settings_wechat_description, Toast.LENGTH_LONG).show();
            }
        });

        mSegmentEngineText = (TextView) findViewById(R.id.segment_engine_text);

        // 默认搜索引擎
        findViewById(R.id.search_engine).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(BigBangSettingsActivity.this).setItems(SearchEngine.getSupportSearchEngineList(), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mConfig.setSearchEngine(SearchEngine.getSupportSearchEngineList()[which]);
                        BigBang.registerAction(BigBang.ACTION_SEARCH, SearchEngine.getSearchAction(getApplicationContext()));
                        updateUI();
                    }
                }).show();
            }
        });

        mSearchEngineText = (TextView) findViewById(R.id.search_engine_text);

        // 返回自动复制
        mAutoCopySwitch = (SwitchCompat) findViewById(R.id.auto_copy_switch);
        mAutoCopySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mConfig.setAutoCopy(isChecked);
                BigBang.registerAction(BigBang.ACTION_BACK, mConfig.isAutoCopy() ? CopyAction.create() : null);
                updateUI();
            }
        });

        // 返回自动复制
        mListenClipboardSwitch = (SwitchCompat) findViewById(R.id.listen_clipboard_switch);
        mListenClipboardSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mConfig.setListenClipboard(isChecked);
                if (mConfig.isListenClipboard()) {
                    ListenClipboardService.start(getApplicationContext());
                } else {
                    ListenClipboardService.stop(getApplicationContext());
                }
                updateUI();
            }
        });

        findViewById(R.id.bigbang_style).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BigBangSettingsActivity.this, StyleActivity.class));
            }
        });
        findViewById(R.id.xposed_app_manger).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BigBangSettingsActivity.this, XposedAppManagerActivity.class));
            }
        });

        if (XposedEnable.isEnable()) {
            findViewById(R.id.xposed_app_manger).setVisibility(View.VISIBLE);
        }
        mConfig = Treasure.get(this, Config.class);
        updateUI();

        //TODO:Set new buttons' click listener
        final TextView textView = (TextView) findViewById(R.id.textView);

        ClipboardManager clipboardService = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData primaryClip = clipboardService.getPrimaryClip();
        if (primaryClip != null && primaryClip.getItemCount() > 0) {
            textView.setText(textView.getText());
        }

        textView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("bigBang://?extra_text="
                            + URLEncoder.encode(textView.getText().toString(), "utf-8"))));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                return true;
            }
        });

        mButton = (TextView) findViewById(R.id.weChat_click);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (BigBangService.isAccessibilitySettingsOn(getApplicationContext())) {
            mButton.setEnabled(false);
            mButton.setText("已开启微信支持");
        } else {
            mButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openAccessibilitySettings();
                }
            });
        }
    }

    private void openAccessibilitySettings() {
        new AlertDialog.Builder(this)
                .setMessage("不需要 root，需要在系统设置中开启权限，前往设置页面，找到 `BigBang`，然后开启。")
                .setPositiveButton("前往设置", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startActivity(new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS));
                    }
                }).show();
    }

    private void updateUI() {
        mSearchEngineText.setText(mConfig.getSearchEngine());
        mSegmentEngineText.setText(SegmentEngine.getSegmentEngineName(getApplicationContext()));
        mAutoCopySwitch.setChecked(mConfig.isAutoCopy());
        mListenClipboardSwitch.setChecked(mConfig.isListenClipboard());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
