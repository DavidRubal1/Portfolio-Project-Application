import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class PianoApplicationController implements MouseListener {
    private PianoApplicationModel model;
    private PianoApplicationView view;

    public PianoApplicationController(PianoApplicationModel model, PianoApplicationView view) {
        this.model = model;
        this.view = view;
        // Create a Piano with 13 keys starting at C4

    }

    // TODO: find a way to differentiate the keys so that each press action is
    // unique to each key.
    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println("Key Pressed");
        throw new UnsupportedOperationException("Unimplemented method 'mouseClicked'");
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'mousePressed'");
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        System.out.println("Key Released");
        throw new UnsupportedOperationException("Unimplemented method 'mouseReleased'");
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'mouseEntered'");
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'mouseExited'");
    }

    // @Override
    // public void actionPerformed(ActionEvent e) {
    // if(e.getSource() == )
    // }

}
