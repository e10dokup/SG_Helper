package xyz.dokup.slidecontroller.service;

import java.util.List;

import xyz.dokup.slidecontroller.model.Event;
import xyz.dokup.slidecontroller.model.Talk;
import xyz.dokup.slidecontroller.model.Timetable;
import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by e10dokup on 2016/01/05
 **/
public interface ConfManagerService {

    @GET("api/v1/")
    Call<List<Event>> listEvents();

    @GET("api/v1/{event_id}")
    Call<List<Timetable>> listTimetables(@Path("event_id") String eventId);

    @GET("api/v1/{event_id}/{timetable_id}")
    Call<List<Talk>> listTalks(@Path("event_id") String eventId, @Path("timetable_id") String timetableId);

}