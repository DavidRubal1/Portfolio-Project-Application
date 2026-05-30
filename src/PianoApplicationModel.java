// all the calculations

import java.io.File;
import java.io.IOException;

import javax.sound.midi.Instrument;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Soundbank;
import javax.sound.midi.Synthesizer;

import components.piano.Piano;
import components.piano.Piano1;

public class PianoApplicationModel {
    // TODO: Remake the Piano Component without using the OSU component interface so
    // the components jar file does not need to be included

    // TODO: Allow importing soundbanks & the ability to switch between instruments
    // through the menu

    private Piano keyboard;
    // Keyboard properties, subject to change and usage will vary
    public final int STARTING_KEY = 40;
    public final int KEYBOARD_LENGTH = 13;

    private Synthesizer synth;
    private Soundbank soundbank;
    private MidiChannel[] channel;
    private Instrument[] instruments;

    public PianoApplicationModel() {
        // Initialize Piano Object
        this.keyboard = new Piano1(this.KEYBOARD_LENGTH, this.STARTING_KEY);

        // Initialize Midi Synthesizer
        try {
            this.synth = MidiSystem.getSynthesizer();
            this.synth.open();
        } catch (MidiUnavailableException e) {
            System.err.print("Unable to get synthesizer");
            return;
        }

        // Initialize Soundbank
        // Use the default soundbank first. If it is null, then use
        // the included soundbank instead.
        if (this.synth.getDefaultSoundbank() == null) {
            // Call loadsoundbank with the one provided in lib.
            File soundbankFile = new File("lib\\GS_sound_set__16_bit_.sf2");
            try {
                this.soundbank = MidiSystem.getSoundbank(soundbankFile);
            } catch (IOException e) {
                System.err.println("Unable to read/open soundbank file");
                return;
            } catch (InvalidMidiDataException e) {
                System.err.println("Soundbank file does not point to valid MIDI soundbank");
                return;
            }
        } else {
            this.soundbank = this.synth.getDefaultSoundbank();
        }

    }

    public Piano getKeyboard() {
        return this.keyboard;
    }

    public Synthesizer getSynth() {
        return this.synth;
    }

    public Soundbank getSoundbank() {
        return this.soundbank;
    }

    public void setSoundbank(Soundbank soundbank) {
        this.soundbank = soundbank;
    }
}