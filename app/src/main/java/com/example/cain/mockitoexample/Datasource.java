package com.example.cain.mockitoexample;


import android.os.Handler;
import android.os.Looper;

public class Datasource {

    private final Handler mainHandler = new Handler(Looper.getMainLooper());

    public String getValueSync(int i){
        return "sync:" + i;
    }


    public void getValueAsync(final int i, final Callback callback){
        Runnable task = new Runnable(){
            @Override
            public void run() {
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onCallback("async:" + i );
                    }
                });
            }
        };
        new Thread(task).start();
    }


    public interface Callback {
        void onCallback(String value);
    }
}
