
package melodiai.ui;

import java.io.File;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.midi.InvalidMidiDataException;
import melodiai.ai.ArraySequencer;
import melodiai.datastructures.DynamicList;
import melodiai.datastructures.Trie;
import melodiai.datastructures.TrieNode;
import melodiai.midi.MidiBuilder;
import melodiai.midi.MidiParser;


public class tempUi {
    
    private File[] composers;
    private File[] songs;
    private Scanner scanner;
    private MidiParser midiParser;
    private ArraySequencer seq;
    private MidiBuilder mb;
    private Trie noteKeyTrie;
    private Trie noteLengthTrie;
    private Trie velocityTrie;
    private int markovOrder;
    private int lengthInBars;
    private double timeSig;
    
    

    public tempUi() {
        this.scanner = new Scanner(System.in);
        this.composers = new File("Midifiles/").listFiles();
        this.midiParser = new MidiParser();
        this.seq = new ArraySequencer();
        this.mb = new MidiBuilder();
    }
    
    public void start() {
        System.out.println("WELCOME TO MELODIAI! \n \n");
        
        
        
        File[] songFiles = this.getComposer();
        
        midiParser.parseMidi(songFiles);
        this.getMarkovOrder();
        this.generateTries();
        this.getLengthInBars();
        this.getTimeSig();
        this.generateMidi();
        
    }
    
    private void generateMidi() {
        
        int [] noteSeq = seq.generateSequence(lengthInBars * 10, noteKeyTrie, markovOrder, noteKeyTrie.getRandomRootChild());
        int [] veloSeq = seq.generateSequence(lengthInBars * 10, velocityTrie, markovOrder, velocityTrie.getRandomRootChild());
        DynamicList<Double> lengthSeq = seq.generateRhytmSequence(lengthInBars, noteLengthTrie, markovOrder, noteLengthTrie.getRandomRootChild(), timeSig);
        
        System.out.println("Last but not least, please name your song: ");
        String name = scanner.nextLine();
        try {
            mb.createMidiFile(name, noteSeq, veloSeq, lengthSeq);
        } catch (InvalidMidiDataException ex) {
            Logger.getLogger(tempUi.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    private void getTimeSig() {
        System.out.println("Select time signature for your song: 1. 3/4 or 2. 4/4 \n");
        
        while(true) {
            
            int selection = Integer.valueOf(scanner.nextLine());
            
            if (selection == 1) {
                timeSig = 3;
                break;
            } else if (selection == 2) {
                timeSig = 4;
                break;
            } else {
                System.out.println("Incorrect selection... \n");
            }
        }
        
    }
    
    private void generateTries() {
        
        noteKeyTrie = new Trie(markovOrder);
        noteKeyTrie.put(midiParser.getNoteKeys());
        
        noteLengthTrie = new Trie(markovOrder); 
        noteLengthTrie.put(midiParser.getNoteLengths());
        
        velocityTrie = new Trie(markovOrder);
        velocityTrie.put(midiParser.getVelocities()); 
    }
    
    private File[] getComposer() {
        
        System.out.println("Please select composer from the following:\n");
        
        for (int i = 0; i < composers.length; i++) {
            System.out.println(i + 1 + ". " + composers[i].getName());
        }
        
        System.out.println("");
        
        while(true) {
            
            int composerSelection = Integer.valueOf(scanner.nextLine());

            if (composerSelection > 0 && composerSelection <= composers.length){
               return new File("Midifiles/" + composers[composerSelection -1].getName() + "/").listFiles(); 
            }
            
            System.out.println("Incorrect selection.");
        }
    }
    
    private void getMarkovOrder() {
     
        while(true) {
            System.out.println("Enter desired Markov order (2-6): \n");

            int order = Integer.valueOf(scanner.nextLine());
            if (order >= 2 && order <= 6) {
                markovOrder = order;
                break;
            }           
            System.out.println("Enter value between 2 and 6");
        }
    }
    
    private void getLengthInBars() {
        
        while(true) {
            System.out.println("Enter length in bars: \n");

            int length = Integer.valueOf(scanner.nextLine());
            if (length > 0 && length <= 10000) {
                lengthInBars = length;
                break;
            }           
            System.out.println("Enter value between 0 and 10 000");
        }
    }
    
    private int getStartingNote(TrieNode[] nodeArray) {
        
        System.out.println("Select starting note from the following: \n");
        
        while(true) {
            String[] startingNotesList = this.startingNotesToStringArray(nodeArray);
            int index = 1;           
            for (String s: startingNotesList) {
                System.out.print(index + ". " + s);
            }

            int selectedNote = Integer.valueOf(scanner.nextLine());
            if (selectedNote > 0 && selectedNote <= startingNotesList.length) {
                return selectedNote ;
            }           
            System.out.println("Incorrect selection");
        }
    }
    
    private String getNoteName(int noteNumber){
        
        if (noteNumber > 0) {
          noteNumber -= 21;
            String[] notes = new String[] {"A", "A#", "B", "C", "C#", "D", "D#", "E", "F", "F#", "G", "G#"};
            int octave = noteNumber / 12 + 1;
            String name = notes[noteNumber % 12];
            return name + octave;  
        }
        
        return "";
    }
    
    public String[] startingNotesToStringArray(TrieNode[] nodeArray) {
        DynamicList<String> notesList = new DynamicList<>();
        
        for (int i = 0; i < nodeArray.length; i++) {
            if (nodeArray[i] != null) {
                String note = this.getNoteName(nodeArray[i].getNodeKey());
                if (!note.isBlank() && !note.isEmpty()) {
                    notesList.insert(note);
                }
            }
        }
        
        String[] strArray = new String[notesList.size()];
        
        for (int i = 0; i < notesList.size(); i++) {
           strArray[i] = notesList.get(i);
        }
        
        return strArray;
    }
    
    
}
