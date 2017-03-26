/*
 * The MIT License (MIT)
 * Copyright (c) 2016 baoyongzhang <baoyz94@gmail.com>
 */
package com.asc_ii.bangnote.bigbang;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.StringDef;

import com.asc_ii.bangnote.Config;
import com.asc_ii.bangnote.ThirdPartyParser;
import com.asc_ii.bangnote.action.Action;
import com.asc_ii.bangnote.segment.CharacterParser;
import com.asc_ii.bangnote.segment.NetworkParser;
import com.asc_ii.bangnote.segment.SimpleParser;
import com.baoyz.treasure.Treasure;

import java.lang.annotation.Retention;
import java.util.HashMap;
import java.util.Map;

import static com.asc_ii.bangnote.SegmentEngine.TYPE_CHARACTER;
import static com.asc_ii.bangnote.SegmentEngine.TYPE_NETWORK;
import static com.asc_ii.bangnote.SegmentEngine.TYPE_THIRD;
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
    public static final int SET_AS_CHARACTER_PARSER = 0;
    public static final int SET_AS_NATIVE_PARSER = 1;
    public static final int NETWORK_NOT_AVAILABLE = 2;
    public static final int NETWORK_PARSER_USING = 3;
    public static int parserType;
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
                parserType = SET_AS_CHARACTER_PARSER;
                return new CharacterParser();
            case TYPE_NETWORK:
                if (!isNetworkAvailable(context)) {
                    parserType = NETWORK_NOT_AVAILABLE;
                    return new ThirdPartyParser(context);
                } else {
                    parserType = NETWORK_PARSER_USING;
                    return new NetworkParser();
                }
            case TYPE_THIRD:
                parserType = SET_AS_NATIVE_PARSER;
                return new ThirdPartyParser(context);
            default:
                parserType = SET_AS_NATIVE_PARSER;
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
