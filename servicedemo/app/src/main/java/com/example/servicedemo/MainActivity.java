package com.example.servicedemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private MyService.DownloadBinder downloadBinder;

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.d("ServiceConnection","onServiceConnected");
            downloadBinder = (MyService.DownloadBinder) iBinder;
            downloadBinder.startDownload();
            downloadBinder.getProgress();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Log.d("ServiceConnection","onServiceDisconnected");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 开启服务 在后台一直运行
        Button btn1 = findViewById(R.id.button1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 开启后台服务状态 无客户端交互 模式
                Intent intent = new Intent(MainActivity.this,MyService.class);
                startService(intent);
            }
        });

        // 停止服务 停止在后台一直运行的服务
        Button btn2 = findViewById(R.id.button2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 停止后台服务状态 无客户端交互 模式
                Intent intent = new Intent(MainActivity.this,MyService.class);
                // 关闭Service
                stopService(intent);
            }
        });

        // 绑定服务 + 解绑服务
        Button btn3 = findViewById(R.id.button3);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 开启后台服务绑定模式 可以与客户端交互
                Intent intent = new Intent(MainActivity.this,MyService.class);
                // 绑定Service
                bindService(intent,connection,BIND_AUTO_CREATE);
                // 解绑Service
                unbindService(connection);
            }
        });

        // 自动开启子线程的 IntentService 服务
        Button btn4 = findViewById(R.id.button4);
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("MainActivity","自动开启子线程的 IntentService 服务");
                // 开启后台服务绑定模式 可以与客户端交互
                Intent intent = new Intent(MainActivity.this,MyIntentService.class);
                startService(intent);
            }
        });
    }
}
