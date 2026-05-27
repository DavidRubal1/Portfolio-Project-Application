public class PianoApplicationController {
    private PianoApplicationModel model;
    private PianoApplicationView view;

    public PianoApplicationController(PianoApplicationModel model, PianoApplicationView view) {
        this.model = model;
        this.view = view;

    }

    public void updateViewToMatchModel() {
        return;
    }

    // Sends the keyID (int [0, 12] of the key's position) to the model to update
    // the state of the keyboard accordingly
    public void processKeyPress(int keyID) {
        this.model.playKey(keyID);
    }

}
