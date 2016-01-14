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
import xyz.dokup.slidecontroller.model.Event;

/**
 * Created by e10dokup on 2016/01/05
 **/
public class EventFragment extends BaseFragment {
    private static final String TAG = EventFragment.class.getSimpleName();
    private final EventFragment self = this;

    @Inject
    ConfManagerHelper mHelper;

    @Bind(R.id.card_linear)
    LinearLayout mCardLinearLayout;

    private List<Event> mEvents;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        { // Inject values
            MyApplication app = (MyApplication) getBaseActivity().getApplication();
            app.getComponent().inject(this);
        }

        requestEvents();

        ButterKnife.bind(this, view);

        return view;
    }

    private void requestEvents() {
        mHelper.getEventListFromApi(this);
    }

    public void onResultResponse(List<Event> events) {
        mEvents = events;
        mCardLinearLayout.removeAllViews();

        for(int i = 0; i<events.size(); i++) {
            Event event = events.get(i);
            createCardView(event, i);

        }
    }

    private void createCardView(Event event, int i) {
        LayoutInflater inflater = (LayoutInflater) getBaseActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout linearLayout = (LinearLayout) inflater.inflate(R.layout.card_event, null);
        CardView cardView = (CardView) linearLayout.findViewById(R.id.card_event);
        TextView titleText = (TextView) linearLayout.findViewById(R.id.text_title_event);
        TextView placeText = (TextView) linearLayout.findViewById(R.id.text_place_event);
        TextView betweenText = (TextView) linearLayout.findViewById(R.id.text_between);
        titleText.setText(event.getName());
        placeText.setText(event.getPlace());
        betweenText.setText(event.getStart() + " - " + event.getEnd());
        cardView.setTag(i);
        cardView.setOnClickListener(mCardClickListener);
        mCardLinearLayout.addView(linearLayout, i);
    }

    View.OnClickListener mCardClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int index = (int) view.getTag();
            mHelper.setSelectedEvent(mEvents.get(index));
            replaceFragment(new TimetableFragment(), true);
        }
    };
}