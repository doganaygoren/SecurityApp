package com.example.security;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class EventLogActivity extends AppCompatActivity {

    ListView lvEvents;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_log);
        lvEvents=findViewById(R.id.lvEvents);
        databaseHelper=new DatabaseHelper(EventLogActivity.this);
        ArrayList<String> eventList=databaseHelper.getAllEvents();
        lvEvents.setAdapter(new EventListAdapter(this,R.layout.event_list_view,eventList));
    }



    //*************************************************************** Custom Listview Settings ********************************************************************
    private class EventListAdapter extends ArrayAdapter<String>{
        private int layout;
        private EventListAdapter(@NonNull Context context, int resource, @NonNull List<String> objects) {
            super(context, resource, objects);
            layout=resource;
        }
        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            ViewHolder mainViewHolder=null;
            if(convertView==null){
                LayoutInflater inflater=LayoutInflater.from(getContext());
                convertView= inflater.inflate(layout, parent, false);
                ViewHolder viewHolder= new ViewHolder();
                viewHolder.tvEvent= (TextView) convertView.findViewById(R.id.tvEvent);
                viewHolder.tvDescription=(TextView) convertView.findViewById(R.id.tvDescription);
                viewHolder.imageBtnDelete=(ImageButton) convertView.findViewById(R.id.imageBtnDelete);
                convertView.setTag(viewHolder);
            }
            else{
                mainViewHolder=(ViewHolder) convertView.getTag();
                mainViewHolder.tvEvent.setText(getItem(position));
                mainViewHolder.tvDescription.setText(getItem(position));
            }
            return convertView;
        }
    }

    public class ViewHolder{

        TextView tvEvent, tvDescription;
        ImageButton imageBtnDelete;
    }
}
