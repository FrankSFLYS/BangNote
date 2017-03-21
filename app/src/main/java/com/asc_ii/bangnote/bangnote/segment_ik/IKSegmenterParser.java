/*
 * The MIT License (MIT)
 * Copyright (c) 2016 baoyongzhang <baoyz94@gmail.com>
 */
package com.asc_ii.bangnote.bangnote.segment_ik;

import android.app.Application;

import com.asc_ii.bangnote.bangnote.analyzer.core.IKSegmenter;
import com.asc_ii.bangnote.bangnote.analyzer.core.Lexeme;
import com.asc_ii.bangnote.bangnote.segment.SegmentException;
import com.asc_ii.bangnote.bangnote.segment.SimpleParser;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by baoyongzhang on 2016/10/28.
 */
public class IKSegmenterParser extends SimpleParser {

    private final Application mApplication;

    public IKSegmenterParser(Application application) {
        mApplication = application;
    }

    @Override
    public String[] parseSync(String text) throws SegmentException {
        IKSegmenter segmenter = new IKSegmenter(mApplication, new StringReader(text), true);
        try {
            Lexeme next;
            List<String> result = new ArrayList<>();
            while ((next = segmenter.next()) != null) {
                if (next.getLength() > 0){
                    result.add(next.getLexemeText());
                }
            }
            return result.toArray(new String[0]);
        } catch (IOException e) {
            e.printStackTrace();
            throw new SegmentException(e);
        }
    }

}
