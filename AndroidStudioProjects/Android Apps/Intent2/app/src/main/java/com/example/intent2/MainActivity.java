package com.example.intent2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btn = findViewById(R.id.button);
        EditText ex = findViewById(R.id.editText);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String num = ex.getText().toString();
                if(!num.equals("")){
                    Intent i = new Intent(Intent.ACTION_DIAL);
                    i.setData(Uri.parse("tel: " + num));
                    startActivity(i);
                }
                else
                    Toast.makeText(getApplicationContext(),"Please Enter a Phone Number",Toast.LENGTH_LONG).show();
            }
        });

    }
}