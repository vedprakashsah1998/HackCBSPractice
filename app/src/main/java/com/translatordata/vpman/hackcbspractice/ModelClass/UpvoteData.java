package com.translatordata.vpman.hackcbspractice.ModelClass;

public class UpvoteData
{
    private String date,eventName,eventAddress,time;

    public UpvoteData(String date, String eventName, String eventAddress, String time) {
        this.date = date;
        this.eventName = eventName;
        this.eventAddress = eventAddress;
        this.time = time;
    }

    public UpvoteData() {
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventAddress() {
        return eventAddress;
    }

    public void setEventAddress(String eventAddress) {
        this.eventAddress = eventAddress;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
