package info.e10dokup.slidecontroller.fragment;

import android.bluetooth.BluetoothGattCharacteristic;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.uxxu.konashi.lib.Konashi;
import com.uxxu.konashi.lib.KonashiListener;
import com.uxxu.konashi.lib.KonashiManager;

import org.jdeferred.DoneCallback;
import org.jdeferred.FailCallback;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import info.e10dokup.slidecontroller.R;
import info.e10dokup.slidecontroller.core.BaseFragment;
import info.izumin.android.bletia.BletiaException;

/**
 * Created by e10dokup on 2015/11/26
 **/
public class MainFragment extends BaseFragment {
    private static final String TAG = MainFragment.class.getSimpleName();
    private final MainFragment self = this;

    private KonashiManager mKonashiManager;

    @Bind(R.id.btn_left)
    BootstrapButton mLeftButton;
    @Bind(R.id.btn_right)
    BootstrapButton mRightButton;
    @Bind(R.id.btn_start)
    BootstrapButton mStartButton;
    @Bind(R.id.btn_finish)
    BootstrapButton mFinishButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        ButterKnife.bind(this, view);

        mKonashiManager = new KonashiManager(getBaseActivity());
        mKonashiManager.find(getBaseActivity());

        return view;
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
            mKonashiManager.uartMode(Konashi.UART_ENABLE)
                    .then(new DoneCallback<BluetoothGattCharacteristic>() {
                        @Override
                        public void onDone(BluetoothGattCharacteristic result) {
                            mKonashiManager.uartBaudrate(Konashi.UART_RATE_9K6);
                        }
                    })
                    .fail(new FailCallback<BletiaException>() {
                        @Override
                        public void onFail(BletiaException result) {
                            Toast.makeText(getBaseActivity(), result.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
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

    @OnClick(R.id.btn_left)
    public void onClickLeftButton() {
        mKonashiManager.uartWrite("l".getBytes())
                .then(new DoneCallback<BluetoothGattCharacteristic>() {
                    @Override
                    public void onDone(BluetoothGattCharacteristic result) {

                    }
                });
    }

    @OnClick(R.id.btn_right)
    public void onClickRightButton() {
        mKonashiManager.uartWrite("r".getBytes())
                .then(new DoneCallback<BluetoothGattCharacteristic>() {
                    @Override
                    public void onDone(BluetoothGattCharacteristic result) {

                    }
                });
    }

    @OnClick(R.id.btn_start)
    public void onClickStartButton() {
        mKonashiManager.uartWrite("s".getBytes())
                .then(new DoneCallback<BluetoothGattCharacteristic>() {
                    @Override
                    public void onDone(BluetoothGattCharacteristic result) {

                    }
                });
    }

    @OnClick(R.id.btn_finish)
    public void onClickFinishButton() {
        mKonashiManager.uartWrite("q".getBytes())
                .then(new DoneCallback<BluetoothGattCharacteristic>() {
                    @Override
                    public void onDone(BluetoothGattCharacteristic result) {

                    }
                });
    }
}