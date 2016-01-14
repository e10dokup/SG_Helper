package xyz.dokup.slidecontroller.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import xyz.dokup.slidecontroller.R;
import xyz.dokup.slidecontroller.core.BaseFragment;
import xyz.dokup.slidecontroller.core.MyApplication;
import xyz.dokup.slidecontroller.helper.ConfManagerHelper;
import xyz.dokup.slidecontroller.model.Timetable;

/**
 * Created by e10dokup on 2016/01/05
 **/
public class TimetableFragment extends BaseFragment {
    private static final String TAG = TimetableFragment.class.getSimpleName();
    private final TimetableFragment self = this;

    @Inject
    ConfManagerHelper mHelper;

    @Bind(R.id.card_linear)
    LinearLayout mCardLinearLayout;

    private List<Timetable> mTimetables;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        { // Inject values
            MyApplication app = (MyApplication) getBaseActivity().getApplication();
            app.getComponent().inject(this);
        }

        requestTimetables();

        ButterKnife.bind(this, view);

        return view;
    }

    private void requestTimetables() {
        mHelper.getTimetableListFromApi(this);
    }

    public void onResultResponse(List<Timetable> timetables) {
        mTimetables = timetables;
        mCardLinearLayout.removeAllViews();

        for(int i = 0; i<timetables.size(); i++) {
            Timetable timetable = timetables.get(i);
            createCardView(timetable, i);

        }
    }

    private void createCardView(Timetable timetable, int i) {
        LayoutInflater inflater = (LayoutInflater) getBaseActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout linearLayout = (LinearLayout) inflater.inflate(R.layout.card_timetable, null);
        CardView cardView = (CardView) linearLayout.findViewById(R.id.card_timetable);
        TextView nameText = (TextView) linearLayout.findViewById(R.id.text_name_timetable);
        TextView startText = (TextView) linearLayout.findViewById(R.id.text_start_timetable);
        TextView endText = (TextView) linearLayout.findViewById(R.id.text_end_timetable);
        nameText.setText(timetable.getName());
        startText.setText(timetable.getStart());
        endText.setText(timetable.getEnd());
        cardView.setTag(i);
        cardView.setOnClickListener(mCardClickListener);
        mCardLinearLayout.addView(linearLayout, i);
    }

    View.OnClickListener mCardClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int index = (int) view.getTag();
            mHelper.setSelectedTimetable(mTimetables.get(index));
            replaceFragment(new TalkFragment(), true);
        }
    };
}