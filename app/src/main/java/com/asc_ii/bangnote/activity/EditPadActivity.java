package com.asc_ii.bangnote.activity;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.view.Menu;
import android.view.MenuItem;

import com.asc_ii.bangnote.NoteItem;
import com.asc_ii.bangnote.R;
import com.asc_ii.bangnote.richeditor.RichEditor;
import com.asc_ii.bangnote.richeditor.RichEditorFunctionBar;
import com.asc_ii.bangnote.richeditor.RichEditorView;
import com.asc_ii.bangnote.richeditor.StyleType;

import org.litepal.crud.DataSupport;

public class EditPadActivity extends AppCompatActivity {

    static CharSequence text;

    private static final String TAG = "MainActivity";
    private RichEditor richEditor;
    //private Spinner spinner;

    private static final int[] types = {StyleType.BOLD, StyleType.ITALIC, StyleType.DELETE, StyleType.UNDER_LINE, StyleType.QUOTE};
    private RichEditorView richEditorView;
    private RichEditorView richEditorTitle;
    private int currentEditId;
    private NoteItem noteItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_pad);

        richEditorTitle = (RichEditorView) findViewById(R.id.edit_title);
        richEditorView = (RichEditorView) findViewById(R.id.edit);
        RichEditorFunctionBar functionBar = (RichEditorFunctionBar) findViewById(R.id.function_bar);
        richEditorView.setCursorChangeListener(functionBar);

        richEditor = new RichEditor(richEditorView, richEditorView);
        functionBar.setRichEditor(richEditor);
        functionBar.setCursorProvider(richEditorView);

        Intent intent = getIntent();
        String action = intent.getAction();
        currentEditId = intent.getIntExtra("id", 1);
        if(action.equals(Intent.ACTION_EDIT)){
            //Edit Exists note item
            noteItem = DataSupport.find(NoteItem.class, currentEditId);
            richEditorTitle.setText(noteItem.getTitle());
            if (Build.VERSION.SDK_INT < 24) {
                richEditorView.setText(Html.fromHtml(noteItem.getText()));
            } else {
                richEditorView.setText(Html.fromHtml(noteItem.getText(), Html.FROM_HTML_MODE_COMPACT));
            }
            richEditorView.setMovementMethod(LinkMovementMethod.getInstance());

        } else {
            noteItem = new NoteItem();
            noteItem.setId(currentEditId);
            noteItem.setCreatedByUser(true);
            noteItem.setCreateTime(System.currentTimeMillis());
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_pad_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.preview:
                Intent intent = new Intent(this, PreviewActivity.class);
                 text = richEditorView.getSSB();
                 startActivity(intent);
                 break;
            case android.R.id.home:
                boolean savedOrNot = saveNote();
                finish();
                return savedOrNot;
        }
        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveNote();
    }

    private boolean saveNote() {
        noteItem.setLastEditTime(System.currentTimeMillis());
        noteItem.setTitle(richEditorTitle.getSSB().toString());

        SpannableStringBuilder text = richEditorView.getSSB();
        if (text != null) {
            //Set if title is null
            if (noteItem.getTitle().isEmpty()) {
                noteItem.setTitle(getString(R.string.empty_title));
            }
            //Set content text
            if (Build.VERSION.SDK_INT < 24) {
                noteItem.setText(Html.toHtml(text));
            } else {
                noteItem.setText(Html.toHtml(text, Html.TO_HTML_PARAGRAPH_LINES_CONSECUTIVE));
            }
            //Set preview text
            int length = text.length();
            noteItem.setPrevText((length > 30 ?
                    text.subSequence(0, 30).toString()
                    : text.subSequence(0, length).toString()));
            noteItem.save();
            return true;
        } else {
            if (noteItem.getTitle().isEmpty()) {
                //Save nothing
                return false;
            } else {
                //Save title only
                noteItem.setText("");
                noteItem.save();
                return true;
            }
        }
    }
}
