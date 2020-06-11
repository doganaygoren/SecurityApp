package com.example.security;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class EventLogActivity extends AppCompatActivity {

    LinearLayout eventActivity;
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
        eventActivity=(LinearLayout) findViewById(R.id.eventActivity);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.delete_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.goToSensors:
                Intent forward= new Intent(EventLogActivity.this, StatusActivity.class);
                startActivity(forward);
                break;
            case R.id.menuDeleteAll:
                if(eventList.size()==0){
                    // If there is no data currently
                    Toast.makeText(this,"There is no record to delete.",Toast.LENGTH_LONG).show();
                    break;
                }
                databaseHelper.deleteAllEvents();
                eventListAdapter.notifyDataSetChanged();
                finish();
                overridePendingTransition(0, 0);
                startActivity(getIntent());
                overridePendingTransition(0, 0);
                Toast.makeText(this,"All event logs are cleared.",Toast.LENGTH_LONG).show();
                break;

        }
        return super.onOptionsItemSelected(item);
    }
}