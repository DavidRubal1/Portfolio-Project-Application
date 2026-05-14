import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class PianoApplicationView extends JFrame {

    private PianoApplicationController controller;

    private JButton buttonA = new JButton("BUTTON A");
    private JButton buttonB = new JButton("BUTTON B");
    private JTextArea text = new JTextArea("Hello World", 5, 20);

    PianoApplicationView() {
        super("PianoApp");

        JPanel pianoPanel = new JPanel();
        pianoPanel.setLayout(new BoxLayout(pianoPanel, BoxLayout.PAGE_AXIS));

        JPanel buttonPanel = new JPanel(new BoxLayout(this.rootPane, BoxLayout.X_AXIS));
        JPanel textPanel = new JPanel(new GridLayout(3, 3));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(600, 200);

        this.buttonA.addActionListener((ActionEvent e) -> this.controller.playKey(12));

        textPanel.add(this.text);
        buttonPanel.add(this.buttonA, BorderLayout.CENTER);
        // this.buttonA.setPreferredSize(new Dimension(300, 400));
        buttonPanel.add(this.buttonB, BorderLayout.CENTER);

        this.add(buttonPanel);
        // this.add(textPanel);

        this.pack();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        // add all of the page objects creted in private memebers section
    }

    public void registerController(PianoApplicationController controller) {
        this.controller = controller;
    }

    // public void addPianoListener(ActionListener listenForKeyPress) {
    // this.buttonA.addActionListener((ActionListener e) -> ));
    // this.buttonB.addActionListener(listenForKeyPress);
    // }
}
