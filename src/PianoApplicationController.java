import java.io.File;

import javax.sound.midi.Instrument;
import javax.sound.midi.Patch;

public class PianoApplicationController {
    private PianoApplicationModel model;
    private PianoApplicationView view;

    private int currentInstr;

    private final int MIDI_CHANNEL = 0;
    private final int MIDI_OFFSET_FROM_0 = 60;
    private final int VELOCITY = 40;

    public PianoApplicationController(PianoApplicationModel model, PianoApplicationView view) {
        this.model = model;
        this.view = view;
        this.currentInstr = 0;
    }

    // TODO: Have a method to turn a key off after a certain amount of time or when
    // the key is let go of.
    // ! This is important because some instruments do not decay over time

    public void changeInstrument(Instrument instr, int index) {
        Patch p = instr.getPatch();
        // System.out.println(p.getBank() + " ," + p.getProgram());
        this.model.getSynth().getChannels()[this.MIDI_CHANNEL].programChange(p.getBank(), p.getProgram());
        this.currentInstr = index;
    }

    public Instrument[] getInstrumentList() {
        return this.model.getSoundbank().getInstruments();
    }

    public int getCurrentInstrument() {
        return this.currentInstr;
    }

    // TODO finish this
    public void loadNewSoundbank(File soundbankFile) {
        // this.model.getSynth().getChannels()[this.MIDI_CHANNEL].allNotesOff();
        // this.model.getSynth().getChannels()[this.MIDI_CHANNEL].
        // Synthesizer synth = this.model.getSynth();
        // synth.unloadAllInstruments(this.model.getSoundbank());

        // Soundbank s = this.model.getSoundbank();

    }

    public void updateViewToMatchModel() {
        return;
    }

    // Sends the keyID (int [0, 12] of the key's position) to the model to update
    // the state of the keyboard accordingly
    public void processKeyPress(int keyID) {
        this.model.getKeyboard().play(this.model.STARTING_KEY + keyID, 1.0);

        if (this.model.getSynth().getChannels()[this.MIDI_CHANNEL] != null) {
            this.model.getSynth().getChannels()[this.MIDI_CHANNEL].noteOn(keyID + this.MIDI_OFFSET_FROM_0,
                    this.VELOCITY);
        }
    }

    public void processKeyRelease(int keyID) {
        this.model.getKeyboard().play(this.model.STARTING_KEY + keyID, 0.0);

        if (this.model.getSynth().getChannels()[this.MIDI_CHANNEL] != null) {
            this.model.getSynth().getChannels()[this.MIDI_CHANNEL].noteOff(keyID + this.MIDI_OFFSET_FROM_0);
        }
    }

}
