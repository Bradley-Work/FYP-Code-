package com.example.brad.scheduling_final_year;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class EventAdapter extends BaseAdapter {

    private Context Context;
    private ArrayList<Event> Arraylist;

    public EventAdapter(ArrayList<Event> list, Context context) {
        this.Arraylist = list;
        this.Context = context;
    }

    @Override
    public int getCount() {
        return this.Arraylist.size();
    }

    @Override
    public Object getItem(int pos) {
        return this.Arraylist.get(pos);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }


    @Override
    public View getView(int pos, View convert, ViewGroup p) {
        ViewHolder holder = null;

        if (convert == null) {
            LayoutInflater inf = (LayoutInflater) Context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convert = inf.inflate(R.layout.adapter_view, null);

            holder = new ViewHolder();
            holder.text = (TextView) convert.findViewById(R.id.Name);
            holder.date = (TextView) convert.findViewById(R.id.Date);
            holder.time = (TextView) convert.findViewById(R.id.Time);
            holder.eventDesc = (TextView) convert.findViewById(R.id.EventDescription);
            holder.eventType = (TextView) convert.findViewById(R.id.EventType);

            convert.setTag(holder);
        } else {
            holder = (ViewHolder)convert.getTag();
        }

        Event e = Arraylist.get(pos);
        holder.text.setText(e.getDBText());
        holder.date.setText(e.getDBDate());
        holder.time.setText(e.getDBTime());
        holder.eventType.setText(e.getDBType());
        holder.eventDesc.setText(e.getDBDesc());

        return convert;
    }

    private static class ViewHolder{
        public TextView text;
        public TextView date;
        public TextView time;
        public TextView eventDesc;
        public TextView eventType;
    }
}
