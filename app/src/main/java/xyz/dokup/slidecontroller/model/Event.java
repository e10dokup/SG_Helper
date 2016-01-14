package xyz.dokup.slidecontroller.model;

/**
 * Created by e10dokup on 2016/01/05
 **/
public class Event {
    private static final String TAG = Event.class.getSimpleName();
    private final Event self = this;

    private int id;
    private String name, place, description, start, end;

    public Event(String name, int id, String place, String description, String start, String end) {
        this.name = name;
        this.id = id;
        this.place = place;
        this.description = description;
        this.start = start;
        this.end = end;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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