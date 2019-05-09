package com.example.brad.scheduling_final_year;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class ViewEventDetailedPopUp extends AppCompatActivity {

    private TextView date;
    private TextView desc;
    private TextView type;
    private TextView name;
    private TextView time;

    DB database;
    String result;
    String result2;
    String result3;
    String result4;
    String result5;
    int RecordStringID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_detailed_popup);
        Intent i = getIntent();

        type = (TextView) findViewById(R.id.EventType);
        desc = (TextView) findViewById(R.id.EventDescription);
        date = (TextView) findViewById(R.id.EventDate);
        name = (TextView) findViewById(R.id.EventName);
        time = (TextView) findViewById(R.id.EventTime);
        Button b = (Button) findViewById(R.id.Edit);
        Button delete = (Button) findViewById(R.id.Delete);

        DisplayMetrics display = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(display);
        int w = display.widthPixels;
        int h = display.heightPixels;
        getWindow().setLayout((int) (w * .8), (int) (h * .5));

        database = new DB(this);

        final Bundle stuff = i.getExtras();
        if (stuff != null) {
            result = stuff.getString("name");
            result2 = stuff.getString("date");
            result3 = stuff.getString("time");
            result4 = stuff.getString("type");
            result5 = stuff.getString("description");
            RecordStringID = stuff.getInt("id");
        }

        type.setText(result4);
        desc.setText(result5);
        date.setText(result2);
        name.setText(result);
        time.setText(result3);

        System.out.println(RecordStringID);


        final Intent i2 = new Intent(this, editEventActivity.class);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle stuff2 = new Bundle();

                stuff2.putString("name", result);
                stuff2.putString("date", result2);
                stuff2.putString("time", result3);
                stuff2.putString("type", result4);
                stuff2.putInt("id", RecordStringID);
                stuff2.putString("description", result5);
                i2.putExtras(stuff2);

                startActivity(i2);
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database.deleteRecord2(RecordStringID);
                toastMessage("Event successfully deleted");
            }
        });
    }

    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }

}

