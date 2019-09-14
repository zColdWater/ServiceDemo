package com.example.servicedemo;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;


// 备注: 在Service里面调用 stopSelf() 方法就可以让服务停下来
public class MyService extends Service {

    private DownloadBinder mBinder = new DownloadBinder();

    class DownloadBinder extends Binder {
        public void startDownload() {
            Log.d("MyService","startDownload");
        }
        public int getProgress() {
            Log.d("MyService","getProgress");
            return 0;
        }
    }

    public MyService() {}

    // 服务创建时候调用
    @Override
    public void onCreate() {
        super.onCreate();
        Toast.makeText(this, "服务创建", Toast.LENGTH_SHORT).show();
        Log.d("MyService","服务创建");
    }

    // 每次服务启动的时候调用
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this,"服务开启",Toast.LENGTH_SHORT);
        Log.d("MyService","服务开启");

        new Thread(new Runnable() {
            @Override
            public void run() {
                // 处理具体逻辑 非UI线程
                Log.d("Thread","This is non ui thread!");
            }
        });

        return super.onStartCommand(intent, flags, startId);
    }

    // 服务销毁的时候调用
    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this,"服务销毁",Toast.LENGTH_SHORT);
        Log.d("MyService","服务销毁");
    }


    @Override
    public IBinder onBind(Intent intent) {
        Toast.makeText(this,"服务绑定",Toast.LENGTH_SHORT);
        Log.d("MyService","服务绑定");
//        throw new UnsupportedOperationException("Not yet implemented");
        return mBinder;
    }

}
