package com.fxj.memoryleaktest02;

import android.app.Activity;

import java.util.HashMap;

public class ListenerControl {

    private static HashMap<Activity,MyListener> hashMap=new HashMap<>();
    public static void setListener(Activity activity,MyListener listener){
        hashMap.put(activity,listener);
    }
    public static void performRegister(Activity activity){
       MyListener myListener=(MyListener) hashMap.get(activity);
       if(myListener!=null){
           myListener.register(activity);
       }
    }
}
