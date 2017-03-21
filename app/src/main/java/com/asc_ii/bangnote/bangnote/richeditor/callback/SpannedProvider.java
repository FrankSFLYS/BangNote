package com.asc_ii.bangnote.bangnote.richeditor.callback;

import android.text.SpannableStringBuilder;

/**
 * Created by zhou on 2016/11/25.
 * Spanned提供器
 */
public interface SpannedProvider {

    /**
     * 获取SpannableStringBuilder
     *
     * @return ssb
     */
    SpannableStringBuilder getSSB();

}
