package xyz.dokup.slidecontroller.helper;

import android.content.Context;
import android.util.Log;

import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;
import xyz.dokup.slidecontroller.R;
import xyz.dokup.slidecontroller.fragment.EventFragment;
import xyz.dokup.slidecontroller.fragment.TalkFragment;
import xyz.dokup.slidecontroller.fragment.TimetableFragment;
import xyz.dokup.slidecontroller.model.Event;
import xyz.dokup.slidecontroller.model.Talk;
import xyz.dokup.slidecontroller.model.Timetable;
import xyz.dokup.slidecontroller.service.ConfManagerService;

/**
 * Created by e10dokup on 2016/01/05
 **/
public class ConfManagerHelper {
    private static final String TAG = ConfManagerHelper.class.getSimpleName();
    private final ConfManagerHelper self = this;

    private Context mContext;
    private Event mSelectedEvent;
    private Timetable mSelectedTimetable;
    private Talk mSelectedTalk;

    public ConfManagerHelper(Context context) {
        mContext = context;
    }

    public Event getSelectedEvent() {
        return mSelectedEvent;
    }

    public void setSelectedEvent(Event selectedEvent) {
        mSelectedEvent = selectedEvent;
    }

    public Timetable getSelectedTimetable() {
        return mSelectedTimetable;
    }

    public void setSelectedTimetable(Timetable selectedTimetable) {
        mSelectedTimetable = selectedTimetable;
    }

    public Talk getSelectedTalk() {
        return mSelectedTalk;
    }

    public void setSelectedTalk(Talk selectedTalk) {
        mSelectedTalk = selectedTalk;
    }

    public void getEventListFromApi(final EventFragment fragment) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(mContext.getString(R.string.url_api))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ConfManagerService service = retrofit.create(ConfManagerService.class);

        Call<List<Event>> call = service.listEvents();
        call.enqueue(new Callback<List<Event>>() {
            @Override
            public void onResponse(Response<List<Event>> response, Retrofit retrofit) {
                if(response.isSuccess()) {
                    fragment.onResultResponse(response.body());
                } else {
                    Log.d(TAG, response.message());
                }
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void getTimetableListFromApi(final TimetableFragment fragment) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(mContext.getString(R.string.url_api))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ConfManagerService service = retrofit.create(ConfManagerService.class);

        Call<List<Timetable>> call = service.listTimetables(String.valueOf(mSelectedEvent.getId()));
        call.enqueue(new Callback<List<Timetable>>() {
            @Override
            public void onResponse(Response<List<Timetable>> response, Retrofit retrofit) {
                if(response.isSuccess()) {
                    fragment.onResultResponse(response.body());
                } else {
                    Log.d(TAG, response.message());
                }
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void getTalkListFromApi(final TalkFragment fragment) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(mContext.getString(R.string.url_api))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ConfManagerService service = retrofit.create(ConfManagerService.class);

        Call<List<Talk>> call = service.listTalks(String.valueOf(mSelectedEvent.getId()),
                String.valueOf(mSelectedTimetable.getId()));
        call.enqueue(new Callback<List<Talk>>() {
            @Override
            public void onResponse(Response<List<Talk>> response, Retrofit retrofit) {
                if(response.isSuccess()) {
                    fragment.onResultResponse(response.body());
                } else {
                    Log.d(TAG, response.message());
                }
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
            }
        });
    }
}