package com.example.myapplication2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*EditText txt = findViewById(R.id.txtDemo);*/
        EditText user = findViewById(R.id.txtUser);
        EditText pass = findViewById(R.id.txtPass);
        Button btn = findViewById(R.id.btnDemo);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "You Have Logged In",Toast.LENGTH_LONG).show();
                user.setText("Wrong UserName");
            }
        });
        user.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Toast.makeText(getApplicationContext(),charSequence,Toast.LENGTH_LONG).show();

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
}