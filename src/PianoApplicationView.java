import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Point;

import javax.swing.BorderFactory;
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

        JPanel testKey = new JPanel();
        testKey.setEnabled(true);
        testKey.setFocusable(false);
        testKey.addMouseListener(this.controller);
        testKey.putClientProperty("ID", -1);
        testKey.setPreferredSize(new Dimension(100, 366));
        testKey.setBackground(Color.green);
        testKey.setBorder(BorderFactory.createLineBorder(Color.black));
        testKey.addMouseListener(this.controller);
        this.add(testKey);

        int keyboardWidth = 1500;
        int numWhiteKeys = 7;

        // keys are proportionally 2.2:6.5 and 1:4.5
        int whiteKeyWidth = keyboardWidth / numWhiteKeys;
        int whiteKeyHeight = (int) (whiteKeyWidth * 6.5 / 2.2);

        int blackKeyWidth = (int) (whiteKeyWidth / 2.2);
        int blackKeyHeight = (int) (whiteKeyHeight * 4.5 / 6.5);

        JLayeredPane keyLayers = new JLayeredPane();
        keyLayers.setPreferredSize(new Dimension(keyboardWidth, whiteKeyHeight));

        // white key layer has a grid layout to keep all keys in one row
        JPanel whiteKeys = new JPanel(new GridLayout(1, numWhiteKeys, 0, 0));
        // black key layer has a null layout to allow spacing based on the position of
        // the white keys
        JPanel blackKeys = new JPanel(null);

        // create all keys, add to respective key layer
        Point nextWhiteKeyPos = new Point(0, 0);
        for (int i = 0; i <= 11; i++) {
            JPanel key = new JPanel();
            key.setEnabled(true);
            key.setFocusable(false);
            key.addMouseListener(this.controller);
            key.putClientProperty("ID", i);
            switch (i % 12) {
                case 1, 3, 6, 8, 10:
                    key.setBackground(Color.black);
                    key.setBorder(BorderFactory.createLineBorder(Color.gray));
                    key.setBounds(nextWhiteKeyPos.x - blackKeyWidth / 2, 0, blackKeyWidth, blackKeyHeight);
                    blackKeys.add(key);
                    break;
                default:
                    key.setSize(new Dimension(whiteKeyWidth, whiteKeyHeight));
                    key.setBackground(Color.white);
                    key.setBorder(BorderFactory.createLineBorder(Color.black));
                    whiteKeys.add(key);
                    nextWhiteKeyPos.setLocation(nextWhiteKeyPos.x + whiteKeyWidth, nextWhiteKeyPos.y);
                    break;
            }
        }

        whiteKeys.setBounds(0, 0, keyboardWidth, whiteKeyHeight);
        blackKeys.setBounds(0, 0, keyboardWidth, blackKeyHeight);
        blackKeys.setOpaque(false);

        keyLayers.add(blackKeys);
        keyLayers.add(whiteKeys);

        this.add(keyLayers);

        this.setLayout(new FlowLayout());
        this.pack();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    public void registerController(PianoApplicationController controller) {
        this.controller = controller;
    }

    // public void addPianoListener(ActionListener listenForKeyPress) {
    // this.buttonA.addActionListener((ActionListener e) -> ));
    // this.buttonB.addActionListener(listenForKeyPress);
    // }
}
