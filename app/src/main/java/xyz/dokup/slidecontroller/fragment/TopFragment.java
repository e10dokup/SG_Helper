package xyz.dokup.slidecontroller.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import xyz.dokup.slidecontroller.MainActivity;
import xyz.dokup.slidecontroller.R;
import xyz.dokup.slidecontroller.core.BaseFragment;
import xyz.dokup.slidecontroller.core.MyApplication;

/**
 * Created by e10dokup on 2016/01/05
 **/
public class TopFragment extends BaseFragment {
    private static final String TAG = TopFragment.class.getSimpleName();
    private final TopFragment self = this;

    @Bind(R.id.btn_conmane)
    ImageView mConfManagerButton;
    @Bind(R.id.btn_slcon)
    ImageView mSlconButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_top, container, false);

        { // Inject values
            MyApplication app = (MyApplication) getBaseActivity().getApplication();
            app.getComponent().inject(this);
        }

        ButterKnife.bind(this, view);
        ((MainActivity)getBaseActivity()).setToolbarTitle(R.string.app_name);

        return view;
    }

    @OnClick(R.id.btn_conmane)
    public void onClickConfManagerButton() {
        ((MainActivity)getBaseActivity()).setToolbarTitle(R.string.mode_conmane);
        replaceFragment(new EventFragment(), true);
    }

    @OnClick(R.id.btn_slcon)
    public void onClickSlconButton() {
        ((MainActivity)getBaseActivity()).setToolbarTitle(R.string.mode_slcon);
        replaceFragment(new PresentationFragment(), true);
    }

}