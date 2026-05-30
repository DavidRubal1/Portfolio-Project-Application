import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.sound.midi.Instrument;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

public class PianoApplicationView extends JFrame implements MouseListener {

    private PianoApplicationController controller;
    boolean leftClickDown = false;
    JComponent activeKey = null;

    JLayeredPane keyLayers;
    JPanel blackKeys;
    JMenuBar menu;
    JMenu menuFile, menuInstrument, menuHelp;
    JMenuItem menuFileSave, menuFileLoad, menuFileExit, menuChangeInstr, menuChangeSoundbank;

    PianoApplicationView() {
        super("PianoApp");

        this.menu = new JMenuBar();

        this.menuFile = new JMenu("File");
        this.menuInstrument = new JMenu("Instrument");
        this.menuHelp = new JMenu("Help");

        this.menuFileSave = new JMenuItem("Save");
        this.menuFileLoad = new JMenuItem("Load");
        this.menuFileExit = new JMenuItem("Exit");

        this.menuFileExit.addActionListener(e -> this.dispose());

        this.menuFile.add(this.menuFileSave);
        this.menuFile.add(this.menuFileLoad);
        this.menuFile.add(this.menuFileExit);

        this.menuChangeInstr = new JMenuItem("Change Instrument");
        this.menuChangeSoundbank = new JMenuItem("Select New Soundbank");

        this.menuChangeInstr.addActionListener(e -> this.changeInstrumentPage());
        this.menuChangeSoundbank.addActionListener(e -> this.changeSoundbankPage());

        this.menuInstrument.add(this.menuChangeInstr);
        this.menuInstrument.add(this.menuChangeSoundbank);

        this.menu.add(this.menuFile);
        this.menu.add(this.menuInstrument);
        this.menu.add(this.menuHelp);

        this.setJMenuBar(this.menu);

        // TODO: Streamline fix for black keys being re-layered everytime a white key
        // updates

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

    public void changeInstrumentPage() {
        JDialog instrumentDialog = new JDialog(this, "Instrument Selection", Dialog.ModalityType.APPLICATION_MODAL);
        instrumentDialog.setLayout(new BorderLayout());
        instrumentDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        instrumentDialog.setSize(500, 500);
        instrumentDialog.setLocationRelativeTo(this);

        ButtonGroup radioGroup = new ButtonGroup();

        Instrument[] instrList = this.controller.getInstrumentList();
        int currentInstr = this.controller.getCurrentInstrument();
        JPanel buttonPanel = new JPanel(new GridLayout(instrList.length, 1));
        for (int i = 0; i < instrList.length; i++) {
            JRadioButton instrument = new JRadioButton(instrList[i].getName());
            if (i == currentInstr) {
                instrument.setSelected(true);
            }

            // Save the current index to the object to be passed around when used
            instrument.setActionCommand(Integer.toString(i));

            radioGroup.add(instrument);
            buttonPanel.add(instrument);
        }

        JScrollPane buttonScrollPane = new JScrollPane(buttonPanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        buttonScrollPane.getVerticalScrollBar().setUnitIncrement(16);

        JPanel lowerButtonPanel = new JPanel(new FlowLayout());
        JButton selectBtn = new JButton("Select");
        JButton cancelBtn = new JButton("Cancel");
        selectBtn.addActionListener(e -> {
            this.controller.changeInstrument(instrList[Integer.parseInt(
                    radioGroup.getSelection().getActionCommand())], Integer.parseInt(
                            radioGroup.getSelection().getActionCommand()));
            instrumentDialog.dispose();
        });
        cancelBtn.addActionListener(e -> instrumentDialog.dispose());
        lowerButtonPanel.add(selectBtn);
        lowerButtonPanel.add(cancelBtn);

        instrumentDialog.add(buttonScrollPane, BorderLayout.CENTER);
        instrumentDialog.add(lowerButtonPanel, BorderLayout.SOUTH);

        instrumentDialog.setVisible(true);
    }

    public void changeSoundbankPage() {
        final JFileChooser soundBankFileChooser = new JFileChooser();
        FileFilter filter = new FileNameExtensionFilter(".sf2 or .dls Files", "sf2", "dls");
        soundBankFileChooser.setFileFilter(filter);
        soundBankFileChooser.setAcceptAllFileFilterUsed(false);
        soundBankFileChooser.showOpenDialog(this);
        this.controller.loadSoundbank(soundBankFileChooser.getSelectedFile());
    }

    public void registerController(PianoApplicationController controller) {
        this.controller = controller;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            this.activeKey = (JComponent) (e.getSource());
            this.controller.processKeyPress((int) this.activeKey.getClientProperty("ID"));

            this.activeKey.setBackground(Color.gray);

            this.keyLayers.setLayer(this.blackKeys, JLayeredPane.DRAG_LAYER);
            this.leftClickDown = true;

        }

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            // Add a check to see if the note is being sustained
            this.controller.processKeyRelease((int) this.activeKey.getClientProperty("ID"));

            if (this.activeKey.getClientProperty("TYPE").equals("BLACK")) {
                this.activeKey.setBackground(Color.black);
            } else {
                this.activeKey.setBackground(Color.white);
            }

            this.keyLayers.setLayer(this.blackKeys, JLayeredPane.DRAG_LAYER);
            this.leftClickDown = false;
            this.activeKey = null;
        }

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (this.leftClickDown) {
            this.activeKey = (JComponent) (e.getSource());
            this.controller.processKeyPress((int) this.activeKey.getClientProperty("ID"));

            this.activeKey.setBackground(Color.gray);
            this.keyLayers.setLayer(this.blackKeys, JLayeredPane.DRAG_LAYER);
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        JComponent key = (JComponent) (e.getSource());
        this.controller.processKeyRelease((int) key.getClientProperty("ID"));

        if (key.getClientProperty("TYPE").equals("BLACK")) {
            key.setBackground(Color.black);
        } else {
            key.setBackground(Color.white);
        }

        this.keyLayers.setLayer(this.blackKeys, JLayeredPane.DRAG_LAYER);
    }

}
