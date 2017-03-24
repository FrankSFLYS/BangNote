/*
 * The MIT License (MIT)
 * Copyright (c) 2016 baoyongzhang <baoyz94@gmail.com>
 */
package com.asc_ii.bangnote.bangnote.bigbang;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.StringDef;
import android.widget.Toast;

import com.asc_ii.bangnote.bangnote.Config;
import com.asc_ii.bangnote.bangnote.ThirdPartyParser;
import com.asc_ii.bangnote.bangnote.action.Action;
import com.asc_ii.bangnote.bangnote.segment.CharacterParser;
import com.asc_ii.bangnote.bangnote.segment.NetworkParser;
import com.asc_ii.bangnote.bangnote.segment.SimpleParser;
import com.baoyz.treasure.Treasure;

import java.lang.annotation.Retention;
import java.util.HashMap;
import java.util.Map;

import static com.asc_ii.bangnote.bangnote.SegmentEngine.TYPE_CHARACTER;
import static com.asc_ii.bangnote.bangnote.SegmentEngine.TYPE_NETWORK;
import static com.asc_ii.bangnote.bangnote.SegmentEngine.TYPE_THIRD;
import static java.lang.annotation.RetentionPolicy.SOURCE;


/**
 * Created by baoyongzhang on 2016/10/26.
 */
public class BigBang {

    public static final String ACTION_SEARCH = "search";
    public static final String ACTION_SHARE = "share";
    public static final String ACTION_COPY = "copy";
    public static final String ACTION_BACK = "back";
    public static final String ACTION_SAVE = "save";
    private static SimpleParser sParser;
    private static int sItemSpace;
    private static int sLineSpace;
    private static int sItemTextSize;

    public static void setStyle(int itemSpace, int lineSpace, int itemTextSize) {
        sItemSpace = itemSpace;
        sLineSpace = lineSpace;
        sItemTextSize = itemTextSize;
    }

    @StringDef({ACTION_SEARCH, ACTION_SHARE, ACTION_COPY, ACTION_BACK, ACTION_SAVE})
    @Retention(SOURCE)
    public @interface ActionType {

    }

    private static Map<String, Action> mActionMap = new HashMap<>();

    private BigBang() {
    }

    public static void registerAction(@ActionType String type, Action action) {
        mActionMap.put(type, action);
    }

    public static void unregisterAction(@ActionType String type) {
        mActionMap.remove(type);
    }

    public static Action getAction(@ActionType String type) {
        return mActionMap.get(type);
    }

    public static void startAction(Context context, String type, String text) {
        Action action = BigBang.getAction(type);
        if (action != null) {
            action.start(context, text);
        }
    }

    public static SimpleParser getSegmentParser(Context context) {
        String segmentEngine = Treasure.get(context, Config.class).getSegmentEngine();
        switch (segmentEngine) {
            case TYPE_CHARACTER:
                return new CharacterParser();
            case TYPE_NETWORK:
                if (!isNetworkAvailable(context)) {
                    Toast.makeText(context, "网络不可用，将使用本地词库进行分词", Toast.LENGTH_SHORT).show();
                    return new ThirdPartyParser(context);
                } else {
                    return new NetworkParser();
                }
            case TYPE_THIRD:
                return new ThirdPartyParser(context);
            default:
                return new ThirdPartyParser(context);
        }
    }

    public static void setSegmentParser(SimpleParser parser) {
        sParser = parser;
    }

    public static int getItemSpace() {
        return sItemSpace;
    }

    public static int getLineSpace() {
        return sLineSpace;
    }

    public static int getItemTextSize() {
        return sItemTextSize;
    }

    public static boolean isNetworkAvailable(Context context) {

        // 获得网络状态管理器
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager == null) {
            return false;
        } else {
            // 建立网络数组
            NetworkInfo[] net_info = connectivityManager.getAllNetworkInfo();

            if (net_info != null) {
                for (int i = 0; i < net_info.length; i++) {
                    // 判断获得的网络状态是否是处于连接状态
                    if (net_info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
