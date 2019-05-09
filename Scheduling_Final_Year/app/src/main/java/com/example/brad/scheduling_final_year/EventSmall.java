package com.example.brad.scheduling_final_year;

public class EventSmall {
    private String text;
    private String date;
    private String time;


    public EventSmall(String text, String date, String time) {
        this.text = text;
        this.date = date;
        this.time = time;

    }

    public String getDBText() {
        return this.text;
    }

    public String getDBDate() {
        return this.date;
    }

    public String getDBTime() {
        return this.time;
    }


}
