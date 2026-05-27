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

    // TODO: Figure out MIDI Synthesizers
    // - How to assign an instrument
    // - How to play sound

    private Piano keyboard;
    private final int STARTING_KEY = 40;
    private final int KEYBOARD_LENGTH = 13;

    private Synthesizer synth;
    private Soundbank soundbank;
    private Instrument[] instruments;
    private MidiChannel[] channel;

    public PianoApplicationModel() {
        this.keyboard = new Piano1(this.KEYBOARD_LENGTH, this.STARTING_KEY);
        try {
            this.synth = MidiSystem.getSynthesizer();
            this.synth.open();
        } catch (MidiUnavailableException e) {
            System.err.print("Unable to get synthesizer");
            return;
        }

        File soundbankFile = new File("lib\\GS_sound_set__16_bit_.sf2");
        try {
            this.soundbank = MidiSystem.getSoundbank(soundbankFile);
        } catch (IOException e) {
            System.err.println("Unable to read/open soundbank file");
            return;
        } catch (InvalidMidiDataException e) {
            System.err.println("Soundbank file does not point to valid MIDI soundbank");
        }

        this.instruments = this.soundbank.getInstruments();

        this.channel = this.synth.getChannels();

    }

    public void playKey(int keyNum) {
        this.keyboard.play(this.STARTING_KEY + keyNum, 1.0);

        if (this.channel[0] != null) {
            this.channel[0].noteOn(keyNum + 60, 40);
        }

    }

    public void test() {

        for (Instrument i : this.instruments) {
            System.out.println(i.getName());
        }
    }

}