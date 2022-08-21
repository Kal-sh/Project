package com.example.serviceclass021;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Boolean state = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btn = findViewById(R.id.btnService);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),CallBackServ.class);
                if (state){
                    startService(i);
                    state = false;
                    btn.setText("Stop Service");
                }
                else{
                    stopService(i);
                    state = true;
                    btn.setText("Start Service");
                }
            }
        });
    }
}