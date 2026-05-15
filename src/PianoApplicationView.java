import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class PianoApplicationView extends JFrame {

    private PianoApplicationController controller;

    PianoApplicationView() {
        super("PianoApp");

        JMenuBar menu = new JMenuBar();

        JMenu menuFile = new JMenu("File");
        JMenu menuEdit = new JMenu("Edit");
        JMenu menuHelp = new JMenu("Help");

        JMenuItem menuFileSave = new JMenuItem("Save");
        JMenuItem menuFileLoad = new JMenuItem("Load");
        JMenuItem menuFileExit = new JMenuItem("Exit");

        menuFile.add(menuFileSave);
        menuFile.add(menuFileLoad);
        menuFile.add(menuFileExit);

        menu.add(menuFile);
        menu.add(menuEdit);
        menu.add(menuHelp);

        this.setJMenuBar(menu);

        JPanel pianoPane = new JPanel(new GridLayout(1, 88, 0, 0));
        pianoPane.setBackground(Color.GREEN);
        pianoPane.setOpaque(true);
        // pianoPane.setPreferredSize(new Dimension(300, 300));

        JLayeredPane keyLayers = new JLayeredPane();
        keyLayers.setPreferredSize(new Dimension(1000, 100));
        // keyLayers.setOpaque(true);
        // keyLayers.setBackground(Color.blue);
        JPanel whiteKeys = new JPanel(new GridLayout(1, 88, 0, 0));
        JPanel blackKeys = new JPanel(new GridLayout(1, 88, 15, 0));

        // JButton button = new JButton("Test");
        for (int i = 0; i < 50; i++) {
            JButton key = new JButton();
            key.setPreferredSize(new Dimension(30, 100));
            key.setBackground(Color.white);
            key.setFocusable(false);
            whiteKeys.add(key);
        }
        for (int i = 0; i < 50; i++) {
            JButton key = new JButton();
            key.setSize(new Dimension(15, 60));
            key.setBackground(Color.black);
            key.setFocusable(false);
            blackKeys.add(key);
        }
        whiteKeys.setBounds(0, 0, 1000, 100);
        blackKeys.setBounds(0, 0, 1000, 60);
        keyLayers.add(blackKeys);
        keyLayers.add(whiteKeys);

        this.add(keyLayers);
        // this.add(whiteKeys);

        this.setLayout(new FlowLayout());
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
