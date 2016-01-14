package xyz.dokup.slidecontroller.timer;

import android.os.CountDownTimer;
import android.widget.TextView;

/**
 * Created by e10dokup on 2015/12/01
 **/
public class MyCountDownTimer extends CountDownTimer {
    private static final String TAG = MyCountDownTimer.class.getSimpleName();
    private final MyCountDownTimer self = this;

    TextView mTimerView;

    public MyCountDownTimer(TextView textView, long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
    }

    @Override
    public void onTick(long l) {
        long remainTime = l / 1000;
        long remainMinutes = remainTime / 60;
        long remainSecs = remainTime % 60;

        mTimerView.setText(String.valueOf(remainMinutes) + " : " + String.valueOf(remainSecs));
    }

    @Override
    public void onFinish() {
        mTimerView.setText("00 : 00");
    }
}