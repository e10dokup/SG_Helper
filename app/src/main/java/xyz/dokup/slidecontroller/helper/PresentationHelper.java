package xyz.dokup.slidecontroller.helper;

import android.os.CountDownTimer;
import android.support.design.widget.Snackbar;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.BootstrapProgressBar;

import xyz.dokup.slidecontroller.R;

/**
 * Created by e10dokup on 2015/12/02
 **/
public class PresentationHelper {
    private static final String TAG = PresentationHelper.class.getSimpleName();
    private final PresentationHelper self = this;
    private final int SECOND_MILLI = 1000;

    private boolean mStarted = false;
    private long mMinutes, mSeconds;
    private MyCountDownTimer mTimer;

    public PresentationHelper() {
        mMinutes = 0;
        mSeconds = 0;
    }

    public long getMinutes() {
        return mMinutes;
    }

    public void setMinutes(long minutes) {
        if(minutes >= 0) {
            mMinutes = minutes;
        }
    }

    public long getSeconds() {
        return mSeconds;
    }

    public void setSeconds(long seconds) {
        if(seconds >= 0) {
            mSeconds = seconds % 60;
        }
    }

    public boolean isStarted() {
        return mStarted;
    }

    public void startTimer(TextView timerTextView, BootstrapProgressBar progressBar) {
        if(!mStarted || ( mMinutes == 0 && mSeconds == 0 )) {
            mStarted = true;
            long timerSeconds = mMinutes * 60 + mSeconds;
            progressBar.setProgress(100);
            mTimer = new MyCountDownTimer(timerSeconds * SECOND_MILLI, timerTextView, progressBar);
            mTimer.start();
        }
    }

    public void cancelTimer() {
        if(mStarted) {
            mStarted = false;
            mTimer.cancel();
            setTimerText(mTimer.getTimerTextView());
            mTimer.getProgressBar().setProgress(0);
        }
    }

    public void setTimerText(TextView timerTextView) {
        String minutesString = String.format("%02d", mMinutes);
        String secondsString = String.format("%02d", mSeconds);
        timerTextView.setText(minutesString + " : " + secondsString);
    }

    class MyCountDownTimer extends CountDownTimer {
        private TextView mTimerTextView;
        private BootstrapProgressBar mProgressBar;
        private long mLength;

        public MyCountDownTimer(long millisInFutere, TextView timerTextView, BootstrapProgressBar progressBar) {
            super(millisInFutere, SECOND_MILLI);
            mTimerTextView = timerTextView;
            mProgressBar = progressBar;
            mLength = millisInFutere / 1000;
        }

        public TextView getTimerTextView() {
            return mTimerTextView;
        }

        public BootstrapProgressBar getProgressBar() {
            return mProgressBar;
        }

        @Override
        public void onTick(long l) {
            long seconds = (l / 1000);
            int percent = (int) ((float)seconds / (float)mLength * 100.0);
            mProgressBar.setProgress(percent);
            String minutesString = String.format("%02d", seconds/60);
            String secondsString = String.format("%02d", seconds%60);
            mTimerTextView.setText(minutesString + " : " + secondsString);
        }

        @Override
        public void onFinish() {
            Snackbar.make(mTimerTextView, R.string.finish_timer, Snackbar.LENGTH_LONG).show();
            mProgressBar.setProgress(0);
            mTimerTextView.setText("00 : 00");
        }
    }

}