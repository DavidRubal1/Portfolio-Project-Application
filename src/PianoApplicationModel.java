// all the calculations

import components.piano.Piano;
import components.piano.Piano1;

public class PianoApplicationModel {
    // TODO: Remake the Piano Component without using the OSU component interface so
    // the components jar file does not need to be included
    private Piano keyboard;

    public PianoApplicationModel() {
        this.keyboard = new Piano1(13, 40);
    }

}