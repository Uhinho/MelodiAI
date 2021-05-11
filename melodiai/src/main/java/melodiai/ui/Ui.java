
package melodiai.ui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.BorderFactory;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;
import melodiai.datastructures.DynamicList;
import melodiai.datastructures.TrieNode;

public class Ui extends JFrame {
    static Ui ui;
    
    JPanel pnPanel0;
    JLabel lbL1;
    JList lsStartNoteList;
    JTextField tfText0;
    JLabel lbL2;
    JComboBox cmbTimeSig;
    JLabel lbL3;
    JList lsStyleList;
    JLabel lbL4;
    JButton generateBtn;
    
    
    public Ui(TrieNode[] startingNotes) {
        
        super( "MelodiAI" );

        pnPanel0 = new JPanel();
        pnPanel0.setBorder( BorderFactory.createTitledBorder( "" ) );
        GridBagLayout gbPanel0 = new GridBagLayout();
        GridBagConstraints gbcPanel0 = new GridBagConstraints();
        pnPanel0.setLayout( gbPanel0 );

        lbL1 = new JLabel( "Starting note"  );
        gbcPanel0.gridx = 1;
        gbcPanel0.gridy = 2;
        gbcPanel0.gridwidth = 1;
        gbcPanel0.gridheight = 1;
        gbcPanel0.fill = GridBagConstraints.BOTH;
        gbcPanel0.weightx = 1;
        gbcPanel0.weighty = 1;
        gbcPanel0.anchor = GridBagConstraints.NORTH;
        gbcPanel0.insets = new Insets( 0,10,0,10 );
        gbPanel0.setConstraints( lbL1, gbcPanel0 );
        pnPanel0.add( lbL1 );

        String []dataStartNoteList = this.startingNotesToStringArray(startingNotes);
        lsStartNoteList = new JList( dataStartNoteList );
        JScrollPane scpStartNoteList = new JScrollPane( lsStartNoteList );
        gbcPanel0.gridx = 1;
        gbcPanel0.gridy = 3;
        gbcPanel0.gridwidth = 1;
        gbcPanel0.gridheight = 9;
        gbcPanel0.fill = GridBagConstraints.BOTH;
        gbcPanel0.weightx = 1;
        gbcPanel0.weighty = 0;
        gbcPanel0.anchor = GridBagConstraints.NORTH;
        gbcPanel0.insets = new Insets( 5,10,5,10 );
        gbPanel0.setConstraints( scpStartNoteList, gbcPanel0 );
        pnPanel0.add( scpStartNoteList );

        tfText0 = new JTextField( );
        gbcPanel0.gridx = 1;
        gbcPanel0.gridy = 15;
        gbcPanel0.gridwidth = 1;
        gbcPanel0.gridheight = 1;
        gbcPanel0.fill = GridBagConstraints.BOTH;
        gbcPanel0.weightx = 1;
        gbcPanel0.weighty = 0;
        gbcPanel0.anchor = GridBagConstraints.NORTH;
        gbcPanel0.insets = new Insets( 0,10,0,10 );
        gbPanel0.setConstraints( tfText0, gbcPanel0 );
        pnPanel0.add( tfText0 );

        lbL2 = new JLabel( "Length in bars"  );
        gbcPanel0.gridx = 1;
        gbcPanel0.gridy = 14;
        gbcPanel0.gridwidth = 1;
        gbcPanel0.gridheight = 1;
        gbcPanel0.fill = GridBagConstraints.BOTH;
        gbcPanel0.weightx = 1;
        gbcPanel0.weighty = 1;
        gbcPanel0.anchor = GridBagConstraints.NORTH;
        gbcPanel0.insets = new Insets( 0,10,0,0 );
        gbPanel0.setConstraints( lbL2, gbcPanel0 );
        pnPanel0.add( lbL2 );

        String []dataTimeSignatures = {"4/4", "3/4"};
        cmbTimeSig = new JComboBox( dataTimeSignatures );
        gbcPanel0.gridx = 1;
        gbcPanel0.gridy = 18;
        gbcPanel0.gridwidth = 1;
        gbcPanel0.gridheight = 1;
        gbcPanel0.fill = GridBagConstraints.BOTH;
        gbcPanel0.weightx = 1;
        gbcPanel0.weighty = 0;
        gbcPanel0.anchor = GridBagConstraints.NORTH;
        gbcPanel0.insets = new Insets( 0,10,20,10 );
        gbPanel0.setConstraints(cmbTimeSig, gbcPanel0 );
        pnPanel0.add(cmbTimeSig );

        lbL3 = new JLabel( "Time Signature"  );
        gbcPanel0.gridx = 1;
        gbcPanel0.gridy = 17;
        gbcPanel0.gridwidth = 1;
        gbcPanel0.gridheight = 1;
        gbcPanel0.fill = GridBagConstraints.BOTH;
        gbcPanel0.weightx = 1;
        gbcPanel0.weighty = 1;
        gbcPanel0.anchor = GridBagConstraints.NORTH;
        gbcPanel0.insets = new Insets( 0,10,0,10 );
        gbPanel0.setConstraints( lbL3, gbcPanel0 );
        pnPanel0.add( lbL3 );

        String []dataStyleList = { "Bach", "Avici", "Mozart"};
        lsStyleList = new JList( dataStyleList );
        gbcPanel0.gridx = 5;
        gbcPanel0.gridy = 3;
        gbcPanel0.gridwidth = 6;
        gbcPanel0.gridheight = 9;
        gbcPanel0.fill = GridBagConstraints.BOTH;
        gbcPanel0.weightx = 1;
        gbcPanel0.weighty = 1;
        gbcPanel0.anchor = GridBagConstraints.NORTH;
        gbcPanel0.insets = new Insets( 5,10,5,10 );
        gbPanel0.setConstraints( lsStyleList, gbcPanel0 );
        pnPanel0.add( lsStyleList );

        lbL4 = new JLabel( "Style"  );
        gbcPanel0.gridx = 5;
        gbcPanel0.gridy = 2;
        gbcPanel0.gridwidth = 6;
        gbcPanel0.gridheight = 1;
        gbcPanel0.fill = GridBagConstraints.BOTH;
        gbcPanel0.weightx = 1;
        gbcPanel0.weighty = 1;
        gbcPanel0.anchor = GridBagConstraints.NORTH;
        gbcPanel0.insets = new Insets( 0,10,0,0 );
        gbPanel0.setConstraints( lbL4, gbcPanel0 );
        pnPanel0.add( lbL4 );

        generateBtn = new JButton( "Generate"  );
        gbcPanel0.gridx = 5;
        gbcPanel0.gridy = 14;
        gbcPanel0.gridwidth = 6;
        gbcPanel0.gridheight = 2;
        gbcPanel0.fill = GridBagConstraints.BOTH;
        gbcPanel0.weightx = 1;
        gbcPanel0.weighty = 0;
        gbcPanel0.anchor = GridBagConstraints.NORTH;
        gbcPanel0.insets = new Insets( 10,10,10,10 );
        gbPanel0.setConstraints(generateBtn, gbcPanel0 );
        pnPanel0.add(generateBtn );
        generateBtn.addActionListener(e -> {
            
        
        });

        setDefaultCloseOperation( EXIT_ON_CLOSE );

        setContentPane( pnPanel0 );
        pack();
        setVisible( true );
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
    
    private String getNoteName(int noteNumber){
        noteNumber -= 21;
        String[] notes = new String[] {"A", "A#", "B", "C", "C#", "D", "D#", "E", "F", "F#", "G", "G#"};
        int octave = noteNumber / 12 + 1;
        String name = notes[noteNumber % 12];
        return name + octave;
    }
    
}
