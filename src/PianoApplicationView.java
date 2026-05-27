import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class PianoApplicationView extends JFrame implements MouseListener {

    private PianoApplicationController controller;

    JLayeredPane keyLayers;
    JPanel blackKeys;

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

        // TODO: Streamline fix for black keys being re-layered everytime a white key
        // updates

        // TODO: Allow holding down the mouse to drag between notes

        int keyboardWidth = 1500;
        int numWhiteKeys = 7;

        // keys are proportionally 2.2:6.5 and 1:4.5
        int whiteKeyWidth = keyboardWidth / numWhiteKeys;
        int whiteKeyHeight = (int) (whiteKeyWidth * 6.5 / 2.2);

        int blackKeyWidth = (int) (whiteKeyWidth / 2.2);
        int blackKeyHeight = (int) (whiteKeyHeight * 4.5 / 6.5);

        this.keyLayers = new JLayeredPane();
        this.keyLayers.setPreferredSize(new Dimension(keyboardWidth, whiteKeyHeight));

        // white key layer has a grid layout to keep all keys in one row
        JPanel whiteKeys = new JPanel(new GridLayout(1, numWhiteKeys, 0, 0));
        // black key layer has a null layout to allow spacing based on the position of
        // the white keys
        this.blackKeys = new JPanel(null);

        // create all keys, add to respective key layer
        Point nextWhiteKeyPos = new Point(0, 0);
        for (int i = 0; i <= 11; i++) {
            JPanel key = new JPanel();
            key.setEnabled(true);
            key.setFocusable(false);

            key.addMouseListener(this);

            key.putClientProperty("ID", i);
            switch (i % 12) {
                case 1, 3, 6, 8, 10:
                    key.setBackground(Color.black);
                    key.putClientProperty("TYPE", "BLACK");
                    key.setBorder(BorderFactory.createLineBorder(Color.gray));
                    key.setBounds(nextWhiteKeyPos.x - blackKeyWidth / 2, 0, blackKeyWidth, blackKeyHeight);
                    this.blackKeys.add(key);
                    break;
                default:
                    key.setSize(new Dimension(whiteKeyWidth, whiteKeyHeight));
                    key.setBackground(Color.white);
                    key.putClientProperty("TYPE", "WHITE");
                    key.setBorder(BorderFactory.createLineBorder(Color.black));
                    whiteKeys.add(key);
                    nextWhiteKeyPos.setLocation(nextWhiteKeyPos.x + whiteKeyWidth, nextWhiteKeyPos.y);
                    break;
            }
        }

        whiteKeys.setBounds(0, 0, keyboardWidth, whiteKeyHeight);
        this.blackKeys.setBounds(0, 0, keyboardWidth, blackKeyHeight);
        this.blackKeys.setOpaque(false);

        this.keyLayers.add(this.blackKeys);
        this.keyLayers.add(whiteKeys);

        this.add(this.keyLayers);

        this.setLayout(new FlowLayout());
        this.pack();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    public void registerController(PianoApplicationController controller) {
        this.controller = controller;
    }

    // mouseListener cannot be implemented in the controller because mouseListener
    // for each component is set before the controller is constructed.

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        JComponent key = (JComponent) (e.getSource());
        this.controller.processKeyPress((int) key.getClientProperty("ID"));

        System.out.println("Key Pressed");
        System.out.println("KEY ID: " + key.getClientProperty("ID"));

        key.setBackground(Color.gray);

        this.keyLayers.setLayer(this.blackKeys, JLayeredPane.DRAG_LAYER);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        System.out.println("Key Released");

        JComponent key = (JComponent) (e.getSource());

        if (key.getClientProperty("TYPE").equals("BLACK")) {
            key.setBackground(Color.black);
        } else {
            key.setBackground(Color.white);
        }

        this.keyLayers.setLayer(this.blackKeys, JLayeredPane.DRAG_LAYER);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // System.out.println("Key Pressed");
    }

    @Override
    public void mouseExited(MouseEvent e) {
        System.out.println("Key Exited");

        JComponent key = (JComponent) (e.getSource());

        if (key.getClientProperty("TYPE").equals("BLACK")) {
            key.setBackground(Color.black);
        } else {
            key.setBackground(Color.white);
        }

        this.keyLayers.setLayer(this.blackKeys, JLayeredPane.DRAG_LAYER);
    }

}
