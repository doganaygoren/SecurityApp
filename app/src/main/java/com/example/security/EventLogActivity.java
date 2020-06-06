package com.example.security;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class EventLogActivity extends AppCompatActivity {

    ListView lvEvents;
    DatabaseHelper databaseHelper;
    ArrayList<Event> eventList;
    EventListAdapter eventListAdapter=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_log);

        databaseHelper= new DatabaseHelper(EventLogActivity.this);

        lvEvents=findViewById(R.id.lvEvents);
        eventList=new ArrayList<>();
        eventListAdapter=new EventListAdapter(this,R.layout.event_list_view,eventList);
        lvEvents.setAdapter(eventListAdapter);
        Cursor cursor= databaseHelper.getEvents();
        eventList.clear();
        while (cursor.moveToNext()){

            int id=cursor.getInt(0);
            String type=cursor.getString(1);
            String description= cursor.getString(2);
            eventList.add(new Event(id,type,description));
        }
        eventListAdapter.notifyDataSetChanged();
        if(eventList.size()==0){
            // If there is no data currently
            Toast.makeText(this,"There is no current event record",Toast.LENGTH_LONG).show();
        }
    }



}