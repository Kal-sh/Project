package com.example.intent6;

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

        EditText recipient = findViewById(R.id.editRC);
        EditText cc = findViewById(R.id.editCC);
        EditText bcc = findViewById(R.id.editBCC);
        EditText subject = findViewById(R.id.editSub);
        EditText content = findViewById(R.id.editCon);

        Button btn = findViewById(R.id.button);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!recipient.equals("") && !content.equals("")){
                    Intent i = new Intent(Intent.ACTION_SENDTO);
                    i.putExtra(Intent.EXTRA_EMAIL, new String[]{recipient.getText().toString()});
                    i.putExtra(Intent.EXTRA_CC, new String[]{cc.getText().toString()});
                    i.putExtra(Intent.EXTRA_BCC, new String[]{bcc.getText().toString()});
                    i.putExtra(Intent.EXTRA_SUBJECT, subject.getText().toString());
                    i.putExtra(Intent.EXTRA_TEXT, content.getText().toString());
                    i.setData(Uri.parse("mailto:"));
                    startActivity(i);
                }
                else
                    Toast.makeText(getApplicationContext(), "Please Enter Recipient and Email", Toast.LENGTH_SHORT).show();
            }
        });
    }
}