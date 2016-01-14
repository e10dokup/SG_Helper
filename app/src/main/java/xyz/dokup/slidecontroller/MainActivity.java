package xyz.dokup.slidecontroller;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;

import butterknife.Bind;
import butterknife.ButterKnife;
import xyz.dokup.slidecontroller.core.BaseActivity;
import xyz.dokup.slidecontroller.core.BaseFragment;
import xyz.dokup.slidecontroller.core.MyApplication;
import xyz.dokup.slidecontroller.fragment.TopFragment;

public class MainActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        { // Inject values
            MyApplication app = (MyApplication) getApplication();
            app.getComponent().inject(this);
        }

        ButterKnife.bind(this);
        setToolbarTitle(R.string.app_name);

        replaceFragment(new TopFragment(), false);
    }

    @Override
    public void replaceFragment(BaseFragment fragment, boolean recordBackstack) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.container, fragment);
        if (recordBackstack) transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void popFragment() {
        FragmentManager manager = getSupportFragmentManager();
        manager.popBackStack();
    }

    public void setToolbarTitle(int titleId) {
        mToolbar.setTitle(titleId);
    }
}
