package com.example.uiclass025;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.sql.Time;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    EditText et;
    DatePickerDialog dpd;
    EditText et1;
    TimePickerDialog tpd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et = findViewById(R.id.editText);
        Calendar c = Calendar.getInstance();
        int y = c.get(Calendar.YEAR);
        int m = c.get(Calendar.MONTH);
        int d = c.get(Calendar.DAY_OF_MONTH);
        dpd = new DatePickerDialog(MainActivity.this, this, y, m, d);
        dpd.setCancelable(true);
        et.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dpd.show();
            }
        });

        et1 =  findViewById(R.id.editText1);
        int h = c.get(Calendar.HOUR_OF_DAY);
        int min = c.get(Calendar.MINUTE);
        tpd = new TimePickerDialog(MainActivity.this, this, h, min, true);
        tpd.setCancelable(true);
        et.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tpd.show();
            }
        });

    }
        @Override
        public void onDateSet(DatePicker view,int year, int month,int day){
            month++;
            String date =  day + "/" + month + "/" + year;
            et.setText(date);
        }

    @Override
    public void onTimeSet(TimePicker view, int hour, int minute){
        String date =  hour + ":" + minute;
        et1.setText(date);
    }
    }
