
package epidemic.progpracticum.view;

import java.awt.CardLayout;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import epidemic.progpracticum.model.SimParams;

public class InformationPanel extends JPanel {

    private JTextArea textArea;

    public InformationPanel() {

        setLayout(new CardLayout(0, 0));

        textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textArea);

        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setEditable(false);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        add(scrollPane);
        

    }

    public void setText(String text) {
        textArea.setText(text);
        
    }
    
    public void resetText(){
        textArea.setText("");
    }

}
