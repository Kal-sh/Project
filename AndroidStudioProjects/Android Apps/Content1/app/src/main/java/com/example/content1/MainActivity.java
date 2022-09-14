package com.example.content1;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class MainActivity extends AppCompatActivity {
    private final String fileName = "ex.txt";
    private EditText et;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et = findViewById(R.id.editText);
        Button save = findViewById(R.id.btnSave);
        Button read = findViewById(R.id.btnRead);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = et.getText().toString();
                try{
                    FileOutputStream outputStream = openFileOutput(fileName, Context.MODE_PRIVATE);
                    outputStream.write(text.getBytes());
                    Toast.makeText(getApplicationContext(), "Data Saved in " + getFilesDir() + "/" + fileName, Toast.LENGTH_LONG).show();
                    outputStream.close();
                }
                catch (IOException ioe){
                    ioe.printStackTrace();
                }
            }
        });

        read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    FileInputStream inputStream = openFileInput(fileName);
                    InputStreamReader streamReader = new InputStreamReader(inputStream);
                    BufferedReader bufferedReader = new BufferedReader(streamReader);
                    StringBuilder stringBuilder = new StringBuilder();

                    String text;
                    while ((text = bufferedReader.readLine()) != null) {
                        stringBuilder.append(text);
                    }
                    inputStream.close();
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setCancelable(true);
                    builder.setTitle("Content From File ");
                    builder.setMessage(stringBuilder.toString());
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }
        });
    }
}