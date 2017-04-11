/*
 * The MIT License (MIT)
 * Copyright (c) 2016 baoyongzhang <baoyz94@gmail.com>
 */
package com.asc_ii.bangnote.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.asc_ii.bangnote.BuildConfig;
import com.asc_ii.bangnote.R;

import me.drakeet.multitype.Items;
import me.drakeet.support.about.Card;
import me.drakeet.support.about.Category;
import me.drakeet.support.about.Contributor;
import me.drakeet.support.about.License;
import me.drakeet.support.about.Line;

/**
 * Created by baoyongzhang on 2016/10/25.
 * Modified by FrankFLY on 2017/03/10.
 */
public class AboutActivity extends me.drakeet.support.about.AboutActivity {

    @Override
    protected void onCreateHeader(ImageView icon, TextView slogan, TextView version) {
        icon.setImageResource(R.mipmap.ic_launcher);
        slogan.setText(getString(R.string.about_bangnote));
        version.setText(BuildConfig.VERSION_NAME);
    }

    @Override
    protected void onItemsCreated(@NonNull Items items) {
        items.add(new Category("介绍与帮助"));
        items.add(new Card(getString(R.string.description), "项目主页"));

        items.add(new Line());

        items.add(new Category("Developers"));

        items.add(new Contributor(R.mipmap.lavender, "Lavender", "设计师"));
        items.add(new Contributor(R.mipmap.crn, "Percy", "Note功能开发"));
        items.add(new Contributor(R.mipmap.ff, "Frank FLY", "BigBang功能开发"));

        items.add(new Contributor(R.mipmap.bao, "baoyongzhang", "核心功能开发"));
        items.add(new Contributor(R.mipmap.dim, "zzz40500", "Xposed 模块开发"));

        items.add(new Line());

        items.add(new Category("Open Source Licenses"));
        items.add(new License("BigBang", "baoyongzhang", License.MIT,
                "https://github.com/baoyongzhang/BigBang"));
        items.add(new License("Treasure", "baoyongzhang", License.MIT,
                "https://github.com/baoyongzhang/Treasure"));
        items.add(new License("OkHttp", "square", License.APACHE_2,
                "https://github.com/square/okhttp"));
        items.add(new License("discrete-seekbar", "AnderWeb", License.APACHE_2,
                "https://github.com/AnderWeb/discreteSeekBar"));
        items.add(new License("IK-Analyzer", "wltea", License.APACHE_2,
                "http://git.oschina.net/wltea/IK-Analyzer-2012FF"));
        items.add(new License("MultiType", "drakeet", License.APACHE_2,
                "https://github.com/drakeet/MultiType"));
        items.add(new License("about-page", "drakeet", License.APACHE_2,
                "https://github.com/drakeet/about-page"));
    }

    @Override
    protected void onActionClick(View action) {
        super.onActionClick(action);
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/FrankSFLYS/BangNote"));
        startActivity(intent);
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
