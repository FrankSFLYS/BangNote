package com.asc_ii.bangnote.bangnote.richeditor.span;


import com.asc_ii.bangnote.bangnote.richeditor.StyleType;

/**
 * Created by zhou on 2016/11/28.
 * 标记span为对应的style
 */
public interface Styleable {

    /**
     * 获取span对应的styleType
     *
     * @return StyleType
     */
    @StyleType
    int getStyleType();

}
