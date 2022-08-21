package com.example.serviceclass021;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class CallBackServ extends Service {
    public CallBackServ() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}