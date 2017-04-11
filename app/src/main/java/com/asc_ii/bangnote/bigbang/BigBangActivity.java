package com.asc_ii.bangnote.bigbang;

import android.content.Intent;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.asc_ii.bangnote.R;
import com.asc_ii.bangnote.segment.HandlerCallback;
import com.asc_ii.bangnote.segment.SimpleParser;

public class BigBangActivity extends AppCompatActivity implements BigBangLayout.ActionListener {

    public static final String EXTRA_TEXT = "extra_text";
    private BigBangLayout mLayout;
    String textToBang;

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        mLayout.reset();
        handleIntent(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_big_bang);
        mLayout = (BigBangLayout) findViewById(R.id.bigbang);
        mLayout.setActionListener(this);
        if (BigBang.getItemSpace() > 0) mLayout.setItemSpace(BigBang.getItemSpace());
        if (BigBang.getLineSpace() > 0) mLayout.setLineSpace(BigBang.getLineSpace());
        if (BigBang.getItemTextSize() > 0) mLayout.setItemTextSize(BigBang.getItemTextSize());
        handleIntent(getIntent());
    }

    private void handleIntent(Intent intent) {
        Uri data = intent.getData();
        if (data != null) {
            String text = data.getQueryParameter(EXTRA_TEXT);
            textToBang = text;

            if (TextUtils.isEmpty(text)) {
                finish();
                return;
            }

            SimpleParser parser = BigBang.getSegmentParser(getApplicationContext());
            final LinearLayout progress_waiting = (LinearLayout) findViewById(R.id.waiting_show);
            final TextView nativeParser = (TextView) findViewById(R.id.native_slow_wait_text);
            final LottieAnimationView banging = (LottieAnimationView) findViewById(R.id.banging_animation);
            progress_waiting.setVisibility(View.VISIBLE);
            banging.setVisibility(View.VISIBLE);
            switch (BigBang.parserType) {
                case BigBang.NETWORK_NOT_AVAILABLE:
                    Toast.makeText(getApplicationContext(), getString(R.string.network_unavailable), Toast.LENGTH_SHORT).show();
                case BigBang.SET_AS_NATIVE_PARSER:
                    nativeParser.setVisibility(View.VISIBLE);
                    break;
                case BigBang.SET_AS_CHARACTER_PARSER:
                case BigBang.NETWORK_PARSER_USING:
                    nativeParser.setVisibility(View.INVISIBLE);
            }

            parser.parse(text, new HandlerCallback<String[]>() {
                @Override
                public void onFinish(String[] result) {
                    mLayout.reset();
                    for (String str : result) {
                        mLayout.addTextItem(str);
                    }
                    progress_waiting.post(new Runnable() {
                        @Override
                        public void run() {
                            progress_waiting.setVisibility(View.GONE);
                            nativeParser.setVisibility(View.INVISIBLE);
                            banging.setVisibility(View.GONE);
                        }
                    });
                }

                @Override
                public void onError(Exception e) {
                    Toast.makeText(BigBangActivity.this, "分词出错：" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        } else {
            this.finish();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        String selectedText = mLayout.getSelectedText();
        BigBang.startAction(this, BigBang.ACTION_BACK, selectedText);
        BigBang.startAction(this, BigBang.ACTION_SAVE, textToBang);
    }

    @Override
    public void onSearch(String text) {
        BigBang.startAction(this, BigBang.ACTION_SEARCH, text);
    }

    @Override
    public void onShare(String text) {
        BigBang.startAction(this, BigBang.ACTION_SHARE, text);
    }

    @Override
    public void onCopy(String text) {
        BigBang.startAction(this, BigBang.ACTION_COPY, text);
    }

}
