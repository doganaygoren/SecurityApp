package com.example.security;

import android.content.Context;
import android.graphics.ColorSpace;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class EventListAdapter extends BaseAdapter {

    private int layout;
    private Context context;
    private ArrayList<Event> eventList;
    public EventListAdapter(@NonNull Context context, int layout, @NonNull ArrayList<Event> eventList) {

        this.context=context;
        this.layout=layout;
        this.eventList=eventList;

    }


    @Override
    public int getCount() {
        return eventList.size();
    }

    @Override
    public Object getItem(int position) {
        return eventList.get(position);
    }

    @Override
    public long getItemId(int i){
        return i;
    }

    private class ViewHolder{

        TextView tvEvent, tvDescription;
        ImageButton imageBtnDelete;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup){

        View row= convertView;
        ViewHolder viewHolder=new ViewHolder();
        if(row==null){

            LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            //LayoutInflater inflater=LayoutInflater.from(getContext());
            //row=inflater.inflate(layout,null);
            row= inflater.inflate(layout, viewGroup, false);
            viewHolder.tvEvent= row.findViewById(R.id.tvEvent);
            viewHolder.tvDescription= row.findViewById(R.id.tvDescription);
            viewHolder.imageBtnDelete=row.findViewById(R.id.imageBtnDelete);
            row.setTag(viewHolder);
        }
        else{
            viewHolder=(ViewHolder) row.getTag();
        }

        Event event= eventList.get(position);
        viewHolder.tvEvent.setText(event.getType());
        viewHolder.tvDescription.setText((event.getDescription()));

        return row;
    }


}
