package com.example.brad.scheduling_final_year;

import android.content.Context;
import android.database.Cursor;
import android.graphics.ColorSpace;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;
import org.joda.time.Period;
import org.joda.time.PeriodType;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
public class DeadlineCountdownAdapter extends BaseAdapter {

    private Context Context;
    private ArrayList<Event> Arraylist;
    public Date endDate;
    public Date endTime;
    public String tryTest;
    public Date completedDate;
    DB database;

    public DeadlineCountdownAdapter(ArrayList<Event> list, Context context) {
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
            convert = inf.inflate(R.layout.deadline_countdown_adapter, null);

            holder = new ViewHolder();
            holder.text = (TextView) convert.findViewById(R.id.Name);
            holder.date = (TextView) convert.findViewById(R.id.Date);
            holder.desc = (TextView) convert.findViewById(R.id.Desc);

            convert.setTag(holder);
        } else {
            holder = (ViewHolder)convert.getTag();
        }
        Event e = Arraylist.get(pos);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat timeFormatter = new SimpleDateFormat("h:mm");
        SimpleDateFormat complete = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            endDate = (Date)formatter.parse(e.getDBDate());
            endTime = (Date)timeFormatter.parse(e.getDBTime());
            tryTest = e.getDBDate() + " " + e.getDBTime();
            completedDate = complete.parse(tryTest);

        } catch (ParseException ee) {
            System.out.println(ee);
        }

        DateTime date = new DateTime(completedDate);
        DateTime date2 = DateTime.now();
        holder.text.setText(e.getDBText());
        holder.desc.setText(e.getDBDate());
        Period period = new Period(date2, date, PeriodType.dayTime());
        PeriodFormatter formatterComplete = new PeriodFormatterBuilder()
                .appendDays().appendSuffix(" day ", " days ")
                .appendHours().appendSuffix(" hour ", " hours ")
                .appendMinutes().appendSuffix(" minute ", " minutes ")
                .toFormatter();

        holder.date.setText(formatterComplete.print(period));
        System.out.println(formatterComplete.print(period));
        return convert;
    }


    private static class ViewHolder{
        public TextView text;
        public TextView date;
        public TextView desc;
    }


























    public ArrayList<String> selectAll() {
        ArrayList<String> list = new ArrayList<>();
        Cursor date2 = database.getDeadlineDates();
        if (date2.moveToFirst()) {
            do {
                String date = date2.getString(0);
                String e = date;

                list.add(e);
            } while (date2.moveToNext());
        } else {
            System.out.println("ArrayList is empty, no dates found :(");
        }
        date2.close();
        return list;
    }
}
