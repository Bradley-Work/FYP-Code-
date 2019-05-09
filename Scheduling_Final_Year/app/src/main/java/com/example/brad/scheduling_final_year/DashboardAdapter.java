package com.example.brad.scheduling_final_year;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class DashboardAdapter extends BaseAdapter {

    private Context Context;
    private ArrayList<EventSmall> Arraylist;

    public DashboardAdapter(ArrayList<EventSmall> list, Context context) {
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
        DashboardAdapter.ViewHolder holder = null;

        if (convert == null) {
            LayoutInflater inf = (LayoutInflater) Context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convert = inf.inflate(R.layout.dashboard_adapter, null);

            holder = new DashboardAdapter.ViewHolder();
            holder.text = (TextView) convert.findViewById(R.id.Name);
            holder.date = (TextView) convert.findViewById(R.id.Date);
            holder.time = (TextView) convert.findViewById(R.id.Time);

            convert.setTag(holder);

        } else {
            holder = (DashboardAdapter.ViewHolder)convert.getTag();
        }

        EventSmall e = Arraylist.get(pos);
        holder.text.setText(e.getDBText());
        holder.date.setText(e.getDBDate());
        holder.time.setText(e.getDBTime());

        return convert;
    }

    private static class ViewHolder{
        public TextView text;
        public TextView date;
        public TextView time;
    }


}
