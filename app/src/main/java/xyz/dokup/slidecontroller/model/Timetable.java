package xyz.dokup.slidecontroller.model;

/**
 * Created by e10dokup on 2016/01/05
 **/
public class Timetable {
    private static final String TAG = Timetable.class.getSimpleName();
    private final Timetable self = this;

    private int id, eventId;
    private String name, despription, start, end;

    public Timetable(int id, int eventId, String name, String despription, String start, String end) {
        this.id = id;
        this.eventId = eventId;
        this.name = name;
        this.despription = despription;
        this.start = start;
        this.end = end;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDespription() {
        return despription;
    }

    public void setDespription(String despription) {
        this.despription = despription;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }
}