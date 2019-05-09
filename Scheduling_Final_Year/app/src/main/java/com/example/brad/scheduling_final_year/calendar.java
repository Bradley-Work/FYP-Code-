package com.example.brad.scheduling_final_year;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.Toast;


import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


/*
*
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link calendar.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link calendar#newInstance} factory method to
 * create an instance of this fragment.
*/
public class calendar extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    CalendarView widget;

    DB database;
    CaldroidFragment caldroidFragment;


    public calendar() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment calendar.
     */
    // TODO: Rename and change types and number of parameters
    public static calendar newInstance(String param1, String param2) {
        calendar fragment = new calendar();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        calendar();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_calendar, container, false);


    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        widget = (CalendarView) getView().findViewById(R.id.calendarView);
        fillContent();

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public ArrayList<String> selectAll() {
        ArrayList<String> list = new ArrayList<>();
        Cursor date2 = database.getDates();
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


    @Override
    public void onResume() {
        super.onResume();
        calendar();

    }

    public void fillContent() {
        ArrayList<String> list = selectAll();
        for (String e : list) {
            System.out.println(e);
        }
    }


    public void calendar(){
        database = DB.getInstance(getActivity());
        ColorDrawable bgColour = new ColorDrawable(Color.GRAY);
        ArrayList<String> list = selectAll();

        final String dateFormat = "yyyy-MM-dd";
        SimpleDateFormat myFormat = new SimpleDateFormat(dateFormat, Locale.ENGLISH);
        final SimpleDateFormat dateConvert = new SimpleDateFormat(dateFormat);

        caldroidFragment = new CaldroidFragment();

        Bundle args = new Bundle();
        Calendar cal = Calendar.getInstance();
        args.putInt(CaldroidFragment.MONTH, cal.get(Calendar.MONTH) + 1);
        args.putInt(CaldroidFragment.YEAR, cal.get(Calendar.YEAR));
        caldroidFragment.setArguments(args);

        try {
            for (String e : list) {
                caldroidFragment.setBackgroundDrawableForDate(bgColour, myFormat.parse(e));
            }
        } catch (ParseException e) {
            e.printStackTrace();
            System.out.println("A date format did not get passed through correctly");
        }

        FragmentTransaction t = getFragmentManager().beginTransaction();
        t.replace(R.id.calendarView, caldroidFragment);
        t.commit();

        final CaldroidListener listener = new CaldroidListener() {

            @Override
            public void onSelectDate(Date date, View view) {
                String selectedDate = dateConvert.format(date);
                System.out.println(selectedDate);
                Intent calendarDates = new Intent(getActivity(), CalendarEvents.class);
                Bundle dates = new Bundle();
                dates.putString("date", selectedDate);
                calendarDates.putExtras(dates);

                startActivity(calendarDates);
            }
        };

        caldroidFragment.setCaldroidListener(listener);
    }

}



