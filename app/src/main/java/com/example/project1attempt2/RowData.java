package com.example.project1attempt2;

public class RowData {

    private String task;
    private  int month;
    private  int year;
    private  int day;
    private  int hour;
    private  int minute;
    private int repeat;

    private String fileID;
    private boolean selected;

    RowData(){


    }

    public RowData(String task, String fileID, int month, int year, int day, int hour, int minute, int repeat, boolean selected) {
        this.task = task;
        this.month = month;
        this.year = year;
        this.day = day;
        this.fileID=fileID;
        this.hour = hour;
        this.minute = minute;
        this.repeat = repeat;
        this.selected = selected;
    }

    public String getTask() {
        return task;
    }


    public int getRepeat() {
        return repeat;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }



    public void setTask(String task) {
        this.task = task;
    }


    public void setRepeat(int repeat) {
        this.repeat = repeat;
    }

    public int getMonth() {
        return month;
    }
    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }


    public String getFileID() {
        return fileID;
    }

    public void setFileID(String fileID) {
        this.fileID = fileID;
    }
}
