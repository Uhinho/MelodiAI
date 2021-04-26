package melodiai.melodiai;

import java.io.File;
import javax.sound.midi.InvalidMidiDataException;
import melodiai.ai.Sequencer;
import melodiai.datastructures.DynamicList;
import melodiai.datastructures.Trie;
import melodiai.datastructures.TrieNode;
import melodiai.midi.MidiBuilder;
import melodiai.midi.MidiParser;
import melodiai.midi.Note;

public class Main {
    
    


    public static void main(String[] args) throws InvalidMidiDataException {
        
        MidiParser midiParser = new MidiParser();
        Sequencer seq = new Sequencer();
        MidiBuilder mb = new MidiBuilder();
        File folder = new File("Midifiles/Cmajor/");
        File[] listOfFiles = folder.listFiles();
        
        midiParser.parseMidi(listOfFiles);
        
        //midiParser.getNoteLengths().print();
        
        
        Trie noteKeyTrie = new Trie(4);
        noteKeyTrie.put(midiParser.getNoteKeys());
        
        Trie noteLengthTrie = new Trie(4); 
        noteLengthTrie.put(midiParser.getNoteLengths());
        
        Trie velocityTrie = new Trie(4);
        velocityTrie.put(midiParser.getVelocities());
        

           
        int [] noteSeq = seq.generateSequence(500, noteKeyTrie, 3, noteKeyTrie.getRandomRootChild());
        int [] veloSeq = seq.generateSequence(500, velocityTrie, 3, velocityTrie.getRandomRootChild());
        int [] lengthSeq = seq.generateSequence(500, noteLengthTrie, 3, noteLengthTrie.getRandomRootChild()); 
        
        mb.createMidiFile("Uus", noteSeq, veloSeq, lengthSeq);

        
    }
    
}
