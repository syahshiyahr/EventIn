package com.example.eventinapp.data;

import com.example.eventinapp.model.Company;
import com.example.eventinapp.model.Event;

import java.util.ArrayList;

public class EventData {
    public static String[] eventName = {
            "Hology 3.0",
            "Filafest"
    };

    public static ArrayList<Event> getListData() {
        ArrayList<Event> list = new ArrayList<>();
        for (int position = 0; position < eventName.length; position++) {
            Event event = new Event();
            event.setEventName(eventName[position]);
            list.add(event);
        }
        return list;
    }
}
