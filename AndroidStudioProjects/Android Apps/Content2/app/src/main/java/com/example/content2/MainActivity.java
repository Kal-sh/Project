package com.example.content2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class MainActivity extends AppCompatActivity {

    private final String fileName = "ex.txt";
    String text;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editText);
        Button read = findViewById(R.id.btnRead);
        Button save = findViewById(R.id.btnSave);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text = editText.getText().toString();
                if( ContextCompat.checkSelfPermission(getApplicationContext(),WRITE_EXTERNAL_STORAGE)==
                        PackageManager.PERMISSION_GRANTED){
                    //permission is granted
                    saveData() ;
                }
                else{
                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[]{WRITE_EXTERNAL_STORAGE},1);
                }
            }
        });


        read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    File file = new File(Environment.getExternalStorageDirectory(), fileName);
                    FileInputStream inputStream = new FileInputStream(file);
                    InputStreamReader streamReader = new InputStreamReader(inputStream);
                    BufferedReader bufferedReader = new BufferedReader(streamReader);
                    StringBuilder stringBuilder = new StringBuilder();
                    String text;

                    while((text = bufferedReader.readLine()) != null){
                        stringBuilder.append(text + "\n");
                    }
                    inputStream.close();
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setCancelable(true);
                    builder.setIcon(R.drawable.alert_icon);
                    builder.setTitle("Input from External File");
                    builder.setMessage(stringBuilder.toString());
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
                catch (IOException ioe){
                    ioe.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode){
            case 1:
                if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    saveData();
                }
                else{
                    Toast.makeText(MainActivity.this, "You cant save file", Toast.LENGTH_SHORT).show();
                }
        }
        return;
    }

    private void saveData(){
        try{
            File file = new File(Environment.getExternalStorageDirectory(), fileName);
            FileOutputStream outputStream = new FileOutputStream(file);
            outputStream.write(text.getBytes());
            Toast.makeText(MainActivity.this, "Data Saved Successfully in " + getFilesDir() + "/" + fileName, Toast.LENGTH_LONG).show();
            outputStream.close();
        }
        catch (IOException ioe){
            ioe.printStackTrace();
        }
    }
}