package com.example.brad.scheduling_final_year;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.view.View;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Locale;


public class addMeetingPopUp extends Activity  {

    Calendar date;
    DB database;



    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addmeeting);
        DisplayMetrics display = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(display);
        int w = display.widthPixels;
        int h = display.heightPixels;
        getWindow().setLayout((int) (w*.8),(int) (h*.8));
        database = new DB(this);
        TextView t = findViewById(R.id.textView5);

    }

    public void DateTimePicker(View v) {
        final TextView txt = findViewById(R.id.textView);
        final Calendar cDate = Calendar.getInstance();
        final String dateFormat = "yyyy-MM-dd";
        date = Calendar.getInstance();

        new DatePickerDialog(addMeetingPopUp.this, R.style.pickerTheme, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int sYear, int sMonth, int sDay) {
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat formatDate  = new SimpleDateFormat(dateFormat, Locale.ENGLISH);
                calendar.set(sYear, sMonth, sDay);

                txt.setText(formatDate.format(calendar.getTime()));
            }
        }, cDate.get(Calendar.YEAR), cDate.get(Calendar.MONTH), cDate.get(Calendar.DATE)).show();
    }

    public void TimePicker(View v){
        final TextView time = findViewById(R.id.Date);
        final Calendar cDate = Calendar.getInstance();

        new TimePickerDialog(addMeetingPopUp.this, R.style.pickerTheme, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hour, int minute) {
                time.setText(timeConverter(hour) + ":" + timeConverter(minute));
            }
        }, cDate.get(Calendar.HOUR_OF_DAY), cDate.get(Calendar.MINUTE), false).show();
    }

    public void insertToDatabase(View v){
        final TextView eventName = findViewById(R.id.eventnameentry);
        final TextView date = findViewById(R.id.textView);
        final TextView time = findViewById(R.id.Date);
        final Spinner eventType = findViewById(R.id.spinner);
        final TextView eventDescription = findViewById(R.id.eventdescriptionentry);
        String eName = eventName.getText().toString();
        String eDate = date.getText().toString();
        String eTime = time.getText().toString();
        String eType = eventType.getSelectedItem().toString();
        eType = eType.trim();
        String eDesc = eventDescription.getText().toString();
        InsertDateRecord(eName, eDate, eTime, eType, eDesc);
    }



    // Allows proper formatting for time input
    public String timeConverter(int time) {
        if (time > 10) {
            return Integer.toString(time);
        } else {
            return 0 + Integer.toString(time);
        }
    }

    public void InsertDateRecord(String EventName, String EventDate, String EventTime, String EventType, String EventDescription){
        final TextView date = findViewById(R.id.textView);
        final TextView time = findViewById(R.id.Date);
        if(!date.getText().toString().matches("") && !time.getText().toString().matches("")) {
            boolean insert = database.InsertNewRecord(EventName, EventDate, EventTime, EventType, EventDescription);

            if (insert) {
                toastMessage("Event successfully added");
            } else {
                toastMessage("Something went wrong");
            }
        } else {
            toastMessage("Make sure you have selected a date & time");
        }

    }

    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }

}
