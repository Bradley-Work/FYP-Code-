package com.example.brad.scheduling_final_year;

public class Event {
    private String text;
    private String date;
    private String time;
    private String type;
    private String desc;


    public Event(String text, String date, String time, String type, String desc ) {
        this.text = text;
        this.date = date;
        this.time = time;
        this.type = type;
        this.desc = desc;
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

    public String getDBType() {
        return this.type;
    }

    public String getDBDesc() {
        return this.desc;
    }

}
