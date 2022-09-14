package com.example.intent5;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btn = findByViewId(R.id.btnshare);
        btn,setOnClickListener(new View.OnClickListener(){
            @Override
            Public void onClick(View view){
               Intent sharing = new Intent(Intent.ACTION_SEND);
               sharing.setType("text/plain");
               sharing.putExtra(Intent.EXTRA_SUBJECT, "Download Now");
               sharing.putExtra(Intent.EXTRA_TEXT, "http://www.url.com/flex...");
               startActivity(Intent.creatChooser(sharing, "share via"));
            }
        });
    }
}