package com.asc_ii.bangnote;

/**
 * Created by FrankFLY on 2017/3/17.
 */

public class NoteItemCreator {

    private static int userColorRID, bangColorRID;

    public static boolean isOnSelectMode;

    public NoteItem item;

    private boolean isChecked = false;

    public static void setUserColorRID(int newUserColorRID) {
        userColorRID = newUserColorRID;
    }

    public static void setBangColorRID(int newBangColorRID) {
        bangColorRID = newBangColorRID;
    }

    public int getColorId() {
        if (item.getCreatedByUser()) {
            return userColorRID;
        } else {
            return bangColorRID;
        }
    }

    public void setChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }

    public boolean getChecked() {
        return isChecked;
    }

}