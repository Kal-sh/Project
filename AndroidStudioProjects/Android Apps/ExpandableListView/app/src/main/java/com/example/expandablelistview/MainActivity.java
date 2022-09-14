package com.example.expandablelistview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    List<String> groupList;
    List<String> childList;
    Map<String, List<String>> countryCollection;
    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createGroupList();
        createCollection();
        expandableListView = findViewById(R.id.foreignCountries);
        expandableListAdapter = new MyExpendableListAdapter(this, groupList, countryCollection);
        expandableListView.setAdapter(expandableListAdapter);
        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            int lastExpandedPosition = -1;
            @Override
            public void onGroupExpand(int i) {
                if(lastExpandedPosition != -1 && i != lastExpandedPosition){
                    expandableListView.collapseGroup(lastExpandedPosition);
                }
                lastExpandedPosition = i;
            }
        });
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
                String selected = expandableListAdapter.getChild(i,i1).toString();
                Toast.makeText(getApplicationContext(),"Selected " + selected, Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }

    private void createCollection() {
        String[] ETCities = {"Addis Abeba", "Gonder", "Hawassa","Bahir Dar"};
        String[] EGCities = {"Cairo", "Alexandria", "Giza", "Aswan"};
        String[] AUCities = {"Sydney", "Melbourne", "Brisbane", "Adelaide"};
        String[] CHCities = {"Shanghai", "Guangzhou", "Dalian", "Yichang"};
        String[] USCities = {"New York", "Los Angeles", "Boston", "New Orleans"};
        String[] AECities = {"Dubai", "Abu Dhabi", "Doha", "Sharjah"};
        countryCollection = new HashMap<String,List<String>>();
        for(String group : groupList){
            if (group.equals("Ethiopia")){
                loadChild(ETCities);}
            else if (group.equals("Egypt"))
                loadChild(EGCities);
            else if (group.equals("Australia"))
                loadChild(AUCities);
            else if (group.equals("China"))
                loadChild(CHCities);
            else if (group.equals("USA"))
                loadChild(USCities);
            else
                loadChild(AECities);
            countryCollection.put(group,childList);
        }
    }

    private void loadChild(String[] Cities) {
        childList = new ArrayList<>();
        for (String city : Cities){
            childList.add(city);
        }
    }

    private void createGroupList() {
        groupList = new ArrayList<>();
        groupList.add("Ethiopia");
        groupList.add("Egypt");
        groupList.add("Australia");
        groupList.add("China");
        groupList.add("USA");
        groupList.add("UAE");
    }
}