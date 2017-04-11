/*
 * The MIT License (MIT)
 * Copyright (c) 2016 baoyongzhang <baoyz94@gmail.com>
 */
package com.asc_ii.bangnote.segment_jieba;

import com.asc_ii.bangnote.segment.SegmentException;
import com.asc_ii.bangnote.segment.SegmentUtil;
import com.asc_ii.bangnote.segment.SimpleParser;
import com.huaban.analysis.jieba.JiebaSegmenter;
import com.huaban.analysis.jieba.SegToken;

import java.util.List;

/**
 * Created by baoyongzhang on 2016/10/27.
 */
public class JiebaParser extends SimpleParser {

    @Override
    public String[] parseSync(String text) throws SegmentException {
        try {
            text = SegmentUtil.filterInvalidChar(text);
            JiebaSegmenter segmenter = new JiebaSegmenter();
            List<SegToken> list = segmenter.process(text, JiebaSegmenter.SegMode.SEARCH);
            String result[] = new String[list.size()];
            for (int i = 0; i < list.size(); i++) {
                SegToken segToken = list.get(i);
                result[i] = segToken.word;
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            throw new SegmentException(e);
        }
    }
}
