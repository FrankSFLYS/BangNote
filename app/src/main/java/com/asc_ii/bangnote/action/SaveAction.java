package com.asc_ii.bangnote.action;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import com.asc_ii.bangnote.NoteItem;

/**
 * Created by FrankFLY on 2017/3/21.
 */



public class SaveAction implements Action {

    public static SaveAction create() {
        return new SaveAction();
    }

    @Override
    public void start(Context context, String text) {
        if (!TextUtils.isEmpty(text)) {
            NoteItem noteItem = new NoteItem();
            noteItem.setCreateTime(System.currentTimeMillis());
            noteItem.setCreatedByUser(false);
            noteItem.setTitle("Bang 自动保存");
            noteItem.setText(text);
            noteItem.setLastEditTime(System.currentTimeMillis());
            noteItem.save();
            saveSuccess(context);
        }
    }

    public void saveSuccess(Context context) {
        Toast.makeText(context, "已保存到Note", Toast.LENGTH_SHORT).show();
    }
}
