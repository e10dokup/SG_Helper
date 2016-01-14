package xyz.dokup.slidecontroller.core;

import dagger.Component;
import xyz.dokup.slidecontroller.MainActivity;
import xyz.dokup.slidecontroller.fragment.DetailFragment;
import xyz.dokup.slidecontroller.fragment.EventFragment;
import xyz.dokup.slidecontroller.fragment.PresentationFragment;
import xyz.dokup.slidecontroller.fragment.TalkFragment;
import xyz.dokup.slidecontroller.fragment.TimetableFragment;
import xyz.dokup.slidecontroller.fragment.TopFragment;

@Component(
        modules = MyModule.class
)
public interface AppComponent {
    void inject(MainActivity mainActivity);

    void inject(PresentationFragment presentationFragment);

    void inject(TopFragment topFragment);

    void inject(EventFragment eventFragment);

    void inject(TimetableFragment timetableFragment);

    void inject(TalkFragment talkFragment);

    void inject(DetailFragment detailFragment);
}
