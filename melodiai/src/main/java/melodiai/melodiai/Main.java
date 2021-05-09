package melodiai.melodiai;

import java.io.File;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;
import melodiai.ai.ArraySequencer;
import melodiai.datastructures.DynamicList;
import melodiai.datastructures.Trie;
import melodiai.midi.MidiBuilder;
import melodiai.midi.MidiParser;
import melodiai.ui.Ui;

public class Main {
    
    


    public static void main(String[] args) throws InvalidMidiDataException, MidiUnavailableException, InterruptedException {
        
        MidiParser midiParser = new MidiParser();
        ArraySequencer seq = new ArraySequencer();
        MidiBuilder mb = new MidiBuilder();
        File folder = new File("Midifiles/Bach/");
        File[] listOfFiles = folder.listFiles();
        
        midiParser.parseMidi(listOfFiles);
          
        Trie noteKeyTrie = new Trie(4);
        noteKeyTrie.put(midiParser.getNoteKeys());
        
        Trie noteLengthTrie = new Trie(4); 
        noteLengthTrie.put(midiParser.getNoteLengths());
        
        Trie velocityTrie = new Trie(4);
        velocityTrie.put(midiParser.getVelocities());

        //Ui ui = new Ui(noteKeyTrie.getRoot().getChildren());
        
        noteKeyTrie.print();
        noteLengthTrie.print();
        int [] noteSeq = seq.generateSequence(1000, noteKeyTrie, 4, noteKeyTrie.getRandomRootChild());
        int [] veloSeq = seq.generateSequence(1000, velocityTrie, 2, velocityTrie.getRandomRootChild());
        DynamicList<Double> lengthSeq = seq.generateRhytmSequence(15, noteLengthTrie, 4, noteLengthTrie.getRandomRootChild(), 3);  
       
    }
    
}
