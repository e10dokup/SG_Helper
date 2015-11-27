package info.e10dokup.slidecontroller.core;

import dagger.Component;
import info.e10dokup.slidecontroller.MainActivity;

@Component(
        modules = MyModule.class
)
public interface AppComponent {
    void inject(MainActivity mainActivity);
}
