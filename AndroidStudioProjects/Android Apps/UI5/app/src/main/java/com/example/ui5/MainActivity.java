package com.example.ui5;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView textView = findViewById(R.id.textView);
        registerForContextMenu(textView);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuinfo){
        super.onCreateContextMenu(menu, v, menuinfo);
        menu.setHeaderTitle("select the option");
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu,menu);
    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.item_1:
                Toast.makeText(getApplicationContext(),"Item 1 is selected", Toast.LENGTH_SHORT).show();
                break;
            case R.id.item_2:
                Toast.makeText(getApplicationContext(),"Item 2 is selected", Toast.LENGTH_SHORT).show();
                break;
            case R.id.item_3:
                Toast.makeText(getApplicationContext(),"Item 3 is selected", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.item_1:
                Toast.makeText(getApplicationContext(),"Item 1 is selected", Toast.LENGTH_SHORT).show();
                break;
            case R.id.item_2:
                Toast.makeText(getApplicationContext(),"Item 2 is selected", Toast.LENGTH_SHORT).show();
                break;
            case R.id.item_3:
                Toast.makeText(getApplicationContext(),"Item 3 is selected", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onContextItemSelected(item);
    }

}