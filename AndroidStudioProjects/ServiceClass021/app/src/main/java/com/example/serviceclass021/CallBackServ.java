package com.example.serviceclass021;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

public class CallBackServ extends Service {

    @Override
    public IBinder onBind (Intent intent){
        throw new UnsupportedOperationException();
    }
    @Override
    public void onCreate() {
        super.onCreate();
        Toast.makeText(getApplicationContext(), "onCreate callback method", Toast.LENGTH_SHORT).show();
    }


        public int onStartCommand (){
    }

}