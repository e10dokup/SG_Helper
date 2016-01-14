package xyz.dokup.slidecontroller.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.BootstrapButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import xyz.dokup.slidecontroller.R;
import xyz.dokup.slidecontroller.core.BaseFragment;
import xyz.dokup.slidecontroller.core.MyApplication;
import xyz.dokup.slidecontroller.helper.ConfManagerHelper;
import xyz.dokup.slidecontroller.model.Event;
import xyz.dokup.slidecontroller.model.Talk;
import xyz.dokup.slidecontroller.model.Timetable;

/**
 * Created by e10dokup on 2016/01/06
 **/
public class DetailFragment extends BaseFragment {
    private static final String TAG = DetailFragment.class.getSimpleName();
    private final DetailFragment self = this;

    @Inject
    ConfManagerHelper mHelper;

    @Bind(R.id.text_name_detail)
    TextView mNameText;
    @Bind(R.id.text_speaker_detail)
    TextView mSpeakerText;
    @Bind(R.id.text_room_detail)
    TextView mRoomText;
    @Bind(R.id.text_start_detail)
    TextView mStartText;
    @Bind(R.id.text_label_description_detail)
    TextView mDescriptionLabel;
    @Bind(R.id.text_description_detail)
    TextView mDescriptionText;
    @Bind(R.id.btn_add_calendar)
    BootstrapButton mAddCalendarButton;

    private Event mEvent;
    private Timetable mTimetable;
    private Talk mTalk;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);

        { // Inject values
            MyApplication app = (MyApplication) getBaseActivity().getApplication();
            app.getComponent().inject(this);
        }

        mEvent = mHelper.getSelectedEvent();
        mTimetable = mHelper.getSelectedTimetable();
        mTalk = mHelper.getSelectedTalk();

        ButterKnife.bind(this, view);

        mNameText.setText(mTalk.getName());
        mSpeakerText.setText(mTalk.getSpeaker());
        mRoomText.setText(mTalk.getRoom());
        mStartText.setText(mTalk.getStart());
        if(mTalk.getDescription() != "") {
            mDescriptionText.setText(mTalk.getDescription());
        } else {
            mDescriptionLabel.setVisibility(View.GONE);
            mDescriptionText.setVisibility(View.GONE);
        }


        return view;
    }

    @OnClick(R.id.btn_add_calendar)
    public void onClickAddCalendarButton() {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try{
            Date startDate = fmt.parse(mTalk.getStart());
            Intent intent = new Intent(Intent.ACTION_INSERT, CalendarContract.Events.CONTENT_URI);
            //スケジュールのタイトル
            intent.putExtra(CalendarContract.Events.TITLE, mTalk.getName());
            //スケジュールの開始時刻 ゼロで現在時刻
            intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, startDate.getTime());
            //スケジュールの場所
            intent.putExtra(CalendarContract.Events.EVENT_LOCATION, mEvent.getPlace() + " " + mTalk.getRoom());
            //スケジュールの詳細内容
            intent.putExtra(CalendarContract.Events.DESCRIPTION, mTalk.getDescription());
            //Intentを呼び出す
            startActivity(intent);
        } catch(ParseException e) {
            e.printStackTrace();
            Snackbar.make(mAddCalendarButton, "時間の取得に失敗しました", Snackbar.LENGTH_SHORT).show();
        }


    }
}