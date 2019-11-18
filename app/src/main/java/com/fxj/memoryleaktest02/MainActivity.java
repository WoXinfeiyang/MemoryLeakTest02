package com.fxj.memoryleaktest02;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    private static final String TAG="fxj_"+MainActivity.class.getSimpleName();

    MyListener myListener=new MyListener() {
        @Override
        public void register(Activity activity) {
            Log.d(TAG,"MyListener hashCode="+hashCode()+",Activity.hashCode="+activity.hashCode());
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG,"##onCreate##");

    }

    class LeakThread extends Thread{

        @Override
        public void run() {
            try {

                    ListenerControl.setListener(MainActivity.this,new MyListener() {
                        @Override
                        public void register(Activity activity) {
                            Log.d(TAG,"MyListener hashCode="+hashCode()+",Activity.hashCode="+activity.hashCode());
                        }
                    });
                    Thread.sleep(20*1000);
                    ListenerControl.performRegister(MainActivity.this);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG,"##onRestart##");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG,"##onStart##");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG,"##onResume##");
        LeakThread mLeakThread=new LeakThread();
        mLeakThread.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG,"##onPause##Activity hashCode="+hashCode());
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG,"##onStop##");
        BaseApplication.getRefWatcher(this).watch(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"##onDestroy##");
        BaseApplication.getRefWatcher(this).watch(this);
    }
}
