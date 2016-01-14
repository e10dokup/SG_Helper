package xyz.dokup.slidecontroller.core;

import android.content.Context;
import android.support.annotation.NonNull;

import dagger.Module;
import dagger.Provides;
import xyz.dokup.slidecontroller.helper.ConfManagerHelper;
import xyz.dokup.slidecontroller.helper.PresentationHelper;

/**
 * Created by e10dokup on 2015/10/09
 **/
@Module
public class MyModule {
    private static final String TAG = MyModule.class.getSimpleName();
    private final MyModule self = this;

    private Context mContext;
    private static ConfManagerHelper mConfManagerHelper;

    public MyModule(@NonNull Context context) {
        mContext = context;
        mConfManagerHelper = new ConfManagerHelper(mContext);
    }

    @Provides
    public PresentationHelper providePresentationHelper() {
        return new PresentationHelper();
    }

    @Provides
    public ConfManagerHelper provideConfManagerHelper() {
        return mConfManagerHelper;
    }
}