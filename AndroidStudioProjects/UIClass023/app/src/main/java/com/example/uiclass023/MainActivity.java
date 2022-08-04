package com.example.uiclass023;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AutoCompleteTextView actv = findViewById(R.id.autoText);
        String[] text = {"Java", "C#", "Python", "kotlin", "php", "C/C++"};
        ArrayAdapter<String> a = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, text);
        actv.setAdapter(a);
    }
}