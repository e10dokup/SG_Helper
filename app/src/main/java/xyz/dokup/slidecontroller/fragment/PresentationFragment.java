package xyz.dokup.slidecontroller.fragment;

import android.bluetooth.BluetoothGattCharacteristic;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapProgressBar;
import com.uxxu.konashi.lib.Konashi;
import com.uxxu.konashi.lib.KonashiListener;
import com.uxxu.konashi.lib.KonashiManager;

import org.jdeferred.DoneCallback;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import xyz.dokup.slidecontroller.R;
import xyz.dokup.slidecontroller.core.BaseFragment;
import xyz.dokup.slidecontroller.core.MyApplication;
import xyz.dokup.slidecontroller.helper.PresentationHelper;
import xyz.dokup.slidecontroller.view.InverseTriangleView;
import xyz.dokup.slidecontroller.view.TriangleView;
import info.izumin.android.bletia.BletiaException;

/**
 * Created by e10dokup on 2015/11/26
 **/
public class PresentationFragment extends BaseFragment implements View.OnClickListener {
    private static final String TAG = PresentationFragment.class.getSimpleName();
    private final PresentationFragment self = this;

    private KonashiManager mKonashiManager;

    @Inject
    PresentationHelper mPresentationHelper;

    @Bind(R.id.btn_left)
    BootstrapButton mLeftButton;
    @Bind(R.id.btn_right)
    BootstrapButton mRightButton;
    @Bind(R.id.btn_start)
    BootstrapButton mStartButton;
    @Bind(R.id.btn_finish)
    BootstrapButton mFinishButton;
    @Bind(R.id.increment_minutes)
    InverseTriangleView mMinutesIncrement;
    @Bind(R.id.decrement_minutes)
    TriangleView mMinutesDecrement;
    @Bind(R.id.increment_seconds)
    InverseTriangleView mSecondsIncrement;
    @Bind(R.id.decrement_seconds)
    TriangleView mSecondsDecrement;
    @Bind(R.id.progress_timer)
    BootstrapProgressBar mProgressBar;
    @Bind(R.id.text_timer)
    TextView mTimerText;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_presentation, container, false);

        { // Inject values
            MyApplication app = (MyApplication) getBaseActivity().getApplication();
            app.getComponent().inject(this);
        }

        ButterKnife.bind(this, view);
        setOnClickListeners();

        mKonashiManager = new KonashiManager(getBaseActivity());
        mKonashiManager.find(getBaseActivity());

        return view;
    }

    private void setOnClickListeners() {
        mMinutesIncrement.setOnClickListener(this);
        mMinutesDecrement.setOnClickListener(this);
        mSecondsIncrement.setOnClickListener(this);
        mSecondsDecrement.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        mKonashiManager.addListener(mKonashiListener);
        refreshViews();
    }

    @Override
    public void onPause() {
        mKonashiManager.removeListener(mKonashiListener);
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onDestroy() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if(mKonashiManager.isConnected()){
                    mKonashiManager.reset()
                            .then(new DoneCallback<BluetoothGattCharacteristic>() {
                                @Override
                                public void onDone(BluetoothGattCharacteristic result) {
                                    mKonashiManager.disconnect();
                                }
                            });
                }
            }
        }).start();
        super.onDestroy();
    }

    private void refreshViews() {
        boolean isReady = mKonashiManager.isReady();
        mRightButton.setVisibility(isReady ? View.VISIBLE : View.GONE);
        mLeftButton.setVisibility(isReady ? View.VISIBLE : View.GONE);
        mStartButton.setVisibility(isReady ? View.VISIBLE : View.GONE);
        mFinishButton.setVisibility(isReady ? View.VISIBLE : View.GONE);
    }

    private final KonashiListener mKonashiListener = new KonashiListener() {
        @Override
        public void onConnect(KonashiManager manager) {
            refreshViews();
            mKonashiManager.pinModeAll(0xFF);
        }

        @Override
        public void onDisconnect(KonashiManager manager) {
            refreshViews();
        }

        @Override
        public void onError(KonashiManager manager, BletiaException e) {

        }

        @Override
        public void onUpdatePioOutput(KonashiManager manager, int value) {

        }

        @Override
        public void onUpdateUartRx(KonashiManager manager, byte[] value) {

        }

        @Override
        public void onUpdateBatteryLevel(KonashiManager manager, int level) {

        }
    };

    @OnClick(R.id.btn_start)
    public void onClickStartButton() {
        pinSwitching(Konashi.PIO0);
        if(!mPresentationHelper.isStarted()) {
            mPresentationHelper.startTimer(mTimerText, mProgressBar);
        } else {
            mPresentationHelper.cancelTimer();
        }
    }

    @OnClick(R.id.btn_finish)
    public void onClickFinishButton() {
        pinSwitching(Konashi.PIO1);
        if(mPresentationHelper.isStarted()) {
            mPresentationHelper.cancelTimer();
        }
    }

    @OnClick(R.id.btn_left)
    public void onClickLeftButton() {
        pinSwitching(Konashi.PIO2);
    }

    @OnClick(R.id.btn_right)
    public void onClickRightButton() {
        pinSwitching(Konashi.PIO3);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.increment_minutes:
                mPresentationHelper.setMinutes(mPresentationHelper.getMinutes() + 1);
                break;
            case R.id.decrement_minutes:
                mPresentationHelper.setMinutes(mPresentationHelper.getMinutes() - 1);
                break;
            case R.id.increment_seconds:
                mPresentationHelper.setSeconds(mPresentationHelper.getSeconds() + 1);
                break;
            case R.id.decrement_seconds:
                mPresentationHelper.setSeconds(mPresentationHelper.getSeconds() - 1);
                break;
        }
        mPresentationHelper.setTimerText(mTimerText);
    }

    private void pinSwitching(final int pin) {
        mKonashiManager.digitalWrite(pin, Konashi.HIGH)
                .then(new DoneCallback<BluetoothGattCharacteristic>() {
                    @Override
                    public void onDone(BluetoothGattCharacteristic result) {
                        mKonashiManager.digitalWrite(pin, Konashi.LOW);
                    }
                });
    }
}