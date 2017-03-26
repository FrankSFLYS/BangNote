package com.asc_ii.bangnote.xposed.process;

import java.util.Set;

import de.robv.android.xposed.XSharedPreferences;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

import static android.provider.ContactsContract.Directory.PACKAGE_NAME;
import static com.asc_ii.bangnote.xposed.XposedConstant.SP_DISABLE_KEY;
import static com.asc_ii.bangnote.xposed.XposedConstant.SP_NAME;

/**
 * Created by dim on 17/1/18.
 */

public class FilterProcess implements IProcess {
    @Override
    public boolean process(final XC_LoadPackage.LoadPackageParam loadPackageParam) {
        XSharedPreferences appXSP = new XSharedPreferences(PACKAGE_NAME, SP_NAME);
        appXSP.makeWorldReadable();
        Set<String> disAppSet = appXSP.getStringSet(SP_DISABLE_KEY, null);
        if (disAppSet != null && disAppSet.contains(loadPackageParam.packageName)) {
            return true;
        }
        return false;
    }
}
