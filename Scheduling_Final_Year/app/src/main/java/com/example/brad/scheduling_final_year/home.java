package com.example.brad.scheduling_final_year;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.content.Intent;

import java.util.ArrayList;
import java.util.Calendar;


public class home extends Fragment  {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static DashboardAdapter e;

    DB database;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ListView Meetings;
    private ListView TodaysEvents;
    private ListView Appointments;
    private ListView Hobbies;
    private ListView Other;
    private ListView Deadlines;
    private ListView Countdown;
    private Button addMeeting;
    private BroadcastReceiver deadlineMinutelyUpdate;
    private Parcelable listViewScrollState;


    /*    private OnFragmentInteractionListener mListener;*/

    public home() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment home.
     */
    // TODO: Rename and change types and number of parameters
    public static home newInstance(String param1, String param2) {
        home fragment = new home();
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

        database = DB.getInstance(getActivity());


    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Meetings = (ListView) getView().findViewById(R.id.meetings);
        TodaysEvents = (ListView) getView().findViewById(R.id.todays);
        Appointments = (ListView) getView().findViewById(R.id.appointments);
        Deadlines = (ListView) getView().findViewById(R.id.deadlines);
        Hobbies = (ListView) getView().findViewById(R.id.hobbies);
        Other = (ListView) getView().findViewById(R.id.other);
        addMeeting = (Button) getView().findViewById(R.id.addmeeting);
        addMeeting = (Button) getView().findViewById(R.id.addmeeting);
        Countdown = (ListView) getView().findViewById(R.id.countdown);


        fillDeadlines();
        fillHobbies();
        fillMeetings();
        fillOthers();
        fillAppointments();
        fillTodays();
        testCounterDateFormat();

        TodaysEvents.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent appInfo = new Intent(getActivity(), todaysActivity.class);
                startActivity(appInfo);
            }
        });
        Meetings.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent appInfo = new Intent(getActivity(), meetingsActivity.class);
                startActivity(appInfo);
            }
        });
        Appointments.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent appInfo = new Intent(getActivity(), AppointmentsActivity.class);
                startActivity(appInfo);
            }
        });
        Deadlines.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent appInfo = new Intent(getActivity(), DeadlinesActivity.class);
                startActivity(appInfo);
            }
        });
        Hobbies.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent appInfo = new Intent(getActivity(), HobbiesActivity.class);
                startActivity(appInfo);
            }
        });
        Other.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent appInfo = new Intent(getActivity(), OtherActivity.class);
                startActivity(appInfo);
            }
        });
        addMeeting.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), addMeetingPopUp.class);
                startActivity(intent);
            }
        });

        Calendar cl = Calendar.getInstance();
        cl.set(Calendar.HOUR_OF_DAY, 16);
        cl.set(Calendar.MINUTE, 20);
        cl.set(Calendar.SECOND, 0);
    }

    public void fillMeetings() {
        ArrayList<EventSmall> list = selectMeetings();

        DashboardAdapter adapter = new DashboardAdapter(list, getActivity());
        Meetings.setAdapter(adapter);
    }

    public ArrayList<EventSmall> selectMeetings() {
        ArrayList<EventSmall> list = new ArrayList<>();
        Cursor getTodaysEvents = database.getMeetings();

        while(getTodaysEvents.moveToNext()) {
            String text = getTodaysEvents.getString(1);
            String date = getTodaysEvents.getString(4);
            String time = getTodaysEvents.getString(5);

            EventSmall e = new EventSmall(text, date, time);

            list.add(e);

        }
        getTodaysEvents.close();
        return list;
    }

    public void fillDeadlines() {
        ArrayList<EventSmall> list = selectDeadlines();

        DashboardAdapter adapter = new DashboardAdapter(list, getActivity());
        Deadlines.setAdapter(adapter);
    }

    public ArrayList<EventSmall> selectDeadlines() {
        ArrayList<EventSmall> list = new ArrayList<>();
        Cursor getDeadlines = database.getDeadlines();

        while(getDeadlines.moveToNext()) {
            String text = getDeadlines.getString(1);
            String date = getDeadlines.getString(4);
            String time = getDeadlines.getString(5);

            EventSmall e = new EventSmall(text, date, time);

            list.add(e);

        }
        getDeadlines.close();
        return list;
    }

    public void fillHobbies() {
        ArrayList<EventSmall> list = selectHobbies();

        DashboardAdapter adapter = new DashboardAdapter(list, getActivity());
        Hobbies.setAdapter(adapter);
    }

    public ArrayList<EventSmall> selectHobbies() {
        ArrayList<EventSmall> list = new ArrayList<>();
        Cursor getHobbies = database.getHobbies();

        while(getHobbies.moveToNext()) {
            String text = getHobbies.getString(1);
            String date = getHobbies.getString(4);
            String time = getHobbies.getString(5);

            EventSmall e = new EventSmall(text, date, time);

            list.add(e);

        }
        getHobbies.close();
        return list;
    }

    public void fillOthers() {
        ArrayList<EventSmall> list = selectOthers();

        DashboardAdapter adapter = new DashboardAdapter(list, getActivity());
        Other.setAdapter(adapter);
    }

    public ArrayList<EventSmall> selectOthers() {
        ArrayList<EventSmall> list = new ArrayList<>();
        Cursor getOthers = database.getOthers();

        while(getOthers.moveToNext()) {
            String text = getOthers.getString(1);
            String date = getOthers.getString(4);
            String time = getOthers.getString(5);

            EventSmall e = new EventSmall(text, date, time);

            list.add(e);
        }
        return list;
    }

    public void fillAppointments() {
        ArrayList<EventSmall> list = selectAppointments();

        DashboardAdapter adapter = new DashboardAdapter(list, getActivity());
        Appointments.setAdapter(adapter);
    }

    public ArrayList<EventSmall> selectAppointments() {
        ArrayList<EventSmall> list = new ArrayList<>();
        Cursor getAppointments = database.getAppointments();

        while(getAppointments.moveToNext()) {
            String text = getAppointments.getString(1);
            String date = getAppointments.getString(4);
            String time = getAppointments.getString(5);

            EventSmall e = new EventSmall(text, date, time);

            list.add(e);
        }
        return list;
    }

    public void fillTodays() {
        ArrayList<EventSmall> list = selectTodaysEvents();

        DashboardAdapter adapter = new DashboardAdapter(list, getActivity());
        TodaysEvents.setAdapter(adapter);
    }

    public ArrayList<EventSmall> selectTodaysEvents() {
        ArrayList<EventSmall> list = new ArrayList<>();
        Cursor getOthers = database.getTodaysEvents();

        while(getOthers.moveToNext()) {
            String text = getOthers.getString(1);
            String date = getOthers.getString(4);
            String time = getOthers.getString(5);

            EventSmall e = new EventSmall(text, date, time);

            list.add(e);
        }
        return list;
    }

    public ArrayList<Event> selectAll() {
        ArrayList<Event> list = new ArrayList<>();
        Cursor date2 = database.getDeadlines();

        while(date2.moveToNext()) {
            String name = date2.getString(1);
            String type = date2.getString(4);
            String desc = date2.getString(5);
            String date = date2.getString(2);
            String time = date2.getString(3);

            Event e = new Event(name, type, desc, date, time);

            list.add(e);
        }
        date2.close();
        return list;
    }

    public void testCounterDateFormat() {
        ListView listview = getView().findViewById(R.id.countdown);
        ArrayList<Event> list = selectAll();

        DeadlineCountdownAdapter adapter = new DeadlineCountdownAdapter(list, getActivity());
        listview.setAdapter(adapter);
    }

    public void change(View view)
    {
        Intent intent = new Intent(getActivity(), addMeetingPopUp.class);
        startActivity(intent);
    }

    public void MinuteUpdater() {
        final ListView listview = getView().findViewById(R.id.countdown);
        ArrayList<Event> list = selectAll();
        final DeadlineCountdownAdapter adapter = new DeadlineCountdownAdapter(list, getActivity());
        listview.setFocusable(false);

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_TIME_TICK);

        deadlineMinutelyUpdate = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                int index = listview.getFirstVisiblePosition();
                View v = listview.getChildAt(0);

                if (adapter!=null) {
                    if (adapter.getCount() > 0) {
                        int top = v.getTop() - listview.getPaddingTop();
                        listview.setAdapter(adapter);
                        listview.setSelectionFromTop(index, top);
                    }

                }
            }
        };
        getActivity().registerReceiver(deadlineMinutelyUpdate, intentFilter);
    }

    @Override
    public void onResume() {
        super.onResume();
        MinuteUpdater();
        fillDeadlines();
        fillHobbies();
        fillMeetings();
        fillOthers();
        fillAppointments();
        fillTodays();
        testCounterDateFormat();

    }

    @Override
    public void onPause() {
        final ListView listview = getView().findViewById(R.id.countdown);
        super.onPause();
        getActivity().unregisterReceiver(deadlineMinutelyUpdate);
    }



}
