package com.example.test4;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button btnSubmit = findViewById(R.id.btnSubmit);


//        btnSubmit.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v){
//                Toast.makeText(getApplicationContext(),"Succesful login", Toast.LENGTH_LONG).show();
//            }
//        });

    }

    public void login(View view) {
        Toast.makeText(getApplicationContext(),"Succesful login", Toast.LENGTH_LONG).show();
    }
}