package com.asc_ii.bangnote.bangnote.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.SwitchCompat;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.asc_ii.bangnote.bangnote.R;

public class NoteSettingActivity extends AppCompatActivity {

    private ImageView mNote_Dot_Color_image;

    private TextView mPopMenuBtn;

    static final public String PREF_NOTE = "note";
    static final public String USER_COLOR = "user_color_id";
    static final public String BANG_COLOR = "bang_color_id";
    static final public String AUTO_SAVE = "save";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_settings);

        mNote_Dot_Color_image = (ImageView) findViewById(R.id.Note_Dot_Color);
        mPopMenuBtn = (TextView) findViewById(R.id.des_Note_Dot_Color);

        RelativeLayout bangNoteHolder = (RelativeLayout) findViewById(R.id.bang_note_holder);
        RelativeLayout userNoteHolder = (RelativeLayout) findViewById(R.id.user_note_holder);
        ImageView imageView = (ImageView) findViewById(R.id.BigBang_Note_Dot_Color);
        SwitchCompat saveBigBangAsNotes = (SwitchCompat) findViewById(R.id.auto_save);

        SharedPreferences preferences = getSharedPreferences(PREF_NOTE, MODE_PRIVATE);

        int userColorId = preferences.getInt(USER_COLOR, R.mipmap.dot_blue);
        int bangColorId = preferences.getInt(BANG_COLOR, R.mipmap.dot_pink);
        Boolean isAutoSave = preferences.getBoolean(AUTO_SAVE, false);

        mNote_Dot_Color_image.setImageResource(userColorId);
        imageView.setImageResource(bangColorId);
        saveBigBangAsNotes.setChecked(isAutoSave);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        userNoteHolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopMenuBtn = (TextView) findViewById(R.id.des_Note_Dot_Color);
                popUpItems(1);
            }
        });
        bangNoteHolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopMenuBtn = (TextView) findViewById(R.id.des_BigBang_Note_Dot_Color);
                popUpItems(2);
            }
        });
        saveBigBangAsNotes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences.Editor editor = getSharedPreferences(PREF_NOTE, MODE_PRIVATE).edit();
                editor.putBoolean(AUTO_SAVE, isChecked);
                editor.apply();
            }
        });
    }

    private void setImageRes(int id, int resId) {
        if (id == 1) {
            ImageView imageView = (ImageView) findViewById(R.id.Note_Dot_Color);
            imageView.setImageResource(resId);
            SharedPreferences.Editor editor = getSharedPreferences(PREF_NOTE, MODE_PRIVATE).edit();
            editor.putInt(USER_COLOR, resId);
            editor.apply();
        } else {
            ImageView imageView = (ImageView) findViewById(R.id.BigBang_Note_Dot_Color);
            imageView.setImageResource(resId);
            SharedPreferences.Editor editor = getSharedPreferences(PREF_NOTE, MODE_PRIVATE).edit();
            editor.putInt(BANG_COLOR, resId);
            editor.apply();
        }
    }

    private void popUpItems(final int id) {
        PopupMenu popup = new PopupMenu(NoteSettingActivity.this, mPopMenuBtn);
        //Inflating the Popup using xml file
        popup.getMenuInflater().inflate(R.menu.dot_color_menu, popup.getMenu());
        //registering popup with OnMenuItemClickListener

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.dot_black:
                        setImageRes(id, R.mipmap.dot_dark);
                        return true;
                    case R.id.dot_blackblue:
                        setImageRes(id, R.mipmap.dot_darkblue);
                        return true;
                    case R.id.dot_grey:
                        setImageRes(id, R.mipmap.dot_grey);
                        return true;
                    case R.id.dot_red:
                        setImageRes(id, R.mipmap.dot_red);
                        return true;
                    case R.id.dot_blue:
                        setImageRes(id, R.mipmap.dot_blue);
                        return true;
                    case R.id.dot_green:
                        setImageRes(id, R.mipmap.dot_green);
                        return true;
                    case R.id.dot_yellow:
                        setImageRes(id, R.mipmap.dot_yellow);
                        return true;
                    case R.id.dot_pink:
                        setImageRes(id, R.mipmap.dot_pink);
                        return true;
                }
                return true;
            }
        });
        popup.show();
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