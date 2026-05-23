import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JComponent;

public class PianoApplicationController implements MouseListener {
    private PianoApplicationModel model;
    private PianoApplicationView view;

    public PianoApplicationController(PianoApplicationModel model, PianoApplicationView view) {
        this.model = model;
        this.view = view;

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println("Key Pressed");
        JComponent key = (JComponent) (e.getSource());
        key.setBackground(Color.red);
        if (key.getClientProperty("ID").equals(-1)) {
            key.setBackground(Color.red);
        }

    }

    @Override
    public void mousePressed(MouseEvent e) {
        System.out.println("Key Pressed");
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        System.out.println("Key Released");

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        System.out.println("Key Pressed");
    }

    @Override
    public void mouseExited(MouseEvent e) {
        System.out.println("Key Pressed");
    }

    // @Override
    // public void actionPerformed(ActionEvent e) {
    // if(e.getSource() == )
    // }

}
