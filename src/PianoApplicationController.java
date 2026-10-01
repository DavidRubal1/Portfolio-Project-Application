import java.io.File;
import java.io.IOException;

import javax.sound.midi.Instrument;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Patch;

import components.piano.Piano;

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

    // TODO: Have a method to sustain a key while something is held (right click
    // maybe, a keyboard button, etc.)

    // TODO: Fix the high latency between key press and the sounds being generated

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
    public void loadSoundbank(File soundbankFile) {
        // this.model.getSynth().getChannels()[this.MIDI_CHANNEL].allNotesOff();
        // this.model.getSynth().getChannels()[this.MIDI_CHANNEL].
        // Synthesizer synth = this.model.getSynth();
        // synth.unloadAllInstruments(this.model.getSoundbank());

        try {
            this.model.setSoundbank(MidiSystem.getSoundbank(soundbankFile));
        } catch (IOException e) {
            System.err.println("Unable to read/open soundbank file");
            return;
        } catch (InvalidMidiDataException e) {
            System.err.println("Soundbank file does not point to valid MIDI soundbank");
            return;
        }
        this.currentInstr = 0;

    }

    public void updateViewToMatchModel() {
        return;
    }

    // Sends the keyID (int [0, 12] of the key's position) to the model to update
    // the state of the keyboard accordingly
    public void processKeyPress(int keyID) {
        this.model.getKeyboard().play(this.model.STARTING_KEY + keyID, 1.0);
        MidiChannel activeChannel = this.model.getSynth().getChannels()[this.MIDI_CHANNEL];

        if (activeChannel != null) {

            activeChannel.noteOn(keyID + this.MIDI_OFFSET_FROM_0,
                    this.VELOCITY);
        }
    }

    public void processKeyRelease(int keyID) {
        this.model.getKeyboard().play(this.model.STARTING_KEY + keyID, 0.0);

        if (this.model.getSynth().getChannels()[this.MIDI_CHANNEL] != null) {
            this.model.getSynth().getChannels()[this.MIDI_CHANNEL].noteOff(keyID + this.MIDI_OFFSET_FROM_0);
        }
    }

    public String getCurrentNoteString() {
        StringBuilder keys = new StringBuilder();

        for (Piano.Key k : this.model.getKeyboard().activeKeys()) {
            String s = k.toString();
            keys.append(s.substring(s.indexOf(',') + 1, s.length() - 1));
        }
        return keys.toString();
    }

}
