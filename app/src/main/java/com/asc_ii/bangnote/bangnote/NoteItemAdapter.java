package com.asc_ii.bangnote.bangnote;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.asc_ii.bangnote.bangnote.activity.EditPadActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by FrankFLY on 2017/3/17.
 */

public class NoteItemAdapter extends RecyclerView.Adapter<NoteItemAdapter.ViewHolder> {

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd HH:mm");

    private Context mContext;

    private List<NoteItemCreator> mNoteItemList;

    static class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox checkBox;
        ImageView noteColor;
        TextView noteTitle;
        TextView noteContentPrev;
        TextView lastEditTime;
        CardView cardViewHolder;
        LinearLayout noteItemLeftHolder;
        LinearLayout noteItemLargeHolder;
        LinearLayout noteItemTextHolder;
        LinearLayout noteItemLineHolder;

        public ViewHolder(View view) {
            super(view);
            checkBox = (CheckBox)view.findViewById(R.id.note_item_checkbox);
            noteColor = (ImageView) view.findViewById(R.id.note_item_color_tag);
            noteTitle = (TextView) view.findViewById(R.id.note_item_title);
            noteContentPrev=(TextView)view.findViewById(R.id.note_item_content_preview);
            lastEditTime = (TextView) view.findViewById(R.id.note_item_last_edit_time);
            cardViewHolder = (CardView) view.findViewById(R.id.note_item_card_view_holder);
            noteItemLeftHolder = (LinearLayout) view.findViewById(R.id.note_item_left_holder);
            noteItemLargeHolder = (LinearLayout) view.findViewById(R.id.note_item_large_holder);
            noteItemTextHolder = (LinearLayout) view.findViewById(R.id.note_item_text_holder);
            noteItemLineHolder = (LinearLayout) view.findViewById(R.id.note_item_line_holder);
        }
    }

    public NoteItemAdapter(List<NoteItemCreator> noteItemList) {
        mNoteItemList = noteItemList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.note_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final NoteItemCreator noteItem = mNoteItemList.get(position);
        if (NoteItemCreator.isOnSelectMode) {
            holder.checkBox.setVisibility(View.VISIBLE);
            holder.checkBox.setChecked(noteItem.getChecked());
            holder.noteColor.setImageResource(noteItem.getColorId());
            //Toast.makeText(mContext, "Color set.", Toast.LENGTH_SHORT).show();
            holder.noteColor.setVisibility(View.GONE);
        } else {
            holder.checkBox.setVisibility(View.GONE);
            holder.checkBox.setChecked(false);
            noteItem.setChecked(false);
            holder.noteColor.setImageResource(noteItem.getColorId());
            //Toast.makeText(mContext, "Color set.", Toast.LENGTH_SHORT).show();
            holder.noteColor.setVisibility(View.VISIBLE);
        }

        holder.noteItemLeftHolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generalClickListener(v, position, holder);
            }
        });
        holder.noteItemLargeHolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generalClickListener(v, position, holder);
            }
        });
        holder.noteItemTextHolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generalClickListener(v, position, holder);
            }
        });
        holder.noteItemLineHolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generalClickListener(v, position, holder);
            }
        });

        holder.checkBox.setClickable(false);
        holder.noteTitle.setText(noteItem.item.getTitle());
        int length;
        if (noteItem.item.getText() == null) {
            holder.noteContentPrev.setText(" ");
        } else {
            length = noteItem.item.getText().length();
            if(length < 30) {
                holder.noteContentPrev.setText(noteItem.item.getText().substring(0, length));
            } else {
                holder.noteContentPrev.setText(noteItem.item.getText().substring(0, 30));
            }
        }
        holder.lastEditTime.setText(simpleDateFormat.format(new Date(noteItem.item.getLastEditTime())));
    }



    @Override
    public int getItemCount() {
        return mNoteItemList.size();
    }

    private void generalClickListener(View v, int position, ViewHolder holder) {
        if (NoteItemCreator.isOnSelectMode) {
            NoteItemCreator itemCreator = mNoteItemList.get(position);
            Boolean checked = itemCreator.getChecked();
            holder.checkBox.setChecked(!checked);
            itemCreator.setChecked(!checked);
        } else {
            NoteItemCreator noteItemCreator = mNoteItemList.get(position);
            noteItemCreator.setChecked(false);
            holder.checkBox.setChecked(false);
            Intent intent = new Intent(v.getContext(), EditPadActivity.class);
            intent.setAction(Intent.ACTION_EDIT);
            intent.putExtra("id", noteItemCreator.item.getId());
            v.getContext().startActivity(intent);
        }
    }

}
