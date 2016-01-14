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
import xyz.dokup.slidecontroller.model.Talk;

/**
 * Created by e10dokup on 2016/01/05
 **/
public class TalkFragment extends BaseFragment {
    private static final String TAG = TalkFragment.class.getSimpleName();
    private final TalkFragment self = this;

    @Inject
    ConfManagerHelper mHelper;

    @Bind(R.id.card_linear)
    LinearLayout mCardLinearLayout;

    private List<Talk> mTalks;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        { // Inject values
            MyApplication app = (MyApplication) getBaseActivity().getApplication();
            app.getComponent().inject(this);
        }

        requestTalks();

        ButterKnife.bind(this, view);

        return view;
    }

    private void requestTalks() {
        mHelper.getTalkListFromApi(this);
    }

    public void onResultResponse(List<Talk> Talks) {
        mTalks = Talks;
        mCardLinearLayout.removeAllViews();

        for(int i = 0; i<Talks.size(); i++) {
            Talk talk = Talks.get(i);
            createCardView(talk, i);

        }
    }

    private void createCardView(Talk talk, int i) {
        LayoutInflater inflater = (LayoutInflater) getBaseActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout linearLayout = (LinearLayout) inflater.inflate(R.layout.card_talk, null);
        CardView cardView = (CardView) linearLayout.findViewById(R.id.card_talk);
        TextView nameText = (TextView) linearLayout.findViewById(R.id.text_name_talk);
        TextView speakerText = (TextView) linearLayout.findViewById(R.id.text_speaker_talk);
        TextView roomText = (TextView) linearLayout.findViewById(R.id.text_room_talk);
        TextView startText = (TextView) linearLayout.findViewById(R.id.text_start_talk);
        nameText.setText(talk.getName());
        speakerText.setText(talk.getSpeaker());
        roomText.setText(talk.getRoom());
        startText.setText(talk.getStart());
        cardView.setTag(i);
        cardView.setOnClickListener(mCardClickListener);
        mCardLinearLayout.addView(linearLayout, i);
    }

    View.OnClickListener mCardClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int index = (int) view.getTag();
            mHelper.setSelectedTalk(mTalks.get(index));
            replaceFragment(new DetailFragment(), true);
        }
    };
}