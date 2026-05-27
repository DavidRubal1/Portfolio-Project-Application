public class PianoAppDemo {

    public static void main() {

        PianoApplicationModel model = new PianoApplicationModel();
        PianoApplicationView view = new PianoApplicationView();
        PianoApplicationController controller = new PianoApplicationController(model, view);

        model.test();

        view.registerController(controller);
    }
}
