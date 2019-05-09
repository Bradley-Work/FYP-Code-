package com.example.brad.scheduling_final_year;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class editEventActivity extends Activity  {

    Calendar date;
    DB database;
    String result;
    String result2;
    String result3;
    String result4;
    String result5;
    int RecordStringID;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editmeeting);
        DisplayMetrics display = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(display);
        int w = display.widthPixels;
        int h = display.heightPixels;
        getWindow().setLayout((int) (w*.8),(int) (h*.8));
        database = new DB(this);
        EditText eName = (EditText)findViewById(R.id.eventnameentry);
        Spinner eType = findViewById(R.id.spinner);
        EditText eDesc = findViewById(R.id.eventdescriptionentry);
        TextView eDate = findViewById(R.id.textView);
        TextView eTime = findViewById(R.id.Date);

        Intent i2 = getIntent();

        final Bundle stuff = i2.getExtras();
        if (stuff != null) {
            result = stuff.getString("name");
            result2 = stuff.getString("date");
            result3 = stuff.getString("time");
            result4 = stuff.getString("type");
            RecordStringID = stuff.getInt("id");
            result5 = stuff.getString("description");
        }

        eName.setText(result);


        eType.setSelection(getSpinnerIndex(eType, result4));

        eTime.setText(result3);
        eDesc.setText(result5);
        eDate.setText(result2);


    }

    public int getSpinnerIndex(Spinner s, String eType){
        for (int i = 0; i < s.getCount(); i++){
            if (s.getItemAtPosition(i).toString().equals(eType)){
                return i;
            }
        }
        return 0;
    }

    public void DateTimePicker(View v) {
        final TextView txt = findViewById(R.id.textView);
        final Calendar cDate = Calendar.getInstance();
        final String dateFormat = "yyyy-MM-dd";
        date = Calendar.getInstance();

        new DatePickerDialog(editEventActivity.this, R.style.pickerTheme, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int sYear, int sMonth, int sDay) {
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat formatDate  = new SimpleDateFormat(dateFormat, Locale.ENGLISH);
                calendar.set(sYear, sMonth, sDay);
                String record = formatDate.format(calendar.getTime());
                txt.setText(formatDate.format(calendar.getTime()));
            }
        }, cDate.get(Calendar.YEAR), cDate.get(Calendar.MONTH), cDate.get(Calendar.DATE)).show();
    }

    public void TimePicker(View v){
        final TextView time = findViewById(R.id.Date);
        final Calendar cDate = Calendar.getInstance();

        new TimePickerDialog(editEventActivity.this, R.style.pickerTheme, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hour, int minute) {
     //           InsertTimeRecord(timeConverter(hour) + ":" + timeConverter(minute));
                time.setText(timeConverter(hour) + ":" + timeConverter(minute));
            }
        }, cDate.get(Calendar.HOUR_OF_DAY), cDate.get(Calendar.MINUTE), false).show();
    }

    public void Update(View v){
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
            System.out.println(eType);
            String eDesc = eventDescription.getText().toString();

            EditRecord(eName, eDate, eTime, eType, eDesc);
    }



    // Allows proper formatting for time input
    public String timeConverter(int time) {
        if (time > 10) {
            return Integer.toString(time);
        } else {
            return 0 + Integer.toString(time);
        }
    }

    public void EditRecord(String EventName, String EventDate, String EventTime, String EventType, String EventDescription){
        boolean insert = database.EditRecord(RecordStringID, EventName, EventDate, EventTime, EventType, EventDescription);
        if (insert) {
            toastMessage("Event details has been successfully changed");
        } else{
            toastMessage("Something went wrong :(");
        }
    }

    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }


    // Deselects keyboard by touching off the keyboard area while on editText
    public void onClickKeyboardHide(View v){
        if(this.getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }



}
