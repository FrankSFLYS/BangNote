package com.asc_ii.bangnote.bangnote.activity;

/*
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.asc_ii.bangnote.bangnote.R;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
*/

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.asc_ii.bangnote.bangnote.NoteItem;
import com.asc_ii.bangnote.bangnote.NoteItemAdapter;
import com.asc_ii.bangnote.bangnote.NoteItemCreator;
import com.asc_ii.bangnote.bangnote.R;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public final int DEFAULT_NOTE_ITEM_ID = 1;

    public static final String ACTION_NEW_NOTE = "NEW_NOTE";

    private List<NoteItemCreator> noteItemCreatorList = new ArrayList<>();

    private NoteItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        LitePal.initialize(this);
        NoteItem defaultItem = new NoteItem();
        defaultItem.setId(DEFAULT_NOTE_ITEM_ID);
        defaultItem.setCreatedByUser(false);
        defaultItem.setLastEditTime(System.currentTimeMillis());
        if(DataSupport.isExist(NoteItem.class, "id = ?", "" + DEFAULT_NOTE_ITEM_ID)) {
            defaultItem.saveOrUpdate("id = ?", "" + DEFAULT_NOTE_ITEM_ID);
        } else {
            defaultItem.setCreateTime(System.currentTimeMillis());
            defaultItem.save();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EditPadActivity.class);
                intent.setAction(ACTION_NEW_NOTE);
                int currentMaxId = DataSupport.order("id desc").limit(1).find(NoteItem.class).get(0).getId();
                intent.putExtra("id", currentMaxId);
                startActivity(intent);
            }
        });
        fab.setLongClickable(true);
        fab.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Snackbar.make(v, R.string.snackbar_new, Snackbar.LENGTH_SHORT)
                        .setAction(R.string.new_note, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(MainActivity.this, EditPadActivity.class);
                                intent.setAction(ACTION_NEW_NOTE);
                                int currentMaxId = DataSupport.order("id desc").limit(1).find(NoteItem.class).get(0).getId();
                                intent.putExtra("id", currentMaxId);
                                startActivity(intent);
                            }
                        })
                        .show();
                return false;
            }
        });

        NoteItemCreator.isOnSelectMode = false;
    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences preferences = this.getSharedPreferences(NoteSettingActivity.PREF_NOTE, MODE_PRIVATE);
        int userColorRID = preferences.getInt(NoteSettingActivity.USER_COLOR, R.mipmap.dot_blue);
        int bangColorRID = preferences.getInt(NoteSettingActivity.BANG_COLOR, R.mipmap.dot_pink);
        NoteItemCreator.setUserColorRID(userColorRID);
        NoteItemCreator.setBangColorRID(bangColorRID);
        initNoteItemList();

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new NoteItemAdapter(noteItemCreatorList);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_edit) {
            boolean isOnSelectMode = false;
            if (noteItemCreatorList.size() == 0) {
                setExitSelectMode(item);
                Toast.makeText(this, R.string.warn_note_list_empty, Toast.LENGTH_SHORT).show();
            } else {
                for (NoteItemCreator itemCreator : noteItemCreatorList) {
                    if (itemCreator != null) {
                        if (NoteItemCreator.isOnSelectMode) {
                            //Next: exit select mode
                            isOnSelectMode = false;
                            NoteItemCreator.isOnSelectMode = false;
                        } else {
                            //Next: enter select mode
                            isOnSelectMode = true;
                            NoteItemCreator.isOnSelectMode = true;
                        }
                    }
                    break;
                }
                if (!isOnSelectMode) {
                    setExitSelectMode(item);
                } else {
                    setEnterSelectMode(item);
                }
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id) {
            case R.id.nav_bigbang_settings:
                Intent intent = new Intent(MainActivity.this, BigBangSettingsActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_note_settigns:
                Intent intent_note = new Intent(MainActivity.this, NoteSettingActivity.class);
                startActivity(intent_note);
                break;
            case R.id.nav_share:
                Intent intent_share = new Intent(Intent.ACTION_SEND);
                intent_share.setType("text/plain");
                intent_share.putExtra(Intent.EXTRA_TEXT, getString(R.string.action_share_text));
                intent_share.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(Intent.createChooser(intent_share, getString(R.string.action_share_title)));
                break;
            case R.id.nav_about:
                Intent intent_about = new Intent(MainActivity.this, AboutActivity.class);
                startActivity(intent_about);
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void initNoteItemList() {
        List<NoteItem> noteItems = DataSupport
                .order("lastEditTime desc")                 //按最后修改时间的倒序排列
                .where("id > ?", String.valueOf(DEFAULT_NOTE_ITEM_ID))  //ID要大于默认的ID值才可以显示在列表当中
                .find(NoteItem.class);
        noteItemCreatorList.clear();
        for (NoteItem noteItem : noteItems) {
            NoteItemCreator creator = new NoteItemCreator();
            creator.item = noteItem;
            noteItemCreatorList.add(creator);
        }
        ConstraintLayout nothingToShow = (ConstraintLayout) findViewById(R.id.none_notes_shower);
        if (noteItems.size() == 0) {
            nothingToShow.setVisibility(View.VISIBLE);
        } else {
            nothingToShow.setVisibility(View.GONE);
        }
    }

    private void setEnterSelectMode(final MenuItem item) {
        adapter.notifyDataSetChanged();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        item.setIcon(R.mipmap.action_done);
        item.setTitle(R.string.action_done);
        fab.setImageResource(R.mipmap.ic_menu_delete);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO:Set fab click event: Delete Chosen notes
                // TODO: 2017/3/19 Pop a warning window
                List<NoteItemCreator> itemsToDelete = new ArrayList<NoteItemCreator>();
                for (NoteItemCreator itemCreator : noteItemCreatorList) {
                    if(itemCreator != null) {
                        if (itemCreator.getChecked()) {
                            DataSupport.delete(NoteItem.class, itemCreator.item.getId());
                            itemsToDelete.add(itemCreator); //Warning: 不可以在迭代过程中删除list元素
                        }
                    }
                }
                for (NoteItemCreator itemToDelete : itemsToDelete) {
                    if (itemToDelete != null) {
                        noteItemCreatorList.remove(itemToDelete);
                    }
                }
                ConstraintLayout nothingToShow = (ConstraintLayout) findViewById(R.id.none_notes_shower);
                if (noteItemCreatorList.size() == 0) {
                    nothingToShow.setVisibility(View.VISIBLE);
                    setExitSelectMode(item);
                    NoteItemCreator.isOnSelectMode = false;
                } else {
                    nothingToShow.setVisibility(View.GONE);
                }
                adapter.notifyDataSetChanged();

            }
        });
        fab.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Snackbar.make(v, R.string.snackbar_delete, Snackbar.LENGTH_SHORT)
                        .show();
                return false;
            }
        });

    }

    private void setExitSelectMode(MenuItem item) {
        adapter.notifyDataSetChanged();

        item.setIcon(R.mipmap.action_edit);
        item.setTitle(R.string.action_edit);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setImageResource(R.mipmap.ic_menu_edit);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EditPadActivity.class);
                intent.setAction(ACTION_NEW_NOTE);
                int currentMaxId = DataSupport.order("id desc").limit(1).find(NoteItem.class).get(0).getId();
                intent.putExtra("id", currentMaxId);
                startActivity(intent);
            }
        });
        fab.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Snackbar.make(v, R.string.snackbar_new, Snackbar.LENGTH_SHORT)
                        .setAction(R.string.new_note, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(MainActivity.this, EditPadActivity.class);
                                intent.setAction(ACTION_NEW_NOTE);
                                int currentMaxId = DataSupport.order("id desc").limit(1).find(NoteItem.class).get(0).getId();
                                intent.putExtra("id", currentMaxId);
                                startActivity(intent);
                            }
                        })
                        .show();
                return false;
            }
        });

    }
}
