package com.example.db1;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText editTextId, editTextName, editTextNum;
    private String name, ID;
    private int number;
    private dbHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new dbHelper(this);
        editTextId = findViewById(R.id.editText1);
        editTextName = findViewById(R.id.editText2);
        editTextNum = findViewById(R.id.editText3);
        Button btnSave = findViewById(R.id.button);
        Button btnRead = findViewById(R.id.button2);
        Button btnUpdate = findViewById(R.id.button3);
        Button btnDelete = findViewById(R.id.button4);
        Button btnSearch = findViewById(R.id.button5);
        Button btnDeleteAll = findViewById(R.id.button6);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = editTextName.getText().toString();
                String num = editTextNum.getText().toString();
                if(name.isEmpty() || num.isEmpty()){
                    Toast.makeText(MainActivity.this, "Empty fields are not allowed", Toast.LENGTH_SHORT).show();
                }
                else{
                    number = Integer.parseInt(num);
                    try{
                        db.insertData(name, number);
                        editTextId.getText().clear();
                        editTextName.getText().clear();
                        editTextNum.getText().clear();
                    }
                    catch(Exception e){
                        e.printStackTrace();
                    }
                }
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.deleteData(name);
                Toast.makeText(MainActivity.this, "Deleted the Name", Toast.LENGTH_SHORT).show();
            }
        });

        btnDeleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.deleteAll();
                Toast.makeText(MainActivity.this,"DeleteD All Records", Toast.LENGTH_SHORT).show();
            }
        });

        btnRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1);
                String name, num, id;
                try{
                        Cursor cursor = db.readData();
                        if(cursor != null && cursor.getCount() > 0){
                            while(cursor.moveToNext()){
                                id = cursor.getString(0);
                                name = cursor.getString(1);
                                num = cursor.getString(2);
                                adapter.add("ID: " + id + "\n" + "Name: " + name + "\n" + "Number: " + num + "\n\n");

                            }
                }else{
                            adapter.add("No Data");
                }cursor.close();
            }catch(Exception e){
                    e.printStackTrace();
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("SQLite saved data");
                builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ID = editTextId.getText().toString();
                if(ID.isEmpty())
                    Toast.makeText(MainActivity.this, "Please Enter the ID", Toast.LENGTH_SHORT).show();
                else{
                    try{
                        Cursor cursor = db.searchData(ID);
                        if(cursor.moveToFirst()){
                            editTextName.setText(cursor.getString(1));
                            editTextNum.setText(cursor.getString(2));
                            Toast.makeText(MainActivity.this, "Data Found", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(MainActivity.this, "ID not found", Toast.LENGTH_SHORT).show();
                            editTextName.setText("ID not found");
                            editTextNum.setText("ID not found");
                        }
                        cursor.close();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = editTextName.getText().toString();
                String num = editTextNum.getText().toString();
                ID = editTextId.getText().toString();
                if(name.isEmpty() || num.isEmpty() || ID.isEmpty())
                    Toast.makeText(MainActivity.this, "Empty field(s) not allowed", Toast.LENGTH_SHORT).show();
                else{
                    number = Integer.parseInt(num);
                    try{
                        db.updateData(ID, name, num);
                        editTextId.getText().clear();
                        editTextName.getText().clear();
                        editTextNum.getText().clear();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}