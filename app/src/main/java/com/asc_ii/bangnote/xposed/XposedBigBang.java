package com.asc_ii.bangnote.xposed;

import com.asc_ii.bangnote.xposed.common.XposedUtils;
import com.asc_ii.bangnote.xposed.process.CommonAppProcess;
import com.asc_ii.bangnote.xposed.process.FilterProcess;
import com.asc_ii.bangnote.xposed.process.Processor;
import com.asc_ii.bangnote.xposed.process.WeChatProcess;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.callbacks.XC_LoadPackage;


/**
 * Created by dim on 16/10/23.
 */

public class XposedBigBang implements IXposedHookLoadPackage {

    private Processor processor;

    public XposedBigBang() {
        processor = new Processor();
        processor.addProcess(new FilterProcess());
        processor.addProcess(new WeChatProcess());
        processor.addProcess(new CommonAppProcess());
    }

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam loadPackageParam) throws Throwable {
        XposedUtils.setXpoedEnable(loadPackageParam);
        processor.process(loadPackageParam);
    }
}