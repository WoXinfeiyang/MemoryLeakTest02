package com.fxj.memoryleaktest02;

import android.app.Application;
import android.content.Context;
import android.os.Looper;
import android.util.Log;
import android.util.Printer;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

public class BaseApplication extends Application {

    private static final String TAG="fxj_"+BaseApplication.class.getSimpleName();

    private RefWatcher mRefWatcher;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG,"##onCreate##");
        mRefWatcher=setupLeakCanary();
        Looper looper=getMainLooper();
        looper.setMessageLogging(new Printer(){

            @Override
            public void println(String x) {
                Log.e(TAG,"MessageLogging:"+x);
            }
        });
    }

    private RefWatcher setupLeakCanary(){
        Log.d(TAG,"##setupLeakCanary##");
        if(LeakCanary.isInAnalyzerProcess(this)){/*如果当前进程是给LeakCanary进行堆分析的则返回RefWatcher.DISABLED*/
            return RefWatcher.DISABLED;
        }
        return LeakCanary.install(this);
    }

    public static RefWatcher getRefWatcher(Context context){
        BaseApplication baseApplication= (BaseApplication) context.getApplicationContext();
        return baseApplication.mRefWatcher;
    }
}
