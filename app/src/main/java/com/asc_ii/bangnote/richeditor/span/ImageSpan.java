package com.asc_ii.bangnote.richeditor.span;

import android.graphics.drawable.Drawable;

import com.asc_ii.bangnote.richeditor.StyleType;

/**
 * Created by zhou on 2016/11/28.
 */

public class ImageSpan extends android.text.style.ImageSpan implements Styleable {
    public ImageSpan(Drawable d, String source) {
        super(d, source);
    }

    @Override
    public int getStyleType() {
        return StyleType.IMAGE;
    }
}
