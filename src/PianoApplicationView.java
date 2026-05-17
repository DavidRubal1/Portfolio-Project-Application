import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

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

        int keyboardWidth = 2000;
        int numWhiteKeys = 7, numBlackKeys = 5;

        // keys are proportionally 2.2:6.5 and 1:4.5
        int whiteKeyWidth = keyboardWidth / numWhiteKeys;
        int whiteKeyHeight = (int) (whiteKeyWidth * 6.5 / 2.2);

        int blackKeyWidth = (int) (whiteKeyWidth / 2.2);
        int blackKeyHeight = (int) (whiteKeyHeight * 4.5 / 6.5);

        int blackKeyGap = blackKeyWidth / 2;

        JLayeredPane keyLayers = new JLayeredPane();
        keyLayers.setPreferredSize(new Dimension(keyboardWidth, whiteKeyHeight));
        // keyLayers.setLayout(new BorderLayout());

        // TODO: Change this from GridLayout b/c it does not allow for manually sizing
        // keys
        JPanel whiteKeys = new JPanel(new GridLayout(1, numWhiteKeys, 0, 0));
        JPanel blackKeys = new JPanel(new GridLayout(1, numWhiteKeys, blackKeyGap, 0));

        for (int i = 0; i < numWhiteKeys; i++) {
            JPanel key = new JPanel();
            key.setSize(new Dimension(whiteKeyWidth, whiteKeyHeight));
            key.setBackground(Color.white);
            key.setFocusable(false);
            key.setBorder(BorderFactory.createLineBorder(Color.black));
            whiteKeys.add(key);
        }
        for (int i = 0; i < numWhiteKeys; i++) {
            JPanel key = new JPanel();
            key.setSize(new Dimension(blackKeyWidth, blackKeyHeight));
            key.setBackground(Color.black);
            key.setFocusable(false);
            key.setBorder(BorderFactory.createLineBorder(Color.gray));
            blackKeys.add(key);
        }
        whiteKeys.setBounds(0, 0, keyboardWidth, whiteKeyHeight);
        blackKeys.setBounds(0, 0, keyboardWidth, blackKeyHeight);
        blackKeys.setOpaque(false);

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
