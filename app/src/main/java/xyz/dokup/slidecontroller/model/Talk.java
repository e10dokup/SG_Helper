package xyz.dokup.slidecontroller.model;

/**
 * Created by e10dokup on 2016/01/05
 **/
public class Talk {
    private static final String TAG = Talk.class.getSimpleName();
    private final Talk self = this;

    private int id, eventId, timetableId;
    private String name, speaker, description, room, start, end;

    public Talk(int id, int eventId, int timetableId, String name, String speaker, String description, String room, String start, String end) {
        this.id = id;
        this.eventId = eventId;
        this.timetableId = timetableId;
        this.name = name;
        this.speaker = speaker;
        this.description = description;
        this.room = room;
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

    public int getTimetableId() {
        return timetableId;
    }

    public void setTimetableId(int timetableId) {
        this.timetableId = timetableId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpeaker() {
        return speaker;
    }

    public void setSpeaker(String speaker) {
        this.speaker = speaker;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
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