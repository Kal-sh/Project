package com.example.app1;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

//import androidx.appCompat.app.AppCompatActivity;
import android.app.Activity;
import android.widget.Toast;

public class MainActivity extends Activity {
    TextView label;
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        label =  findViewById(R.id.textView);
        Toast.makeText(getApplicationContext(),label.getText().toString(),Toast.LENGTH_LONG).show();
        label.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                lable.setText("Text View Clicked");
            }
        });
    }
}