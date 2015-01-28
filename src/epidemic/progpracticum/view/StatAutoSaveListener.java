
package epidemic.progpracticum.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBoxMenuItem;

public class StatAutoSaveListener implements ActionListener {

    private SimulationFrame mySimulationFrame;

    public StatAutoSaveListener(SimulationFrame aSimulationFrams) {
        mySimulationFrame = aSimulationFrams;
    }

    @Override
    public void actionPerformed(ActionEvent anEvent) {
        final Object source = anEvent.getSource();
        final JCheckBoxMenuItem orgin = (JCheckBoxMenuItem) source;

        if (orgin.isSelected()) {

            mySimulationFrame.setAutoSaveStats(true);
        }
        else {

            mySimulationFrame.setAutoSaveStats(false);
        }

    }

}
