package com.example.intent4;

import androidx.annotation.IntRange;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText ph = findViewById(R.id.editPhone);
        EditText ms = findViewById(R.id.editText);
        Button btn = findViewById(R.id.button);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone = ph.getText().toString();
                String message = ms.getText().toString();
                if(!ph.equals("") && !ms.equals("")) {
                    Uri u = Uri.parse("smsto: " + phone);
                    Intent i = new Intent(Intent.ACTION_SENDTO, u);
                    i.putExtra("sms_body", " " + message);
                    startActivity(i);
                }
                else
                    Toast.makeText(getApplicationContext(),"Please Enter Phone Number & Message", Toast.LENGTH_LONG).show();
            }
        });
    }
}